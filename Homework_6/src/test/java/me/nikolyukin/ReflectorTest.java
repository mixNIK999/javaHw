package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ReflectorTest {

    @Test
    void printStructure() throws IOException {
        Reflector.printStructure(ArrayList.class);
    }

    @Test
    void diffClasses() {
    }
}