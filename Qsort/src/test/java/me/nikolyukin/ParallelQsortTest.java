package me.nikolyukin;

import static me.nikolyukin.ParallelQsort.qsort;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ParallelQsortTest {
    List<String> stringArrayList;
    List<Integer> integerLinkedList;
    List<Integer> emptyIntegerArrayList;
    @BeforeEach
    void init() {
        stringArrayList = new ArrayList<>(Arrays.asList("bb", "ab", "ba", "aa"));
        integerLinkedList = new LinkedList<>(Arrays.asList(1, 3, 2, 0, 4, 1, 3, 2, 0, 4,
            1, 3, 2, 0, 4, 1, 3, 2, 0, 4));
        emptyIntegerArrayList = new ArrayList<>();
    }

    @Test
    void qsortEmpty() throws InterruptedException {
        qsort(emptyIntegerArrayList, 1);
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
    void quartStringArrayListWithUnlimitedThread() throws InterruptedException {
        var qsortedCopy = new ArrayList<>(stringArrayList);
        qsort(qsortedCopy, 0);
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

    @Test
    void qsortIntegerLinkedListUnlimited() throws InterruptedException {
        var qsortedCopy = new LinkedList<>(integerLinkedList);
        qsort(qsortedCopy, Comparator.naturalOrder(), 0);
        integerLinkedList.sort(Comparator.naturalOrder());
        assertEquals(integerLinkedList, qsortedCopy);
    }

    @Test
    @Disabled
    void qsortCompareManyThreadsWhitOne() throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        int duplicates = 5;
        int maxSize = 1_000_001;
        int step = 5000;
        for (int i = 0; i < maxSize; i += step) {
            Collections.shuffle(list);
            var copyList = new ArrayList<>(list);
            long manyThreadStart = System.currentTimeMillis();
            qsort(list, 6);
            long manyThreadEnd = System.currentTimeMillis();
            long manyThreadTime = manyThreadEnd - manyThreadStart;

            long oneThreadStart = System.currentTimeMillis();
            qsort(copyList, 1);
            long oneThreadEnd = System.currentTimeMillis();
            long oneThreadTime = oneThreadEnd - oneThreadStart;

            System.out.printf("size %d: 6 thread %d; 1 threads %d; Batter? %b\n",
                i*duplicates, manyThreadTime, oneThreadTime, manyThreadTime < oneThreadTime);
            for(int j = i; j < i + step; j++) {
                for (int k = 0; k < duplicates; k++) {
                    list.add(j);
                }
            }
        }
    }
}