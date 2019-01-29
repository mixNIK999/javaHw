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

        private void push(String key, String value) throws IllegalArgumentException {
            if (key == null | value == null) {
                throw new IllegalArgumentException("arguments of List.push() are null");
            }

            var node = new Node(key, value, head, head.next);
            node.prev.next = node;
            node.next.prev = node;
        }

        public void add(String key, String value) throws IllegalArgumentException {
            if (key == null | value == null) {
                throw new IllegalArgumentException("arguments of List.add() are null");
            }

            var node = get(key);
            if (node != head) {
                node.value = value;
            } else {
                push (key, value);
            }
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
