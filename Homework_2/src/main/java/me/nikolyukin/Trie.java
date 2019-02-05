package me.nikolyukin;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

public class Trie implements MySerializable {
    private Node root = new Node();

    @Nullable
    @Contract("_, true -> !null")
    private Node goToNode(@NotNull String element, boolean pushNew) {
        var currentNode = root;
        for (Character c : element) {
            var next = currentNode.children.get(c);
            if (next == null) {
                if (!pushNew) {
                    return null;
                }
                next = new Node(currentNode, c);
                currentNode.children.put(c, next);
            }
            currentNode = next;
        }
        return currentNode;
    }

    public boolean add(@NotNull String element) {
        var lastNode = goToNode(element, true);
        if (lastNode.isTerminal) {
            return false;
        }
        lastNode.isTerminal = true;
        while(lastNode != null) {
            lastNode.suffixCount++;
            lastNode = lastNode.parent;
        }
        return true;
    }

    public boolean contains(@NotNull String element) {
        var lastNode = goToNode(element, false);
        return (lastNode != null && lastNode.isTerminal);
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
        private boolean isTerminal = false;

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

