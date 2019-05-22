package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.junit.jupiter.api.Test;

class ReflectorTest {

    final String noDiff = "Unique fields in a:\n"
        + "\n"
        + "Unique fields in b:\n"
        + "\n"
        + "Unique methods in a:\n"
        + "\n"
        + "Unique methods in b:"
        + "\n";


    @Test
    void diffSame() {
        assertEquals(noDiff, Reflector.diffClasses(ArrayList.class, ArrayList.class));
    }

    @Test
    void diffNotSame() {
        assertNotEquals(noDiff, Reflector.diffClasses(TestGenericClass.class, ArrayList.class));
    }

    @Test
    void printAndDiff() throws IOException, ClassNotFoundException {
        var fileJava = new File("TestGenericClass.java");
        var fileClass = new File("TestGenericClass.class");
        try {
            Reflector.printStructure(TestGenericClass.class);
            DiagnosticCollector<JavaFileObject> diagnostics =
                new DiagnosticCollector<JavaFileObject>();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager
                fileManager = compiler.getStandardFileManager(diagnostics, null, null);

            List<String> optionList = new ArrayList<>();
            optionList.add("-classpath");
            optionList.add("TestGenericClass.java");

            Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(fileJava));

            CompilationTask task = compiler.getTask(
                null,
                fileManager,
                null,
                optionList,
                null,
                compilationUnit);
            assertTrue(task.call());
            URLClassLoader classLoader =
                new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
            Class c = classLoader.loadClass("TestGenericClass");
            assertEquals(noDiff, Reflector.diffClasses(c, TestGenericClass.class));
        } finally {
            fileJava.delete();
            fileClass.delete();
        }
    }
}