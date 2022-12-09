package cloud.pangea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.DocTree.Kind;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class JsonDoclet implements Doclet {
    public void init(Locale locale, Reporter reporter) {
        // TODO Auto-generated method stub
    }

    /**
     * Takes a doc comment assuming the form "@param paramType paramDescription"
     * and splits it out to a HashMap: { type: "paramType", text: "paramDescription"
     * }
     * 
     * @param comment
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> processParamDocComment(String comment) {
        HashMap<String, Object> param = new HashMap<>();

        String[] splitComment = comment.split(" ");
        String[] textArr = Arrays.copyOfRange(splitComment, 2, splitComment.length);

        param.put("type", splitComment[1]);
        param.put("text", String.join(" ", textArr));

        return param;
    }

    public HashMap<String, Object> processDocComment(String comment) {
        HashMap<String, Object> docComment = new HashMap<>();

        String[] splitComment = comment.split(" ");
        String[] textArr = Arrays.copyOfRange(splitComment, 1, splitComment.length);

        docComment.put("tag", splitComment[0]);
        docComment.put("text", String.join(" ", textArr));

        return docComment;
    }

    public HashMap<String, Object> printElement(DocTrees trees, Element e) {
        HashMap<String, Object> props = new HashMap<>();

        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            // Usually: "METHOD" with a value of the method signature
            props.put(e.getKind().toString(), e.toString());
            props.put("fullBody", docCommentTree.getFullBody().toString());

            List<HashMap<String, Object>> tags = new ArrayList<HashMap<String, Object>>();
            System.out.println("THIS HAPPENED!!!!!!!" + e.getClass().getSimpleName());
            for (DocTree t : docCommentTree.getBlockTags()) {
                System.out.println("GOT HERE: " + t.getKind());
                HashMap<String, Object> tag = new HashMap<>();
                tag.put("kind", t.getKind());

                switch (t.getKind()) {
                    case PARAM:
                        tag.put("data", processParamDocComment(t.toString()));
                        break;
                    case RETURN:
                    case THROWS:
                    case UNKNOWN_BLOCK_TAG:
                        tag.put("data", processDocComment(t.toString()));
                        break;
                    default:
                        break;
                }

                tag.put("rawText", t.toString());
                tags.add(tag);
            }

            props.put("tags", tags);
            props.put("blockTags", docCommentTree.getBlockTags().toString());
            props.put("rawDocString", docCommentTree.toString());

            // System.out.println("Element (" + e.getKind() + ": "
            // + e + ") has the following comments:");
            // System.out.println("Entire body: " + docCommentTree.getFullBody());
            // System.out.println("Block tags: " + docCommentTree.getBlockTags());
            // System.out.println("hey: " + docCommentTree.toString());
        }

        return props;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    String classPathString;

    public Set<? extends Option> getSupportedOptions() {
        Option[] options = {
                new Option() {
                    private final List<String> someOption = List.of(
                            "-author",
                            "-charset",
                            "-d",
                            "-docencoding",
                            "-doctitle",
                            "-use",
                            "-windowtitle",
                            "-bottom",
                            "-tag",
                            "-protected");

                    public int getArgumentCount() {
                        return 1;
                    }

                    public String getDescription() {
                        return "an option with aliases";
                    }

                    public Option.Kind getKind() {
                        return Option.Kind.STANDARD;
                    }

                    public List<String> getNames() {
                        return someOption;
                    }

                    public String getParameters() {
                        return "file";
                    }

                    public boolean process(String opt, List<String> arguments) {
                        return true;
                    }
                }
        };

        return Set.of(options);
    }

    public SourceVersion getSupportedSourceVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean run(DocletEnvironment environment) {
        // get the DocTrees utility class to access document comments
        DocTrees docTrees = environment.getDocTrees();

        ArrayList<HashMap<String, Object>> docComments = new ArrayList<HashMap<String, Object>>();

        for (TypeElement t : ElementFilter.typesIn(environment.getIncludedElements())) {
            System.out.println(t.getKind() + ":" + t);
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
            System.out.println("IDK something bad here" + e1);
        }

        return true;
    }

    public static void writeJson(File f, Object o) throws IOException {
        if (f.exists())
            f.delete();
        if (!f.createNewFile())
            throw new IOException("Cant create file " + f.getName());
        if (!f.canWrite())
            throw new IOException("Hey bud let me write to " + f.getName());

        FileWriter fw = new FileWriter(f);

        // Try with ObjectMapper?
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(o);

        fw.write(json);
        fw.flush();
        fw.close();
    }
}
