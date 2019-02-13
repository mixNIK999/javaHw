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
    void iteratorNext532() {
        var iterator = TreeSet532.iterator();
        assertEquals(Integer.valueOf(5), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
    }

    @Test
    void sizeEmpty() {
        assertEquals(0, emptyTreeSet.size());
    }

    @Test
    void sizeNotEmpty() {
        assertEquals(3, TreeSet532.size());
    }

    @Test
    void addTrue() {
        assertTrue(TreeSet532.add(8));
        assertEquals(4,TreeSet532.size());
    }

    @Test
    void addFalse() {
        assertFalse(TreeSet532.add(5));
        assertEquals(3,TreeSet532.size());
    }

    @Test
    void descendingIteratorHasNextFalse() {
        assertFalse(emptyTreeSet.descendingIterator().hasNext());
    }

    @Test
    void descendingIteratorHasNextTrue() {
        assertTrue(TreeSet532.descendingIterator().hasNext());
    }

    @Test
    void descendingIteratorNext532() {
        var descendingIterator = TreeSet532.descendingIterator();
        assertEquals(Integer.valueOf(2), descendingIterator.next());
        assertEquals(Integer.valueOf(3), descendingIterator.next());
        assertEquals(Integer.valueOf(5), descendingIterator.next());
    }
    
    @Test
    void descendingSet() {
        var iterator = TreeSet532.descendingSet().iterator();
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
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