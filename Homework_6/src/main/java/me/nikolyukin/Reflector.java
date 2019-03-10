package me.nikolyukin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import org.jetbrains.annotations.NotNull;

public class Reflector {

    public static void printStructure(@NotNull Class<?> someClass) throws IOException {
        try (var out = new BufferedWriter(new FileWriter(someClass.getName() + ".java"))) {
            printStructure(someClass, out);
        }
    }

    private static void printStructure(@NotNull Class<?> someClass, @NotNull BufferedWriter out)
        throws IOException {

        out.write(declarationToString(someClass));

        out.write("{\n\n");

        for (Field someField : someClass.getDeclaredFields()) {
            out.write(fieldToString(someField) + "\n");
        }
        out.write("\n");
        for (Method someMethod : someClass.getDeclaredMethods()) {
            out.write(methodToString(someMethod) + "\n");
        }
        out.write("}");
    }

    @NotNull
    private static String declarationToString(@NotNull Class<?> someClass) {
        StringBuilder declarationBuilder = new StringBuilder();
        var mods = modifiersToString(someClass.getModifiers());
        if (mods.length() != 0) {
            declarationBuilder.append(mods).append(" ");
        }
        declarationBuilder.append(someClass.getSimpleName());

        TypeVariable<?>[] parameters = someClass.getTypeParameters();
        if (parameters.length != 0) {
            declarationBuilder.append("<");
            boolean isFirst = true;
            for (var param : parameters) {
                if (!isFirst) {
                    declarationBuilder.append(",");
                }
                declarationBuilder.append(param.getTypeName());
                isFirst = false;
            }
            declarationBuilder.append("> ");
        }
        var superClass = someClass.getSuperclass();
        if (superClass != null) {
            declarationBuilder.append("extends ").append(superClass.getName()).append(" ");
        }

        var interfaces = someClass.getInterfaces();
        if (interfaces.length != 0) {
            declarationBuilder.append("implements ");
            for (var someInterface : interfaces) {
                declarationBuilder.append(someInterface.getName()).append(" ");
            }
        }
        return declarationBuilder.toString();
    }

    private static String methodToString(@NotNull Method someMethod) {
        return someMethod.toGenericString();
    }

    private static String modifiersToString(int mods) {
        return Modifier.toString(mods);
//        if (Modifier.isPublic(mods)) {
//            out.write("public ");
//        }
//        if (Modifier.isProtected(mods)) {
//            out.write("protected ");
//        }
//        if (Modifier.isPrivate(mods)) {
//            out.write("private ");
//        }
//        if (Modifier.isStatic(mods)) {
//            out.write("static ");
//        }
    }

    @NotNull
    private static String fieldToString(@NotNull Field someField) {
//        someField.setAccessible(true);
//        printModifiers(someField.getModifiers(), out);
//        out.write(someField.getType().getName() + " ");
//        return someField.toGenericString();
        StringBuilder fieldBuilder = new StringBuilder();
        var mods = modifiersToString(someField.getModifiers());
        if (mods.length() != 0) {
            fieldBuilder.append(modifiersToString(someField.getModifiers())).append(" ");
        }
        fieldBuilder.append(someField.getGenericType().getTypeName()).append(" ");
        fieldBuilder.append(someField.getName());
        return fieldBuilder.toString();
    }

    public static void diffClasses(Class<?> a, Class<?> b) {}

}
