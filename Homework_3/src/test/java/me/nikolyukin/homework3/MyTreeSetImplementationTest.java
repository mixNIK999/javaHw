package me.nikolyukin.homework3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyTreeSetImplementationTest {
    private MyTreeSet<Integer> emptyTreeSet;
    private MyTreeSet<Integer> treeSet532;
    @BeforeEach
    void init() {
        emptyTreeSet = new MyTreeSetImplementation<>();
        treeSet532 = new MyTreeSetImplementation<>(Comparator.reverseOrder());
        treeSet532.add(2);
        treeSet532.add(5);
        treeSet532.add(3);
    }

    @Test
    void iteratorHasNextFalse() {
        assertFalse(emptyTreeSet.iterator().hasNext());
    }

    @Test
    void iteratorHasNextTrue() {
        assertTrue(treeSet532.iterator().hasNext());
    }

    @Test
    void iteratorNext532() {
        var iterator = treeSet532.iterator();
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
        assertEquals(3, treeSet532.size());
    }

    @Test
    void addTrue() {
        assertTrue(treeSet532.add(8));
        assertEquals(4, treeSet532.size());
    }

    @Test
    void addFalse() {
        assertFalse(treeSet532.add(5));
        assertEquals(3, treeSet532.size());
    }

    @Test
    void descendingIteratorHasNextFalse() {
        assertFalse(emptyTreeSet.descendingIterator().hasNext());
    }

    @Test
    void descendingIteratorHasNextTrue() {
        assertTrue(treeSet532.descendingIterator().hasNext());
    }

    @Test
    void descendingIteratorNext532() {
        var descendingIterator = treeSet532.descendingIterator();
        assertEquals(Integer.valueOf(2), descendingIterator.next());
        assertEquals(Integer.valueOf(3), descendingIterator.next());
        assertEquals(Integer.valueOf(5), descendingIterator.next());
    }
    
    @Test
    void descendingSet() {
        var iterator = treeSet532.descendingSet().iterator();
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
    }

    @Test
    void firstNull() {
        assertNull(emptyTreeSet.first());
    }

    @Test
    void firstNotNull() {
        assertEquals(Integer.valueOf(5), treeSet532.first());
    }

    @Test
    void lastNull() {
        assertNull(emptyTreeSet.last());
    }

    @Test
    void lastNotNull() {
        assertEquals(Integer.valueOf(2), treeSet532.last());
    }

    @Test
    void lowerNull() {
        assertNull(emptyTreeSet.lower(1));
    }

    @Test
    void lowerNotNull() {
        assertEquals(Integer.valueOf(5), treeSet532.lower(3));
    }

    @Test
    void floorNull() {
        assertNull(emptyTreeSet.floor(1));
    }

    @Test
    void floorNotNull() {
        assertEquals(Integer.valueOf(3), treeSet532.floor(3));
    }

    @Test
    void ceilingNull() {
        assertNull(emptyTreeSet.ceiling(1));
    }

    @Test
    void ceilingNotNull() {
        assertEquals(Integer.valueOf(3), treeSet532.ceiling(3));
    }

    @Test
    void higherNull() {
        assertNull(emptyTreeSet.higher(1));
    }

    @Test
    void higherNotNull() {
        assertEquals(Integer.valueOf(2), treeSet532.higher(3));
    }
}