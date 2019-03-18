package me.nikolyukin.homework3;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.jetbrains.annotations.NotNull;

/**
 * Контейнер  для хранения данных с использованием бинарного дерева
 * @param <E> тип данных. Обязан быть задан любо Comparator, либо natural ordering.
 */
public class MyTreeSetImplementation<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private int size = 0;
    private final @NotNull Node<E> root = new Node<>();
    private Comparator<? super E> comparator;

    /**
     * Контсруктор по умолчанию, использует natural ordering.
     * Параметр E должен быть Comparable<? super T>
     */
    public MyTreeSetImplementation() {
        this((element1, element2) -> ((Comparable<E>) element1).compareTo(element2));
    }

    /**
     * Конструктор с использованием comparator.
     * @param comparator для E
     */
    public MyTreeSetImplementation(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Method creates an iterator over the elements in this set in proper sequence
     *
     * @return an iterator over the elements contained in this collection
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        var currentNode = root;
        while (currentNode.leftChild != null) {
            currentNode = currentNode.leftChild;
        }
        return new TreeIterator(currentNode);
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
        var foundNode = find(e);
        if (Comparator.nullsFirst(comparator).compare(e, foundNode.value) == 0) {
            return false;
        }

        var newNode = new Node<>(e, foundNode);
        if (Comparator.nullsFirst(comparator).compare(e, foundNode.value) > 0) {
            foundNode.rightChild = newNode;
        } else {
            foundNode.leftChild = newNode;
        }
        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @implSpec This implementation iterates over the elements in the collection, checking each
     * element in turn for equality with the specified element.
     * @param element
     */
    @Override
    public boolean contains(Object element) {
        return element.equals(find((E) element).value);
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @implSpec This implementation iterates over the collection looking for the specified element.
     *  If it finds the element, it removes the element from the collection
     *
     */
    @Override
    public boolean remove(Object element) {
        Node<E> foundNode = find((E) element);
        if (!element.equals(foundNode.value)) {
            return false;
        }
        removeNode(foundNode);
        return true;
    }

    private void removeNode(@NotNull Node<E> node) {
        Node<E> newChild;
        if (node.rightChild == null) {
            newChild = node.leftChild;
        } else if (node.leftChild == null) {
            newChild = node.rightChild;
        } else {
            newChild = node.goNext();
            removeNode(newChild);
        }
        node.putInstead(newChild);
        size--;
    }
    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        var currentNode = root;
        while (currentNode.rightChild != null) {
            currentNode = currentNode.rightChild;
        }
        return new DescendingIterator(currentNode);
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        MyTreeSet<E> descendingSet = new MyTreeSetImplementation<>(comparator.reversed());
        descendingSet.addAll(this);
        return  descendingSet;
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
        Node<E> currentNode = find(element);
        return (Comparator.nullsLast(comparator).compare(element, currentNode.value) >= 0) ? currentNode
            : currentNode.goPrev();
    }

    private Node<E> find(E element) {
        var currentNode = root;
        var nextNode = root.rightChild;
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
        return currentNode;
    }

    private class TreeIterator implements Iterator<E> {

        private Node<E> prevElement;

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
         * true} if {@link #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return prevElement.goNext().value != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prevElement = prevElement.goNext();
            return prevElement.value;
        }

        private TreeIterator(Node<E> prevElement) {
            this.prevElement = prevElement;
        }
    }

    private class DescendingIterator implements Iterator<E> {

        private Node<E> prevElement;
        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
         * true} if {@link #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return prevElement.value != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = prevElement.value;
            prevElement = prevElement.goPrev();
            return value;
        }
        
        private DescendingIterator(Node<E> prevElement) {
            this.prevElement = prevElement;
        }
    }

    private static class Node<E> {
        private Node<E> leftChild;
        private Node<E> rightChild;
        private Node<E> parent;
        private E value;

        private Node() {}

        private Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }

        private Node<E> goNext() {
            var currentNode = this;
            if (currentNode.rightChild != null) {
                currentNode = currentNode.rightChild;
                while (currentNode.leftChild != null) {
                    currentNode = currentNode.leftChild;
                }
                return currentNode;
            }
            while (currentNode.parent != null) {
                if (currentNode.parent.leftChild == currentNode) {
                    return currentNode.parent;
                }
                currentNode = currentNode.parent;
            }
            return currentNode;
        }

        private Node<E> goPrev() {
            var currentNode = this;
            if (currentNode.leftChild != null) {
                currentNode = currentNode.leftChild;
                while (currentNode.rightChild != null) {
                    currentNode = currentNode.rightChild;
                }
                return currentNode;
            }
            while (currentNode.parent != null) {
                if (currentNode.parent.rightChild == currentNode) {
                    return currentNode.parent;
                }
                currentNode = currentNode.parent;
            }
            return currentNode;
        }


        private void putInstead(Node<E> other) {
            if (parent != null) {
                if (parent.rightChild == this) {
                    parent.rightChild = other;
                } else {
                    parent.leftChild = other;
                }
                if (other != null) {
                    other.parent = parent;
                }
                parent = null;
            }
        }
    }
}
