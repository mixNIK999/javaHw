package me.nikolyukin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    private Trie emptyTrie;
    private Trie heSheHisHers;

    @BeforeEach
    void initial() {
        emptyTrie = new Trie();
        heSheHisHers = new Trie();
        heSheHisHers.add("he");
        heSheHisHers.add("she");
        heSheHisHers.add("his");
        heSheHisHers.add("hers");
    }

    @Test
    void sizeFromEmpty() {
        assertEquals(0, emptyTrie.size());
    }

    @Test
    void add1ToEmpty() {
        emptyTrie.add("a");
        assertEquals(1, emptyTrie.size());
    }

    @Test
    void add4ToEmpty() {
        assertEquals(4, heSheHisHers.size());
    }

    @Test
    void containsInEmpty() {
        assertFalse(emptyTrie.contains(""));
    }

    @Test
    void containsInNotEmptyFalse() {
        assertFalse(heSheHisHers.contains("false"));
    }

    @Test
    void containsInNotEmptyTrue1() {
        assertTrue(heSheHisHers.contains("he"));
    }

    @Test
    void containsInNotEmptyTrue2() {
        assertTrue(heSheHisHers.contains("his"));
    }

    @Test
    void removeFromEmpty() {
        assertFalse(emptyTrie.remove(""));
    }

    @Test
    void removeFromNotEmptyFalse() {
        assertFalse(heSheHisHers.remove("hi"));
    }

    @Test
    void removeFromNotEmptyTrue() {
        assertTrue(heSheHisHers.remove("he"));
    }

    @Test
    void removeFromNotEmptyMinusSize() {
        heSheHisHers.remove("his");
        assertEquals(3, heSheHisHers.size());
    }

    @Test
    void howManyStartsWithPrefixFromEmpty() {
        assertEquals(0, emptyTrie.howManyStartsWithPrefix(""));
    }

    @Test
    void howManyStartsWithPrefixFromNotEmpty() {
        assertEquals(3, heSheHisHers.howManyStartsWithPrefix("h"));
    }

    @Test
    void serialize() {
    }

    @Test
    void deserialize() {
    }
}