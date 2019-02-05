package me.nikolyukin;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Бор - структура данных для хранения набора строк.
 *
 */
public class Trie implements MySerializable {
    private Node root = new Node();

    /**
     * Конструктор пустого бора.
     */
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

    /**
     * Добавляет строку в бор, работает за O(|element|).
     *
     * @param element новая строка бора
     * @return true, если такой строки ещё не было
     */
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

    /**
     * Проверяет, есть ли строка в боре, работает за O(|element|).
     *
     * @param element искомая строка
     * @return true, если такая строка содержится в боре
     */
    public boolean contains(@NotNull String element) {
        var lastNode = goToNode(element, false);
        return (lastNode != null && lastNode.isTerminal);
    }

    /**
     * Удаляет строку из бора, работает за O(|element|).
     *
     * @param element удаляемая строка
     * @return true, если элемент был в дереве
     */
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

    /**
     * Узнает количество строк в боре, работает за O(1).
     *
     * @return количество строк.
     */
    public int size() {
        return root.suffixCount;
    }

    /**
     *
     * Узнает количество строк с данным префиксом, работает за O(|prefix|).
     *
     * @param prefix префикс
     * @return количество строк, начинающиеся с данного префикса.
     */
    public int howManyStartsWithPrefix(@NotNull String prefix) {
        var lastNode = goToNode(prefix, false);
        if (lastNode == null) {
            return 0;
        }
        return lastNode.suffixCount;
    }

    private void dfsSerialize (@NotNull ObjectOutputStream objectOut,
                @NotNull Node currentNode) throws IOException {
        objectOut.writeInt(currentNode.childrenCount());
        objectOut.writeBoolean(currentNode.isTerminal);
        for (var entry : currentNode.children.entrySet()) {
            objectOut.writeChar(entry.getKey());
            dfsSerialize(objectOut, entry.getValue());
        }
    }

    /**
     * {@link MySerializable#serialize(OutputStream)}
     */
    @Override
    public void serialize(@NotNull OutputStream out) throws IOException {
        try (var objectOut = new ObjectOutputStream(new BufferedOutputStream(out))){
            dfsSerialize(objectOut, root);
        }
    }

    private void dfsDeserialize (@NotNull ObjectInputStream objectIn,
            @NotNull Node currentNode) throws IOException {
        int n = objectIn.readInt();
        currentNode.isTerminal = objectIn.readBoolean();
        currentNode.suffixCount = (currentNode.isTerminal) ? 1: 0;
        for (int i = 0; i < n; i++) {
            char childChar = objectIn.readChar();
            Node nextNode = new Trie(currentNode).goToNode(Character.toString(childChar), true);
            dfsDeserialize(objectIn, nextNode);
            currentNode.suffixCount += nextNode.suffixCount;
        }
    }

    /**
     * {@link MySerializable#deserialize(InputStream)}
     */
    @Override
    public void deserialize(@NotNull InputStream in) throws IOException {
        root = new Node();
        try (var objectIn = new ObjectInputStream(new BufferedInputStream(in))){
            dfsDeserialize(objectIn, root);
        }
    }

    private static class Node {
        private Map<Character, Node> children
                = new  LinkedHashMap<>();
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

