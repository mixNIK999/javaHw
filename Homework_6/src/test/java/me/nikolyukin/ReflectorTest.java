package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ReflectorTest {

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