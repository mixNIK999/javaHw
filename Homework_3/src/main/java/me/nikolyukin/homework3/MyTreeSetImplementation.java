package me.nikolyukin.homework3;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.jetbrains.annotations.NotNull;

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

    private static class TreeIterator<E> extends ListIterator<E> {
        Node<E> next;

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
            return next.value != null;
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
            var value = next.value;

            return null;
        }

        /**
         * Returns {@code true} if this list iterator has more elements when traversing the list in
         * the reverse direction.  (In other words, returns {@code true} if {@link #previous} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when traversing the list in
         * the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            return false;
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
        @Override
        public E previous() {
            return null;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to {@link
         * #next}. (Returns list size if the list iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a subsequent call to {@code
         * next}, or list size if the list iterator is at the end of the list
         */
        @Override
        public int nextIndex() {
            return 0;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to {@link
         * #previous}. (Returns -1 if the list iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a subsequent call to {@code
         * previous}, or -1 if the list iterator is at the beginning of the list
         */
        @Override
        public int previousIndex() {
            return 0;
        }

//        /**
//         * Removes from the list the last element that was returned by {@link #next} or {@link
//         * #previous} (optional operation).  This call can only be made once per call to {@code
//         * next} or {@code previous}. It can be made only if {@link #add} has not been called after
//         * the last call to {@code next} or {@code previous}.
//         *
//         * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
//         * this list iterator
//         * @throws IllegalStateException if neither {@code next} nor {@code previous} have been
//         * called, or {@code remove} or {@code add} have been called after the last call to {@code
//         * next} or {@code previous}
//         */
        @Override
        public void remove() {

        }

        /**
         * Replaces the last element returned by {@link #next} or {@link #previous} with the
         * specified element (optional operation). This call can be made only if neither {@link
         * #remove} nor {@link #add} have been called after the last call to {@code next} or {@code
         * previous}.
         *
         * @param e the element with which to replace the last element returned by {@code next} or
         * {@code previous}
         * @throws UnsupportedOperationException if the {@code set} operation is not supported by
         * this list iterator
         * @throws ClassCastException if the class of the specified element prevents it from being
         * added to this list
         * @throws IllegalArgumentException if some aspect of the specified element prevents it from
         * being added to this list
         * @throws IllegalStateException if neither {@code next} nor {@code previous} have been
         * called, or {@code remove} or {@code add} have been called after the last call to {@code
         * next} or {@code previous}
         */
        @Override
        public void set(E e) {

        }

        /**
         * Inserts the specified element into the list (optional operation). The element is inserted
         * immediately before the element that would be returned by {@link #next}, if any, and after
         * the element that would be returned by {@link #previous}, if any.  (If the list contains
         * no elements, the new element becomes the sole element on the list.)  The new element is
         * inserted before the implicit cursor: a subsequent call to {@code next} would be
         * unaffected, and a subsequent call to {@code previous} would return the new element. (This
         * call increases by one the value that would be returned by a call to {@code nextIndex} or
         * {@code previousIndex}.)
         *
         * @param e the element to insert
         * @throws UnsupportedOperationException if the {@code add} method is not supported by this
         * list iterator
         * @throws ClassCastException if the class of the specified element prevents it from being
         * added to this list
         * @throws IllegalArgumentException if some aspect of this element prevents it from being
         * added to this list
         */
        @Override
        public void add(E e) {

        }

        private static Node goNext(@NotNull Node element) {
            element.push();
            if (element.rightChild != null) {
                return element.rightChild;
            }
            while(element.parent != null) {
                if (element.parent.leftChild == element) {
                    return element.parent;
                }
                element = element.parent;
            }
            return element;
        }

        private static Node goPrev(@NotNull Node element) {
            element.push();
            if (element.leftChild != null) {
                return element.leftChild;
            }
            while(element.parent != null) {
                if (element.parent.rightChild == element) {
                    return element.parent;
                }
                element = element.parent;
            }
            return element;
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
