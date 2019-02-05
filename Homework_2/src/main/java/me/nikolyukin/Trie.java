package me.nikolyukin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

public class Trie implements MySerializable {

    boolean add(String element) {

    }

    boolean contains(String element) {

    }

    boolean remove(String element) {

    }

    int size() {

    }

    int howManyStartsWithPrefix(String prefix) {

    }

    @Override
    public void serialize(OutputStream out) throws IOException {

    }

    @Override
    public void deserialize(InputStream in) throws IOException {

    }

    private static class Node {
        private LinkedHashMap<Character, Node> children;
        private Node parent;
        private Character parantCharacter;

        private Node() {}
        private Node(Node parent, Character parentCharacter) {
            this.parent = parent;
            this.parantCharacter = parentCharacter;
        }

        private int childrenCount() {
            return children.size();
        }
    }
}

