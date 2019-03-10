package me.nikolyukin;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import org.jetbrains.annotations.NotNull;

public class Reflector {

    public static void printStructure(@NotNull Class<?> someClass) throws IOException {
        try (var out = new BufferedWriter(new FileWriter(someClass.getSimpleName() + ".java"))) {
            printStructure(someClass, out);
        }
    }

    public static void printStructure(@NotNull Class<?> someClass, BufferedWriter out) throws IOException {
        int mods = someClass.getModifiers();
        if (Modifier.isPublic(mods)) {
            out.write("public ");
        }
        if (Modifier.isStatic(mods)) {
            out.write("static ");
        }
        if (Modifier.isFinal(mods)) {
            out.write("final ");
        }
        out.write("class " + someClass.getSimpleName() + " ");

        var superClass = someClass.getSuperclass();
        if (superClass != null) {
            out.write("extends " + superClass.getName() + " ");
        }

        var interfaces = someClass.getInterfaces();
        if (interfaces.length != 0) {
            out.write("implements ");
            for (var implementedInterface : interfaces) {
                out.write(implementedInterface.getName() + " ");
            }
        }
        out.write("{");

        out.write("}");
    }

//    private static void ()

    public static void diffClasses(Class<?> a, Class<?> b) {}

}
