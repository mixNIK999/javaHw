package me.nikolyukin;

import java.util.AbstractList;
import java.util.ArrayList;

public class SmartList<E> extends AbstractList<E> {
    private int listSize = 0;
    private Object container;

//    private void resize(int newSize) {
//
//    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            return (E)container;
        } else if (size <= 5){
            return ((E[])container)[index];
        } else {
            return ((ArrayList<E>)container).get(index);
        }
    }

    public int size() {
        return listSize;
    }

    public E set​(int index, E element) {
        E prev;
        if (size == 1) {
            prev = (E)container;
            container = element;
        } else if (size <= 5){
            prev = ((E[])container)[index];
            ((E[])container)[index] = element;
        } else {
            prev = ((ArrayList<E>)container).set(index, element);
        }
        return prev;
    }


}
