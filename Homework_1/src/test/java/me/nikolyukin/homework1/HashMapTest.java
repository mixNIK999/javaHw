package me.nikolyukin.homework1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    private HashMap hashMap;

    @BeforeEach
    void initialHashMap() {
        hashMap = new HashMap(5);
    }

    @Test
    void sizeWhenEmpty() {
        assertEquals(0, hashMap.size());
    }

    @Test
    void sizeWhenAdd1Element() {
        hashMap.put("a", "b");
        assertEquals(1, hashMap.size());
    }

    @Test
    void sizeWhenAdd2Remove1Elements() {
        hashMap.put("a", "b");
        hashMap.put("c", "d");
        hashMap.remove("c");
        assertEquals(1, hashMap.size());
    }


    @Test
    void containsWhenEmpty() {
        assertFalse(hashMap.contains("a"));
    }

    @Test
    void containsWhenNot() {
        hashMap.put("a", "b");
        assertFalse(hashMap.contains("b"));
    }

    @Test
    void containsWhenYes() {
        hashMap.put("a", "b");
        assertTrue(hashMap.contains("a"));
    }

    @Test
    void getWhenEmpty() {
        assertNull(hashMap.get("a"));
    }

    @Test
    void getWhenHasNot() {
        hashMap.put("a", "b");
        assertNull(hashMap.get("c"));
    }

    @Test
    void getWhenHas() {
        hashMap.put("a", "b");
        assertEquals("b",hashMap.get("a"));
    }


    @Test
    void putWhenEmpty() {
        assertNull(hashMap.put("a", "b"));
    }

    @Test
    void putWhenHas() {
        hashMap.put("a", "b");
        assertEquals("b", hashMap.put("a", "c"));
    }

    @Test
    void putWhenHasNot() {
        hashMap.put("a", "b");
        assertNull(hashMap.put("c", "d"));
    }

    @Test
    void removeWhenEmpty() {
        assertNull(hashMap.remove("a"));
    }

    @Test
    void removeWhenHas() {
        hashMap.put("a", "b");
        assertEquals("b", hashMap.remove("a"));
    }

    @Test
    void removeWhenHasNot() {
        hashMap.put("a", "b");
        assertNull(hashMap.remove("c"));
    }

    @Test
    void clearWhenEmpty() {
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }

    @Test
    void clearWhenNotEmpty() {
        hashMap.put("a", "b");
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }
}