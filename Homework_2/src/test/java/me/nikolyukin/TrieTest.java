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
    void contains() {
    }

    @Test
    void remove() {
    }

    @Test
    void howManyStartsWithPrefix() {
    }

    @Test
    void serialize() {
    }

    @Test
    void deserialize() {
    }
}