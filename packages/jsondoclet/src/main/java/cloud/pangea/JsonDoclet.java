package cloud.pangea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class JsonDoclet implements Doclet {
    public void init(Locale locale, Reporter reporter) {
    }

    public HashMap<String, Object> printElement(DocTrees trees, Element e) {
        HashMap<String, Object> props = new HashMap<>();

        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            List<HashMap<String, Object>> tags = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String, Object>> paramTags = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String, Object>> throwsTags = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String, Object>> returnsTags = new ArrayList<HashMap<String, Object>>();
            String[] types = {};

            // Usually: "METHOD" with a value of the method signature
            props.put(e.getKind().toString(), e.toString());
            props.put("fullBody", docCommentTree.getFullBody().toString());

            if (e.getKind() == ElementKind.CONSTRUCTOR || e.getKind() == ElementKind.METHOD) {
                types = getTypesFromMethod(e.toString());
            }

            for (DocTree t : docCommentTree.getBlockTags()) {
                HashMap<String, Object> tag = new HashMap<>();
                tag.put("kind", t.getKind());

                switch (t.getKind()) {
                    case PARAM:
                        var paramTag = processParamDocComment(t.toString());
						if (paramTag != null) {
							paramTags.add(paramTag);
						}
                        break;
                    case RETURN:
                      HashMap<String, Object> returnComment = processDocComment(t.toString());
                        returnsTags.add(returnComment);
                        props.put("returns", returnComment.get("text"));
                        break;
                    case THROWS:
                        throwsTags.add(processDocComment(t.toString()));
                        break;
                    case DEPRECATED:
                        props.put("deprecated", processDocComment(t.toString()).get("text"));
                        break;
                    case UNKNOWN_BLOCK_TAG:
                        HashMap<String, Object> processedComment = processDocComment(t.toString());

                        tag.put("data", processedComment);

                        if (processedComment.get("tag").equals("@pangea.description")) {
                            props.put("description", processedComment.get("text"));
                        } else if (processedComment.get("tag").equals("@pangea.code")) {
                            props.put("example", processedComment.get("text"));
                        } else if (processedComment.get("tag").equals("@pangea.operationId")) {
                            props.put("operationId", processedComment.get("text"));
                        }
                        break;
                    default:
                        break;
                }

                tag.put("rawText", t.toString());
                tags.add(tag);
            }

            // Add in types to params
            int i = 0;
            for (HashMap<String, Object> param : paramTags) {
                param.put("type", types[i]);
                i++;
            }

            props.put("throws", throwsTags);
            props.put("params", paramTags);
            props.put("tags", tags);
            props.put("rawDocString", docCommentTree.toString());
        }

        return props;
    }

    public String getName() {
        return null;
    }

    String classPathString;

    public Set<? extends Option> getSupportedOptions() {
        return null;
    }

    public SourceVersion getSupportedSourceVersion() {
        return null;
    }

    public boolean run(DocletEnvironment environment) {
        // get the DocTrees utility class to access document comments
        DocTrees docTrees = environment.getDocTrees();

        ArrayList<HashMap<String, Object>> docComments = new ArrayList<HashMap<String, Object>>();

        for (TypeElement t : ElementFilter.typesIn(environment.getIncludedElements())) {
            // System.out.println(t.getKind() + ":" + t);
            HashMap<String, Object> doc = new HashMap<>();

            doc.put("kind", t.getKind());
            doc.put("name", t.getSimpleName().toString());
            doc.put("qualifiedName", t.getQualifiedName().toString());

            // I don't know what these are and if they're useful or not...
            // commenting out for now
            // doc.put("typeParams", t.getTypeParameters().toString());
            // doc.put("interfaces", t.getInterfaces().toString());

            ArrayList<HashMap<String, Object>> comments = new ArrayList<>();
            for (Element e : t.getEnclosedElements()) {
                HashMap<String, Object> docComment = printElement(docTrees, e);

                if (!docComment.isEmpty())
                    comments.add(docComment);
            }

            doc.put("docComments", comments);

            docComments.add(doc);
        }

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

        // Try with ObjectMapper?
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(o);

        fw.write(json);
        fw.flush();
        fw.close();
    }

    /**
     * Takes a doc comment assuming the form "@param paramType paramDescription"
     * and splits it out to a HashMap: { name: "paramName", text: "paramDescription"
     * }
     *
     * @param comment
     * @return HashMap<String, Object>
     */
    private HashMap<String, Object> processParamDocComment(String comment) {
        HashMap<String, Object> param = new HashMap<>();

        String[] splitComment = comment.split(" ");
        String[] textArr = Arrays.copyOfRange(splitComment, 2, splitComment.length);

		// HACK: do nothing with type params.
		if (splitComment[1].startsWith("<") && splitComment[1].endsWith(">")) {
			return null;
		}

        param.put("name", splitComment[1]);
        param.put("text", String.join(" ", textArr));

        return param;
    }

    private HashMap<String, Object> processDocComment(String comment) {
        HashMap<String, Object> docComment = new HashMap<>();

        String[] splitComment = comment.split(" ");
        String[] textArr = Arrays.copyOfRange(splitComment, 1, splitComment.length);

        docComment.put("tag", splitComment[0]);
        docComment.put("text", String.join(" ", textArr));

        return docComment;
    }

    private String[] getTypesFromMethod(String method) {
        int start = method.indexOf("(");
        int end = method.indexOf(")");

        String typeString = method.substring(start + 1, end);

        return typeString.split(",");
    }
}
