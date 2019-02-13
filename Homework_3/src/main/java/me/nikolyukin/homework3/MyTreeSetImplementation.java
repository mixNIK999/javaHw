package me.nikolyukin.homework3;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class MyTreeSetImplementation<E extends > extends AbstractSet<E> implements MyTreeSet<E> {
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
    public Iterator iterator() {
        return null;
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

    private static class Node<E> {
        private Node<E> LeftChild;
        private Node<E> RightChild;
        private E value;
        private boolean needToReverse = false;

        private Node() {}

        private Node(E value) {
            this.value = value;
        }
    }

}
