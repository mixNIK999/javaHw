package me.nikolyukin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.jetbrains.annotations.NotNull;

/**
 * Класс предоставляет статические методы для записи (printStructure) и сравнения структуры классов (diffClasses).
 */
public class Reflector {

    /**
     * Метод создаёт файл с именем someClass.java.
     * В нём описан класс SomeClass со всеми полями, методами, внутренними и вложенными классами и интерфейсами.
     * Методы без реализации.
     * Модификаторы такие же, как в переданном классе.
     * Объявления полей, методов и вложенных классов сохраняет генериковость.
     *
     * @param someClass класс, структуру которого мы записываем. NotNull
     * @throws IOException если произошла ошибка при работе с файлом *someClass*.java
     */
    public static void printStructure(@NotNull Class<?> someClass) throws IOException {
        try (var out = new BufferedWriter(new FileWriter(someClass.getSimpleName() + ".java"))) {
            printStructure(someClass, out);
        }
    }

    /**
     * Метод возвращает все поля и методы, различающиеся в двух классах в форме:<br>
     * Unique fields in a:<br>
     * *описание полей a без аналогичных в b по одному в строке*<br>
     *<br>
     * Unique fields in b:<br>
     * *описание полей b без аналогичных в b по одному в строке*<br>
     *<br>
     * Unique methods in a:<br>
     * *описание методов a без аналогичных в b по одному в строке*<br>
     *<br>
     * Unique methods in b:<br>
     * *описание методов b без аналогичных в a по одному в строке*<br>
     *
     * @param a первый класс который мы сравниваем
     * @param b второй класс который мы сравниваем
     * @return отличия в формате описанном выше
     */
    @NotNull
    public static String diffClasses(@NotNull Class<?> a, @NotNull Class<?> b) {
        var diffBuilder = new StringBuilder();

        var aFields = getAllFields(a);
        var bFields = getAllFields(b);
        var aMethods = getAllMethods(a);
        var bMethods = getAllMethods(b);

        diffBuilder.append("Unique fields in a:\n");
        appendDifference(diffBuilder, aFields, bFields);
        diffBuilder.append("\nUnique fields in b:\n");
        appendDifference(diffBuilder, bFields, aFields);
        diffBuilder.append("\nUnique methods in a:\n");
        appendDifference(diffBuilder, aMethods, bMethods);
        diffBuilder.append("\nUnique methods in b:\n");
        appendDifference(diffBuilder, bMethods, aMethods);

        return diffBuilder.toString();
    }

    private static void printStructure(@NotNull Class<?> someClass, @NotNull BufferedWriter out)
        throws IOException {

        out.write(declarationToString(someClass));

        out.write("{\n\n");

        for (Field someField : someClass.getDeclaredFields()) {
            out.write(fieldToString(someField) + "\n");
        }
        out.write("\n");

        for(Constructor someConstructor: someClass.getDeclaredConstructors()) {
            out.write(constructorToString(someConstructor) + "\n");
        }
        out.write("\n");

        for (Method someMethod : someClass.getDeclaredMethods()) {
            out.write(methodToString(someMethod) + "\n");
        }
        out.write("\n");

        for (Class classIn : someClass.getDeclaredClasses()) {
            printStructure(classIn, out);
            out.write("\n");
        }
        out.write("}");
    }

    @NotNull
    private static String declarationToString(@NotNull Class<?> someClass) {
//        return someClass.toGenericString();
        StringBuilder declarationBuilder = new StringBuilder();
        var mods = modifiersToString(someClass.getModifiers());
        if (mods.length() != 0) {
            declarationBuilder.append(mods).append(" ");
        }
        if (!someClass.isInterface()) {
            declarationBuilder.append("class ");
        }
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
    private static String constructorToString(@NotNull Constructor someConstructor) {
        StringBuilder methodBuilder = new StringBuilder();
        var mods = modifiersToString(someConstructor.getModifiers());
        if (mods.length() != 0) {
            methodBuilder.append(mods).append(" ");
        }
        appendTypeParameters(methodBuilder, someConstructor.getTypeParameters());
        methodBuilder.append(" ");
        methodBuilder.append(someConstructor.getDeclaringClass().getSimpleName());
        appendParameters(methodBuilder, someConstructor.getGenericParameterTypes());
        methodBuilder.append(" ");
        appendThrows(methodBuilder, someConstructor.getGenericExceptionTypes());
        methodBuilder.append(" {}");

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

    private static void appendDifference(@NotNull StringBuilder builder, @NotNull HashSet<String> a,
        @NotNull HashSet<String> b) {
        for (var str : a) {
            if (!b.contains(str)) {
                builder.append(str).append("\n");
            }
        }
    }

    private static HashSet<String> getAllFields(@NotNull Class<?> someClass) {
        var fields = new HashSet<String>();
        for (Field someField : someClass.getDeclaredFields()) {
            fields.add(fieldToString(someField));
        }
        return fields;
    }

    private static HashSet<String> getAllMethods(@NotNull Class<?> someClass) {
        var methods = new LinkedHashSet<String>();
        for (Method someMethod : someClass.getDeclaredMethods()) {
            methods.add(methodToString(someMethod));
        }
        return methods;
    }

}
