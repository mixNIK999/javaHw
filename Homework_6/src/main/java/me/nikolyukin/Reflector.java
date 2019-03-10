package me.nikolyukin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
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
        declarationBuilder.append("class ");
        declarationBuilder.append(someClass.getSimpleName());

        appendTypeParameters(declarationBuilder, someClass.getTypeParameters());
        declarationBuilder.append(" ");

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

    private static void appendTypeParameters(@NotNull StringBuilder builder, @NotNull TypeVariable<?>[] typeParameters) {
        if (typeParameters.length != 0) {
            builder.append("<");
            boolean isFirst = true;
            for (var param : typeParameters) {
                if (!isFirst) {
                    builder.append(",");
                }
                builder.append(param.getTypeName());
                isFirst = false;
            }
            builder.append(">");
        }
    }

    private static void appendParameters(@NotNull StringBuilder builder, @NotNull Type[] typeParameters) {
        builder.append("(");
        for (int i = 0; i < typeParameters.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(typeParameters[i].getTypeName()).append(" v").append(i);
        }
        builder.append(")");
    }

    private static void appendThrows(@NotNull StringBuilder builder, @NotNull Type[] typeParameters) {
        if (typeParameters.length != 0) {
            builder.append("throws ");
            for (int i = 0; i < typeParameters.length; i++) {
                if (i != 0) {
                    builder.append(", ");
                }
                builder.append(typeParameters[i].getTypeName());
            }
        }
    }

    @NotNull
    private static String methodToString(@NotNull Method someMethod) {
//        return someMethod.toGenericString();
        StringBuilder methodBuilder = new StringBuilder();
        var mods = modifiersToString(someMethod.getModifiers());
        if (mods.length() != 0) {
            methodBuilder.append(mods).append(" ");
        }
        appendTypeParameters(methodBuilder, someMethod.getTypeParameters());
        methodBuilder.append(" ");
        methodBuilder.append(someMethod.getGenericReturnType()).append(" ");
        methodBuilder.append(someMethod.getName());
        appendParameters(methodBuilder, someMethod.getGenericParameterTypes());
        methodBuilder.append(" ");
        appendThrows(methodBuilder, someMethod.getGenericExceptionTypes());
        methodBuilder.append(" {throw new Error();}");

        return methodBuilder.toString();
    }

    @NotNull
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
        StringBuilder fieldBuilder = new StringBuilder();
        var mods = modifiersToString(someField.getModifiers());
        if (mods.length() != 0) {
            fieldBuilder.append(modifiersToString(someField.getModifiers())).append(" ");
        }
        fieldBuilder.append(someField.getGenericType().getTypeName()).append(" ");
        fieldBuilder.append(someField.getName()).append(";");
        return fieldBuilder.toString();
    }

    public static void diffClasses(Class<?> a, Class<?> b) {}

}
