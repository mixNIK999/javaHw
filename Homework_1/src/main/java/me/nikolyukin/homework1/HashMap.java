package me.nikolyukin.homework1;

public class HashMap {
    private class List {

        Node head;
        int size;

        private class Node {
            private String value;
            private String key;
            private Node next;

            public Node() {}

            public Node(String value, String key, Node next) {
                this.value = value;
                this.key = key;
                this.next = next;
            }

            public Node getNext() {
                return next;
            }

            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public void setNext(Node next) {
                this.next = next;
            }
        }
    }
}
