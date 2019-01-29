package me.nikolyukin.homework1;

public class HashMap {
    private List[] array;
    private int numberOfElements;
    private int defaultSize;

    public HashMap(int size) {
        defaultSize = size;
        array = new List[size];
    }

    public int size() {
        return numberOfElements;
    }

    public int getCapacity() {
        return array.length;
    }

            var oldArray = array;
            array = new List[newSize];
            numberOfElements = 0;

            for (int i = 0; i < oldArray.length; i++) {
                if (oldArray[i] != null) {
                    for (var j = oldArray[i].head.next; j != oldArray[i].head; j = j.next) {
                        put(j.key, j.value);
                    }
                }
            }
        }
    }

    private List findList(String key) {
        int index = Math.abs(key.hashCode()) % array.length;
        if (array[index] == null) {
            array[index] = new List();
        }
        return array[index];
    }

    public boolean contains(String key) throws IllegalArgumentException  {
        if (key == null) {
            throw new IllegalArgumentException("arguments of HashMap.contains() are null");
        }
        return findList(key).contains(key);
    }

    public String get(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("arguments of HashMap.get() are null");
        }

        return findList(key).getNode(key).value;
    }

    public String put(String key, String value) throws IllegalArgumentException {
        if (key == null | value == null) {
            throw new IllegalArgumentException("arguments of HashMap.put() are null");
        }

        String previousValue = findList(key).put(key, value);
        if (previousValue == null) {
            numberOfElements++;
        }
        return previousValue;
    }

    public String remove(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("arguments of HashMap.remove() are null");
        }

        String previousValue = findList(key).remove(key);
        if (previousValue != null) {
            numberOfElements--;
        }
        return previousValue;
    }

    public void clear() {
        array = new List[defaultSize];
        numberOfElements = 0;
    }

    private class List {

        Node head;
        int size;

        public List () {
            head = new Node();
            head.next = head;
            head.prev = head;
        }

        public int getSize() {
            return size;
        }

        private void push(String key, String value) throws IllegalArgumentException {
            if (key == null | value == null) {
                throw new IllegalArgumentException("arguments of List.push() are null");
            }

            var node = new Node(key, value, head, head.next);
            node.prev.next = node;
            node.next.prev = node;
            size++;
        }

        public String put(String key, String value) throws IllegalArgumentException {
            if (key == null | value == null) {
                throw new IllegalArgumentException("arguments of List.add() are null");
            }

            var node = getNode(key);
            if (node != head) {
                var previousValue = node.value;
                node.value = value;
                return previousValue;
            } else {
                push (key, value);
                return null;
            }
        }

        public String remove(String key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("arguments of List.remove() are null");
            }
            var node = getNode(key);
            if (node != head) {
                size--;
                node.next.prev = node.prev;
                node.prev.next = node.next;
                return node.value;
            }
            return null;
        }

        public Node getNode(String key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("arguments of List.getNode() are null");
            }

            for (Node current = head.next; current != head; current = current.next) {
                if (current.key.equals(key)) {
                    return current;
                }
            }
            return head;
        }

        public boolean contains(String key) {
            return getNode(key) != head;
        }

        private class Node {
            public String value;
            public String key;
            public Node next;
            public Node prev;

            public Node() {}

            public Node(String key, String value, Node prev, Node next) {
                this.value = value;
                this.key = key;
                this.next = next;
                this.prev = prev;
            }
        }
    }
}
