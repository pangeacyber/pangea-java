package cloud.pangeacyber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class JsonDoclet implements Doclet {
    @Override
    public void init(Locale locale, Reporter reporter) {
        // TODO Auto-generated method stub

    }

    public HashMap<String,String> printElement(DocTrees trees, Element e) {
        HashMap<String,String> props = new HashMap<>();

        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            // Usually: "METHOD" with a value of the method signature
            props.put(e.getKind().toString(), e.toString());
            props.put("summary", docCommentTree.getFullBody().toString());
            props.put("blockTags", docCommentTree.getBlockTags().toString());
            props.put("rawDocString", docCommentTree.toString());

            // System.out.println("Element (" + e.getKind() + ": "
            //         + e + ") has the following comments:");
            // System.out.println("Entire body: " + docCommentTree.getFullBody());
            // System.out.println("Block tags: " + docCommentTree.getBlockTags());
            // System.out.println("hey: " + docCommentTree.toString());
        }

        return props;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
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

                    @Override
                    public int getArgumentCount() {
                        return 1;
                    }

                    @Override
                    public String getDescription() {
                        return "an option with aliases";
                    }

                    @Override
                    public Option.Kind getKind() {
                        return Option.Kind.STANDARD;
                    }

                    @Override
                    public List<String> getNames() {
                        return someOption;
                    }

                    @Override
                    public String getParameters() {
                        return "file";
                    }

                    @Override
                    public boolean process(String opt, List<String> arguments) {
                        return true;
                    }
                }
        };

        return Set.of(options);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        // get the DocTrees utility class to access document comments
        DocTrees docTrees = environment.getDocTrees();

        ArrayList<HashMap<String,String>> docComments = new ArrayList<HashMap<String,String>>();
        
        for (TypeElement t : ElementFilter.typesIn(environment.getIncludedElements())) {
            // System.out.println(t.getKind() + ":" + t);

            for (Element e : t.getEnclosedElements()) {
                HashMap<String, String> docComment = printElement(docTrees, e);

                if (!docComment.isEmpty()) docComments.add(docComment);
            }
        }

        try {
            writeJson(new File("docs.json"),docComments);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("IDK something bad here" + e1);
        }

        return true;
    }

    public static void writeJson(File f, Object o) throws IOException {
        if(f.exists())f.delete();
        if(!f.createNewFile()) throw new IOException("Cant create file "+f.getName());
        if(!f.canWrite()) throw new IOException("Hey bud let me write to "+f.getName());

        FileWriter fw = new FileWriter(f);

        // Try with ObjectMapper?
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(o);

        // Try with Gson?
        // Gson gson = new Gson();
        // String json = gson.toJson(o);

        fw.write(json);
        fw.flush();
        fw.close();
    }

    // private static HashMap<String,Object> getObjectData(List<HashMap<String,Object>> docComments) {

    // }
}
