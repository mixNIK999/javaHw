package me.nikolyukin.homework3;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.jetbrains.annotations.NotNull;

public class MyTreeSetImplementation<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private int size = 0;
    private Node<E> root = new Node<>();
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
    @NotNull
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

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this set (its cardinality)
     */
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
        var lowerNode = lowerBoundNode(e);
        if (comparator.compare(e, lowerNode.value) == 0) {
            return false;
        }
        lowerNode.leftChild = new Node<>(e, lowerNode);
        size++;
        return true;
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return new descendingIterator<>(root);
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        root.needToReverse = !root.needToReverse;
        return this;
    }

    /**
     * {@link TreeSet#first()}
     **/
    @Override
    public E first() {
        var begin = iterator();
        if (begin.hasNext()) {
            return begin.next();
        }
        return null;
    }

    /**
     * {@link TreeSet#last()}
     **/
    @Override
    public E last() {
        var end = descendingIterator();
        if (end.hasNext()) {
            return end.next();
        }
        return null;
    }

    /**
     * {@link TreeSet#lower(Object)}
     **/
    @Override
    public E lower(E e) {
        var currentNode = lowerBoundNode(e);
        if (currentNode == root) {
            return null;
        }
        if (comparator.compare(currentNode.value, e) == 0) {
            currentNode = currentNode.goPrev();
        }
        return currentNode.value;
    }

    /**
     * {@link TreeSet#lower(Object)}
     **/
    @Override
    public E floor(E e) {
        return lowerBoundNode(e).value;
    }

    /**
     * {@link TreeSet#ceiling(Object)}
     **/
    @Override
    public E ceiling(E e) {
        var currentNode = lowerBoundNode(e);
        if (currentNode == root) {
            return null;
        }
        if (comparator.compare(currentNode.value, e) != 0) {
            currentNode = currentNode.goNext();
        }
        return currentNode.value;
    }

    /**
     * {@link TreeSet#higher(Object)}
     **/
    @Override
    public E higher(E e) {
        return lowerBoundNode(e).goNext().value;
    }

    @NotNull
    private Node<E> lowerBoundNode(E element) {
        var currentNode = root;
        var nextNode = root.leftChild;
        currentNode.push();
        while (nextNode != null) {
            currentNode = nextNode;
            currentNode.push();
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

    private class TreeIterator<T> implements Iterator<T> {
        Node<T> nextElement;

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
         * true} if {@link #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return nextElement.value != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = nextElement.value;
            nextElement = nextElement.goNext();
            return value;
        }

        private TreeIterator(Node<T> nextElement) {
            this.nextElement = nextElement;
        }
    }

    private class descendingIterator<T> implements Iterator<T> {
        Node<T> nextElement;
        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
         * true} if {@link #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return nextElement.goPrev() != root;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextElement = nextElement.goPrev();
            return nextElement.value;
        }
        
        private descendingIterator(Node<T> nextElement) {
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
