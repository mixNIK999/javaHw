package me.nikolyukin;

import static me.nikolyukin.ParallelQsort.qsort;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParallelQsortTest {
    List<String> stringArrayList;
    List<Integer> integerLinkedList;
    List<Integer> emptyIntegerArrayList;
    @BeforeEach
    void init() {
        stringArrayList = new ArrayList<>(Arrays.asList("bb", "ab", "ba", "aa"));
        integerLinkedList = new LinkedList<>(Arrays.asList(1, 3, 2, 0, 4));
        emptyIntegerArrayList = new ArrayList<>();
    }

    @Test
    void qsortEmpty() throws InterruptedException {
        qsort(emptyIntegerArrayList, 0);
        assertEquals(Collections.emptyList(), emptyIntegerArrayList);
    }

    @Test
    void qsortStringArrayList() throws InterruptedException {
        var qsortedCopy = new ArrayList<>(stringArrayList);
        qsort(qsortedCopy, 6);
        stringArrayList.sort(Comparator.naturalOrder());
        assertEquals(stringArrayList, qsortedCopy);
    }

    @Test
    void qsortStringArrayListWithOneThread() throws InterruptedException {
        var qsortedCopy = new ArrayList<>(stringArrayList);
        qsort(qsortedCopy, 1);
        stringArrayList.sort(Comparator.naturalOrder());
        assertEquals(stringArrayList, qsortedCopy);
    }

    @Test
    void qsortIntegerLinkedList() throws InterruptedException {
        var qsortedCopy = new LinkedList<>(integerLinkedList);
        qsort(qsortedCopy, 6);
        integerLinkedList.sort(Comparator.naturalOrder());
        assertEquals(integerLinkedList, qsortedCopy);
    }
}