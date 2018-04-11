package no.ntnu.vislab.vislabcontroller.annotations.processors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import no.ntnu.vislab.vislabcontroller.annotations.ProjectorSPI;

@SuppressWarnings({"Since15"})
@SupportedAnnotationTypes("no.ntnu.vislab.vislabcontroller.annotations.ProjectorSPI")
public class ProjectorAnnotationProcessor extends AbstractProcessor {
    private static final String PATH = "META-INF/services/";
    private static final String CONTRACT = "no.ntnu.vislab.vislabcontroller.providers.Projector";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) return false;
        // TODO should not write anything until processingOver
        Elements elements = processingEnv.getElementUtils();
        for (Element e : roundEnv.getElementsAnnotatedWith(ProjectorSPI.class)) {
            TypeElement type = (TypeElement) e;
            Set<String> lines = new HashSet<>();
            lines.add(elements.getBinaryName(type).toString());
            Filer filer = processingEnv.getFiler();
            try {
                FileObject f = filer.getResource(StandardLocation.CLASS_OUTPUT, "", PATH + CONTRACT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(f.openInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
            } catch (FileNotFoundException | java.nio.file.NoSuchFileException x) {
                //file does not exist
            } catch (IOException x) {
                processingEnv.getMessager().printMessage(Kind.ERROR, "Failed to load existing service definition files: " + x);
            }
            processingEnv.getMessager().printMessage(Kind.NOTE, PATH + CONTRACT);
            try {
                FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", PATH + CONTRACT);
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(fileObject.openOutputStream(), "UTF-8"));

                for (String line : lines) {
                    pw.println(line);
                }
                pw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        try {
            // Seems to work. Probably could use some additional error checks, but current code does not even verify that the class is assignable to an explicitly specified type!
            // Need to add unit tests. See stapler/stapler/core/src/test/java/org/kohsuke/stapler/jsr269/ for examples.
            return SourceVersion.valueOf("RELEASE_8");
        } catch (IllegalArgumentException x) {}
        try {
            return SourceVersion.valueOf("RELEASE_7");
        } catch (IllegalArgumentException x) {}
        return SourceVersion.RELEASE_6;
    }

}
