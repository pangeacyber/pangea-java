package cloud.pangeacyber;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class JsonDoclet implements Doclet {
    private Reporter reporter;
    private PrintWriter stdout;

    @Override
    public void init(Locale locale, Reporter reporter) {
        // TODO Auto-generated method stub

    }

    public void printElement(DocTrees trees, Element e) {
        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            System.out.println("Element (" + e.getKind() + ": "
                    + e + ") has the following comments:");
            System.out.println("Entire body: " + docCommentTree.getFullBody());
            System.out.println("Block tags: " + docCommentTree.getBlockTags());
            System.out.println("hey: " + docCommentTree.toString());
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    private String author;
    private String charset;

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
                        author = arguments.get(0);
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

        // This method is called to perform the work of the doclet.
        // // In this case, it just prints out the names of the
        // // elements specified on the command line.
        // environment.getSpecifiedElements()
        // .forEach(System.out::println);

        // location of an element in the same directory as overview.html
        try {
            Element e = ElementFilter.typesIn(environment.getSpecifiedElements()).iterator().next();
            DocCommentTree docCommentTree = docTrees.getDocCommentTree(e);
            if (docCommentTree != null) {
                stdout.println("Overview html: " + docCommentTree.getFullBody());
            }
        } catch (NoSuchElementException missing) {
            System.out.println(Kind.ERROR + "Idk something happened");
        }

        for (TypeElement t : ElementFilter.typesIn(environment.getIncludedElements())) {
            System.out.println(t.getKind() + ":" + t);
            for (Element e : t.getEnclosedElements()) {
                printElement(docTrees, e);
            }
        }

        System.out.println("hello world");

        return true;
    }
}

/**
 * A minimal doclet that just prints out the names of the
 * selected elements.
 */
// public class JsonDoclet implements Doclet {
// @Override
// public void init(Locale locale, Reporter reporter) { }

// @Override
// public String getName() {
// // For this doclet, the name of the doclet is just the
// // simple name of the class. The name may be used in
// // messages related to this doclet, such as in command-line
// // help when doclet-specific options are provided.
// return getClass().getSimpleName();
// }

// @Override
// public Set<? extends Option> getSupportedOptions() {
// // This doclet does not support any options.
// return Collections.emptySet();
// }

// @Override
// public SourceVersion getSupportedSourceVersion() {
// // This doclet supports all source versions.
// // More sophisticated doclets may use a more
// // specific version, to ensure that they do not
// // encounter more recent language features that
// // they may not be able to handle.
// return SourceVersion.latest();
// }

// private static final boolean OK = true;

// @Override
// public boolean run(DocletEnvironment environment) {
// // This method is called to perform the work of the doclet.
// // In this case, it just prints out the names of the
// // elements specified on the command line.
// environment.getSpecifiedElements()
// .forEach(System.out::println);
// return OK;
// }
// }
