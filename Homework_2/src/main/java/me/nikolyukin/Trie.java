package me.nikolyukin;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Trie implements MySerializable {
    private Node root = new Node();


    public Trie() {}

    private Trie(Node root) {
        this.root = root;
    }

    @Nullable
    @Contract("_, true -> !null")
    private Node goToNode(@NotNull String element, boolean pushNew) {
        var currentNode = root;
        for (Character c : element.toCharArray()) {
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

    public boolean remove(@NotNull String element) {
        var lastNode = goToNode(element, false);
        if (lastNode == null || !lastNode.isTerminal) {
            return false;
        }

        lastNode.isTerminal = false;
        lastNode.suffixCount--;
        var lastChar = lastNode.parentCharacter;
        lastNode = lastNode.parent;
        while(lastNode != null) {
            lastNode.suffixCount--;
            if (lastNode.children.get(lastChar).suffixCount == 0) {
                lastNode.children.remove(lastChar);
            }
            lastChar = lastNode.parentCharacter;
            lastNode = lastNode.parent;
        }
        return true;
    }

    public int size() {
        return root.suffixCount;
    }

    public int howManyStartsWithPrefix(@NotNull String prefix) {
        var lastNode = goToNode(prefix, false);
        if (lastNode == null) {
            return 0;
        }
        return lastNode.suffixCount;
    }

    private void dfsSerialize (@NotNull ObjectOutputStream objectOut, @NotNull Node currentNode) throws IOException {
        objectOut.writeInt(currentNode.childrenCount());
        objectOut.writeBoolean(currentNode.isTerminal);
        for (var entry : currentNode.children.entrySet()) {
            objectOut.writeObject(entry.getKey());
            dfsSerialize(objectOut, entry.getValue());
        }
    }

    @Override
    public void serialize(@NotNull OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(out))){
            dfsSerialize(objectOut, root);
        }
    }

    private void dfsDeserialize (@NotNull ObjectInputStream objectIn, @NotNull Node currentNode) throws IOException {
        int n = objectIn.readInt();
        currentNode.isTerminal = objectIn.readBoolean();
        currentNode.suffixCount = (currentNode.isTerminal) ? 1: 0;
        for (int i = 0; i < n; i++) {
            char childChar = objectIn.readChar();
            Node nextNode = new Trie(currentNode).goToNode(Character.toString(childChar), true);
            dfsDeserialize(objectIn, nextNode);
            currentNode.suffixCount += (nextNode.isTerminal) ? 1 : 0;
        }
    }

    @Override
    public void deserialize(@NotNull InputStream in) throws IOException {
        root = new Node();
        try (var objectIn = new ObjectInputStream(new BufferedInputStream(in))){
            dfsDeserialize(objectIn, root);
        }
    }

    private static class Node {
        private Map<Character, Node> children
                = new  LinkedHashMap<Character, Node>();
        private Node parent;
        private Character parentCharacter;
        private int suffixCount = 0;
        private boolean isTerminal = false;

        private Node() {}
        private Node(Node parent, Character parentCharacter) {
            this.parent = parent;
            this.parentCharacter = parentCharacter;
        }

        private int childrenCount() {
            return children.size();
        }
    }
}

