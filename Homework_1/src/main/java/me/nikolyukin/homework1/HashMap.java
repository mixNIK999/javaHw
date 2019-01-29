package me.nikolyukin.homework1;

public class HashMap {
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

            var node = get(key);
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
            var node = get(key);
            if (node != head) {
                size--;
                node.next.prev = node.prev;
                node.prev.next = node.next;
                return node.value;
            }
            return null;
        }

        public Node get(String key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("arguments of List.get() are null");
            }

            for (Node current = head.next; current != head; current = current.next) {
                if (current.key().equals(key)) {
                    return current;
                }
            }
            return head;
        }

        private class Node {
            public String value;
            public String key;
            public Node next;
            public Node prev;

            public Node() {}

            public Node(String value, String key, Node next, Node prev) {
                this.value = value;
                this.key = key;
                this.next = next;
                this.prev = prev;
            }
        }
    }
}
