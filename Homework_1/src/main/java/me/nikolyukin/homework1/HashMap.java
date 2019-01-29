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


        public Node get(String key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("arguments are null");
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
