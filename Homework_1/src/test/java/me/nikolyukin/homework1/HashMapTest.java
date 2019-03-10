package me.nikolyukin.homework1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    private HashMap hashMap;
    private HashMap smallHashMapWithHugeCollection;

    @BeforeEach
    void initialHashMap() {
        hashMap = new HashMap(5);
        smallHashMapWithHugeCollection = new HashMap(1);
        for (char c = 'a'; c <= 'z'; c++) {
            smallHashMapWithHugeCollection.put(Character.toString(c), Character.toString(c));
        }
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
    void containsWhenHasNot() {
        hashMap.put("a", "b");
        assertFalse(hashMap.contains("b"));
    }

    @Test
    void containsWhenHas() {
        hashMap.put("a", "b");
        assertTrue(hashMap.contains("a"));
    }

    @Test
    void containsWhenManyCollisionsTrue() {
        for (char c = 'a'; c <= 'z'; c++) {
            assertTrue(smallHashMapWithHugeCollection.contains(Character.toString(c)));
        }
    }

    @Test
    void containsWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> {hashMap.contains(null);});
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
    void getWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> {hashMap.get(null);});
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
    void putWhenManyCollisions() {
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(Character.toString(c), smallHashMapWithHugeCollection.put(Character.toString(c), "newValue"));

        }
    }

    @Test
    void putWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> {hashMap.put(null, null);});
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
    void removeWhenManyCollisions() {
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(Character.toString(c), smallHashMapWithHugeCollection.remove(Character.toString(c)));
        }
    }

    @Test
    void removeWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> {hashMap.remove(null);});
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

    @Test
    void getCapacityWhen5() {
        assertEquals(5, hashMap.getCapacity());
    }

    @Test
    void resizeWhenCollisions() {
        smallHashMapWithHugeCollection.resize(100);
        assertEquals(100, smallHashMapWithHugeCollection.getCapacity());
        assertEquals('z' - 'a' + 1, smallHashMapWithHugeCollection.size());
    }
}