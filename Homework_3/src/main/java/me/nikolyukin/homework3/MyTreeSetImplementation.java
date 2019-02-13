package me.nikolyukin.homework3;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class MyTreeSetImplementation<E> extends AbstractSet<E> implements MyTreeSet<E> {
    int size = 0;
    Node<E> root = new Node<>();
    private Comparator<? super E> comparator;

    public MyTreeSetImplementation() {
        this(null);
    }

    public MyTreeSetImplementation(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public TreeIterator<E> iterator() {
        var currentNode = root;
        currentNode.push();
        while(currentNode.leftChild != null) {
            currentNode = currentNode.leftChild;
            currentNode.push();
        }
        return new TreeIterator<>(currentNode);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     * @implSpec This implementation always throws an {@code UnsupportedOperationException}.
     */
    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return null;
    }

    /**
     * {@link TreeSet#first()}
     **/
    @Override
    public E first() {
        return null;
    }

    /**
     * {@link TreeSet#last()}
     **/
    @Override
    public E last() {
        return null;
    }

    /**
     * {@link TreeSet#lower(Object)}
     **/
    @Override
    public E lower(E e) {
        return null;
    }

    /**
     * {@link TreeSet#lower(Object)}
     **/
    @Override
    public E floor(E e) {
        return null;
    }

    /**
     * {@link TreeSet#ceiling(Object)}
     **/
    @Override
    public E ceiling(E e) {
        return null;
    }

    /**
     * {@link TreeSet#higher(Object)}
     **/
    @Override
    public E higher(E e) {
        return null;
    }

    private Node<E> lowerBoundNode(E element) {
        var currentNode = root;
        var nextNode = root.leftChild;
        while (nextNode != null) {
            currentNode = nextNode;
            int compareRes = comparator.compare(element, currentNode.value);
            if (compareRes > 0) {
                nextNode = currentNode.rightChild;
            } else if (compareRes == 0) {
                return currentNode;
            } else {
                nextNode = currentNode.leftChild;
            }
        }
        return (comparator.compare(element, currentNode.value) >= 0) ? currentNode
            : currentNode.goPrev();
    }

    private class TreeIterator<E> implements Iterator<E> {
        Node<E> nextElement;

        /**
         * Returns {@code true} if this list iterator has more elements when traversing the list in
         * the forward direction. (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when traversing the list in
         * the forward direction
         */
        @Override
        public boolean hasNext() {
            return nextElement.value != null;
        }

        /**
         * Returns the next element in the list and advances the cursor position. This method may be
         * called repeatedly to iterate through the list, or intermixed with calls to {@link
         * #previous} to go back and forth. (Note that alternating calls to {@code next} and {@code
         * previous} will return the same element repeatedly.)
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = nextElement.value;
            nextElement = nextElement.goNext();
            return value;
        }

        /**
         * Returns {@code true} if this list iterator has more elements when traversing the list in
         * the reverse direction.  (In other words, returns {@code true} if {@link #previous} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when traversing the list in
         * the reverse direction
         */
        public boolean hasPrevious() {
            return nextElement.goPrev() != root;
        }

        /**
         * Returns the previous element in the list and moves the cursor position backwards.  This
         * method may be called repeatedly to iterate through the list backwards, or intermixed with
         * calls to {@link #next} to go back and forth.  (Note that alternating calls to {@code
         * next} and {@code previous} will return the same element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous element
         */
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            nextElement = nextElement.goPrev();
            return nextElement.value;
        }

        private TreeIterator() {
            this.nextElement = root;
        }

        private TreeIterator(Node<E> nextElement) {
            this.nextElement = nextElement;
        }
    }

    private static class Node<E> {
        private Node<E> leftChild;
        private Node<E> rightChild;
        private Node<E> parent;
        private E value;
        private boolean needToReverse = false;

        private Node() {}

        private Node(E value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        private Node<E> goNext() {
            push();
            var currentNode = this;
            if (currentNode.rightChild != null) {
                currentNode = currentNode.rightChild;
                while(currentNode.leftChild != null) {
                    currentNode = currentNode.leftChild;
                }
                return currentNode;
            }
            while(currentNode.parent != null) {
                if (currentNode.parent.leftChild == currentNode) {
                    return currentNode.parent;
                }
                currentNode = currentNode.parent;
            }
            return currentNode;
        }

        private Node<E> goPrev() {
            push();
            var currentNode = this;
            if (currentNode.leftChild != null) {
                currentNode = currentNode.leftChild;
                while(currentNode.rightChild != null) {
                    currentNode = currentNode.rightChild;
                }
                return currentNode;
            }
            while(currentNode.parent != null) {
                if (currentNode.parent.rightChild == currentNode) {
                    return currentNode.parent;
                }
                currentNode = currentNode.parent;
            }
            return currentNode;
        }

        private void push() {
            if (needToReverse) {
                var tmp = leftChild;
                leftChild =  rightChild;
                rightChild = tmp;
                needToReverse = false;
                if (leftChild != null) {
                    leftChild.needToReverse = !leftChild.needToReverse;
                }
                if (rightChild != null) {
                    rightChild.needToReverse = !rightChild.needToReverse;
                }
            }
        }
    }

}
