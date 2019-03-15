package me.nikolyukin;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс позволяет статические методы для сортировки List
 * с помощью многопоточного алгоритма qsort
 */
public class ParallelQsort {

    /**
     * Метод сортирует лист используя переданный компаратор.
     * Позволяет задать число потоков. Если threadNumber < 1, то число потоков будет неограниченно.
     *
     * @param list лист, который будет отсортировон
     * @param comparator компаратор, который будет использовать сортировка
     * @param threadNumber число потоков, которое будет использованно
     * @param <T> тип сортируеммых данных
     * @throws InterruptedException если сортировка была прервана
     */
    public static <T> void qsort(List<T> list, Comparator<T> comparator, int threadNumber)
        throws InterruptedException {
        ExecutorService executor;
        if (threadNumber > 0) {
            executor = Executors.newFixedThreadPool(threadNumber);
        } else {
            executor = Executors.newCachedThreadPool();
        }
        T[] array = (T[]) list.toArray();

        synchronized (array) {
            var sortedElements = new AtomicInteger(0);
            executor.submit(new RunSort<>(array, comparator, sortedElements,
                executor, 0, array.length));
            while (sortedElements.get() != array.length){
                array.wait();
            }
        }
        var iterator = list.listIterator();
        for (var element: array) {
            iterator.next();
            iterator.set(element);
        }
    }

    /**
     * Метод сортирует лист используя natural order.
     * Позволяет задать число потоков. Если threadNumber < 1, то число потоков будет неограниченно.
     *
     * @param list лист, который будет отсортировон
     * @param threadNumber число потоков, которое будет использованно
     * @param <T> тип сортируеммых данных
     * @throws InterruptedException если сортировка была прервана
     */
    public static <T extends Comparable<? super T>> void qsort(List<T> list, int threadNumber)
        throws InterruptedException {
        qsort(list, Comparator.naturalOrder(), threadNumber);
    }

    private static class RunSort<T> implements Runnable {
        private final T[] array;
        private final Comparator<T> comparator;
        private final AtomicInteger doneCounter;
        private final ExecutorService executor;
        private final int left;
        private final int right;

        private RunSort(T[] array, Comparator<T> comparator, AtomicInteger doneCounter,
            ExecutorService executor, int left, int right) {
            this.array = array;
            this.comparator = comparator;
            this.doneCounter = doneCounter;
            this.executor = executor;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            int length = right - left;
            if(length <= 1) {
                if(length == 1 && doneCounter.incrementAndGet() == array.length) {
                    synchronized (array) {
                        array.notify();
                    }
                }
                return;
            }

            T rootValue = array[left + ThreadLocalRandom.current().nextInt(length)];
            int i = left;
            int j = right - 1;
            while(i <= j) {
                while (comparator.compare(array[i], rootValue) < 0) {
                    i++;
                }
                while (comparator.compare(array[j], rootValue) > 0) {
                    j--;
                }
                if(i <= j) {
                    T tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                    i++;
                    j--;
                }
            }
            if (i - j - 1 > 0) {
                if (doneCounter.addAndGet(i - j - 1) == array.length) {
                    synchronized (array) {
                        notify();
                    }
                }
            }

            if (left < j + 1) {
                executor.submit(new RunSort<>(array, comparator, doneCounter, executor, left, j + 1));
            }

            if (i < right) {
                executor.submit(new RunSort<>(array, comparator, doneCounter, executor, i, right));
            }
        }
    }
}
