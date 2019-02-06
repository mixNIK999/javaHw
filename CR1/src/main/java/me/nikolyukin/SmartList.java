package me.nikolyukin;

import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

public class SmartList<E> extends AbstractList<E> {
    private int size = 0;
    private Object container;
    

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
        return size;
    }

    public E set​(int index,@NotNull E element) {
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

    public boolean add​(@NotNull E e) {
        if (size == 0) {
            container = e;
        } else if (size == 1) {
            var newContainer = new Object[5];
            var prev = (E)container;
            container = newContainer;
            ((E[])container)[0] = prev;
            ((E[])container)[1] = e;
        } else if (size <= 4){
            ((E[])container)[size] = e;
        } else {
            if (size == 5) {
                container = new ArrayList<>(Arrays.asList((E[]) container));
            }
            ((ArrayList<E>)container).add(e);
        }
        return true;
    }

}
