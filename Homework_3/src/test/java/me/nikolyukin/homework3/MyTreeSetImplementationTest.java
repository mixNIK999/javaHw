package me.nikolyukin.homework3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyTreeSetImplementationTest {
    private MyTreeSet<Integer> emptyTreeSet;
    private MyTreeSet<Integer> TreeSet532;
    @BeforeEach
    void init() {
        emptyTreeSet = new MyTreeSetImplementation<>();
        TreeSet532 = new MyTreeSetImplementation<>(Comparator.reverseOrder());
        TreeSet532.add(2);
        TreeSet532.add(5);
        TreeSet532.add(3);
    }

    @Test
    void iteratorHasNextFalse() {
        assertFalse(emptyTreeSet.iterator().hasNext());
    }

    @Test
    void iteratorHasNextTrue() {
        assertTrue(TreeSet532.iterator().hasNext());
    }

    @Test
    void size() {
    }

    @Test
    void add() {
    }

    @Test
    void descendingIterator() {
    }

    @Test
    void descendingSet() {
    }

    @Test
    void first() {
    }

    @Test
    void last() {
    }

    @Test
    void lower() {
    }

    @Test
    void floor() {
    }

    @Test
    void ceiling() {
    }

    @Test
    void higher() {
    }
}