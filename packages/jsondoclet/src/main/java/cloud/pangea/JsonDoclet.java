package cloud.pangea;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.source.doctree.CommentTree;
import com.sun.source.doctree.DeprecatedTree;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.LinkTree;
import com.sun.source.doctree.ParamTree;
import com.sun.source.doctree.ReturnTree;
import com.sun.source.doctree.TextTree;
import com.sun.source.doctree.ThrowsTree;
import com.sun.source.util.DocTrees;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public final class JsonDoclet implements Doclet {
	private static final ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

	private DocTrees trees;

	@Override
	public void init(final Locale locale, final Reporter reporter) {
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Set<? extends Option> getSupportedOptions() {
		return Set.of();
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_21;
	}

	@Override
	public boolean run(final DocletEnvironment environment) {
		this.trees = environment.getDocTrees();

		final var docComments = ElementFilter.typesIn(environment.getIncludedElements())
				.stream()
				.filter(e -> e.getKind().isClass() || e.getKind().isInterface())
				.map(this::classToJson)
				.collect(Collectors.toList());

		// Sort by qualified name.
		docComments.sort((a, b) -> a.get("qualifiedName").asText().compareTo(b.get("qualifiedName").asText()));

		try {
			writeJson(new File("docs.json"), docComments);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Something bad happened: " + e1);
		}

		return true;
	}

	public static void writeJson(File f, Object o) throws IOException {
		if (f.exists())
			f.delete();
		if (!f.createNewFile())
			throw new IOException("Cannot create the file: " + f.getName());
		if (!f.canWrite())
			throw new IOException("Cannot write to " + f.getName());

		FileWriter fw = new FileWriter(f);

		final var printer = new DefaultPrettyPrinter();
		printer.indentArraysWith(new DefaultIndenter("  ", DefaultIndenter.SYS_LF));
		final var objectMapper = JsonDoclet.mapper.setDefaultPrettyPrinter(printer);
		final var json = objectMapper.writeValueAsString(o);

		fw.write(json);
		fw.flush();
		fw.close();
	}

	private ObjectNode classToJson(final TypeElement classElement) {
		final var classObj = new ObjectMapper().createObjectNode();
		processClassAttributes(classElement, classObj);
		processFieldAndMethodAttributes(classElement, classObj);
		return classObj;
	}

	private void processClassAttributes(final TypeElement classElement, final ObjectNode classObj) {
		classObj.put("name", classElement.getSimpleName().toString());
		classObj.put("qualifiedName", classElement.getQualifiedName().toString());
		classObj.put("comment", getComment(this.trees.getDocCommentTree(classElement)));
	}

	private void processFieldAndMethodAttributes(final TypeElement classElement, final ObjectNode classObj) {
		final var fields = JsonDoclet.mapper.createArrayNode();
		final var methods = JsonDoclet.mapper.createArrayNode();

		for (final var el : classElement.getEnclosedElements()) {
			// Skip private APIs.
			if (!el.getModifiers().contains(Modifier.PUBLIC)) {
				continue;
			}

			// Skip if this element contains the `@hidden` block tag.
			// <https://docs.oracle.com/en/java/javase/11/docs/specs/doc-comment-spec.html#hidden>
			if (isHidden(el)) {
				continue;
			}

			final var obj = JsonDoclet.mapper.createObjectNode();
			obj.put("name", el.getSimpleName().toString());
			obj.put("comment", getComment(this.trees.getDocCommentTree(el)));

			switch (el.getKind()) {
				case FIELD:
					obj.put("typeLong", getTypeLong(el.asType()));
					obj.put("typeShort", getTypeShort(el.asType()));
					fields.add(obj);
					break;
				case CONSTRUCTOR:
				case METHOD:
					final var execElement = (ExecutableElement) el;
					obj.put("signature", execElement.toString());
					addParams(execElement, obj);
					addReturn(execElement, obj);
					addDeprecated(execElement, obj);
					addPangeaBlocks(execElement, obj);
					methods.add(obj);
					break;
				default:
					break;
			}
		}

		classObj.set("fields", fields);
		classObj.set("methods", methods);
	}

	private void addParams(final ExecutableElement execElement, final ObjectNode obj) {
		final var params = mapper.createArrayNode();
		for (final var varElement : execElement.getParameters()) {
			final var paramObj = mapper.createObjectNode();
			paramObj.put("name", varElement.getSimpleName().toString());
			paramObj.put("typeLong", getTypeLong(varElement.asType()));
			paramObj.put("typeShort", getTypeShort(varElement.asType()));
			var comment = "";
			final var commentTree = this.trees.getDocCommentTree(execElement);
			if (commentTree != null) {
				for (final var blockTag : commentTree.getBlockTags()) {
					if (blockTag.getKind().equals(DocTree.Kind.PARAM)) {
						ParamTree paramTree = (ParamTree) blockTag;
						if (paramTree.getName().getName().equals(varElement.getSimpleName())) {
							comment = getComment(blockTag);
							break;
						}
					}
				}
			}
			paramObj.put("comment", comment);
			params.add(paramObj);
		}
		obj.set("params", params);
	}

	private void addReturn(final ExecutableElement execElement, final ObjectNode obj) {
		final var returnType = execElement.getReturnType();
		final var returnObj = mapper.createObjectNode();
		returnObj.put("typeLong", getTypeLong(returnType));
		returnObj.put("typeShort", getTypeShort(returnType));

		final var commentTree = this.trees.getDocCommentTree(execElement);
		if (commentTree != null) {
			for (final var blockTag : commentTree.getBlockTags()) {
				if (blockTag.getKind().equals(DocTree.Kind.RETURN)) {
					returnObj.put("comment", getComment(blockTag));
					break;
				}
			}
		}

		obj.set("return", returnObj);
	}

	private void addDeprecated(final ExecutableElement execElement, final ObjectNode obj) {
		final var commentTree = this.trees.getDocCommentTree(execElement);
		if (commentTree == null) {
			return;
		}

		for (final var blockTag : commentTree.getBlockTags()) {
			if (blockTag.getKind().equals(DocTree.Kind.DEPRECATED)) {
				obj.put("deprecated", getComment(blockTag));
				return;
			}
		}
	}

	private void addPangeaBlocks(final ExecutableElement execElement, final ObjectNode obj) {
		final var commentTree = this.trees.getDocCommentTree(execElement);
		if (commentTree == null) {
			return;
		}

		for (final var blockTag : commentTree.getBlockTags()) {
			// The custom `@pangea.*` block tags will be unknown to the parser.
			if (blockTag.getKind().equals(DocTree.Kind.UNKNOWN_BLOCK_TAG)) {
				final var split = blockTag.toString().split(" ", 2);
				if (split[0].equals("@pangea.code")) {
					obj.put("example", split[1]);
				} else if (split[0].equals("@pangea.description")) {
					obj.put("description", split[1]);
				} else if (split[0].equals("@pangea.operationId")) {
					obj.put("operationId", split[1]);
				}
			}
		}
	}

	private String getComment(final DocTree docTree) {
		switch (docTree.getKind()) {
			case COMMENT:
				return ((CommentTree) docTree).getBody();
			case DEPRECATED:
				return getComment(((DeprecatedTree) docTree).getBody());
			case LINK:
				return ((LinkTree) docTree).getReference().getSignature();
			case PARAM:
				return getComment(((ParamTree) docTree).getDescription());
			case RETURN:
				return getComment(((ReturnTree) docTree).getDescription());
			case TEXT:
				return ((TextTree) docTree).getBody();
			case THROWS:
				return getComment(((ThrowsTree) docTree).getDescription());
			default:
				return "";
		}
	}

	private String getComment(final List<? extends DocTree> docTreeList) {
		return docTreeList.stream().map(this::getComment).collect(Collectors.joining());
	}

	private String getComment(final DocCommentTree docCommentTree) {
		return docCommentTree != null
				? getComment(docCommentTree.getFullBody())
				: "";
	}

	private String getTypeLong(final TypeMirror type) {
		return type.toString();
	}

	private String getTypeShort(final TypeMirror type) {
		return type.getKind().equals(TypeKind.DECLARED)
				? ((DeclaredType) type).asElement().getSimpleName().toString()
				: type.toString();
	}

	private boolean isHidden(final Element element) {
		final var commentTree = this.trees.getDocCommentTree(element);
		if (commentTree == null) {
			return false;
		}

		return commentTree.getBlockTags().stream().anyMatch(blockTag -> blockTag.getKind().equals(DocTree.Kind.HIDDEN));
	}
}
