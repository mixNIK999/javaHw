package me.nikolyukin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    void add1ToEmptyTrue() {
        assertTrue(emptyTrie.add("a"));
        assertEquals(1, emptyTrie.size());
    }

    @Test
    void addToNotEmptyFalse() {
        assertFalse(heSheHisHers.add("he"));
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
    void containsInNotEmptyTrue() {
        assertTrue(heSheHisHers.contains("he"));
        assertTrue(heSheHisHers.contains("she"));
        assertTrue(heSheHisHers.contains("his"));
        assertTrue(heSheHisHers.contains("hers"));
    }


    @Test
    void containsEmptyStringTrue() {
        emptyTrie.add("");
        assertTrue(emptyTrie.contains(""));
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
    void serializeEmptyDeserializeToFull() throws IOException {
        var out = new ByteArrayOutputStream(1000);
        emptyTrie.serialize(out);
        byte[] data = out.toByteArray();
        var in = new ByteArrayInputStream(data);
        heSheHisHers.deserialize(in);
        assertEquals(0, heSheHisHers.size());
    }

    @Test
    void serializeFullDeserializeToEmpty() throws IOException {
        var out = new ByteArrayOutputStream(1000);
        heSheHisHers.serialize(out);
        byte[] data = out.toByteArray();
        var in = new ByteArrayInputStream(data);
        emptyTrie.deserialize(in);
        assertEquals(4, emptyTrie.size());
        assertEquals(3, emptyTrie.howManyStartsWithPrefix("h"));
    }

    @Test
    void deserializeWithException() throws IOException {
        var in = new ByteArrayInputStream(new byte[0]);
        assertThrows(IOException.class, () -> {emptyTrie.deserialize(in);});
    }
}