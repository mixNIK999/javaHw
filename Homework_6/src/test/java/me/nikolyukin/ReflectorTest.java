package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    void printAndDiff() throws IOException {
        var file = new File("TestGenericClass.java");
        try {
            Reflector.printStructure(TestGenericClass.class);
        } finally {
            file.delete();
        }
    }
    @Test
    void printStructure() throws IOException {
        Reflector.printStructure(TestGenericClass.class);
        Reflector.printStructure(ArrayList.class);
        Reflector.printStructure(AutoCloseable.class);
    }

    @Test
    void diffClasses() {
        System.out.println(Reflector.diffClasses(ArrayList.class, TestGenericClass.class));
        System.out.println(Reflector.diffClasses(ArrayList.class, ArrayList.class));
    }
}