package me.nikolyukin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

public class Trie implements MySerializable {
    private Node root = new Node();

    public boolean add(String element) {

    }

    public boolean contains(String element) {

    }

    public boolean remove(String element) {

    }

    public int size() {
        return root.suffixCount;
    }

    public int howManyStartsWithPrefix(String prefix) {

    }

    @Override
    public void serialize(OutputStream out) throws IOException {

    }

    @Override
    public void deserialize(InputStream in) throws IOException {

    }

    private static class Node {
        private LinkedHashMap<Character, Node>  children
                = new  LinkedHashMap<Character, Node>();
        private Node parent;
        private Character parantCharacter;
        private int suffixCount = 0;
        private bool isTerminal = false;

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

