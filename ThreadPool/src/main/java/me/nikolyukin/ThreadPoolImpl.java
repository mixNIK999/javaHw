package me.nikolyukin;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class ThreadPoolImpl implements ThreadPool {
    private ArrayList<Thread> threadList;
    private ThreadSafeQueue<LightFutureImpl> taskQueue;

    public ThreadPoolImpl(int size) {
        threadList = new ArrayList<>(size);
        taskQueue = new ThreadSafeQueue<>();
        for (int i = 0; i < size; i++) {
            threadList.set(i, new Thread(() -> {
                while (true) {
                    try {
                        taskQueue.pop().calculate();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }));
        }
    }

    @Override
    public <T> LightFuture<T> submit(Supplier<T> task) {
        var newTask = new LightFutureImpl<>(task);
        taskQueue.push(newTask);
        return newTask;
    }

    @Override
    public void shutdown() {
        for (var thread : threadList) {
            thread.interrupt();
        }
    }

    private static class ThreadSafeQueue<E> {
        Queue<E> queue;

        public ThreadSafeQueue() {
            queue = new ArrayDeque<>();
        }

        private synchronized void push(@NotNull E data) {
            queue.add(data);
            queue.notify();
        }

        private synchronized E pop() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            return queue.remove();
        }

        private synchronized boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    private class LightFutureImpl<T> implements LightFuture<T> {
        private volatile Supplier<T> task;
        private T result;
        private Throwable exception;
        private volatile ThreadSafeQueue<LightFutureImpl> nextTasks;

        private LightFutureImpl(@NotNull Supplier<T> task) {
            this.task = task;
            nextTasks = new ThreadSafeQueue<>();
        }

        @Override
        public boolean isReady() {
            return task == null;
        }

        @Override
        public synchronized T get() throws LightExecutionException{
            while (!isReady()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new LightExecutionException(e);
                }
            }

            if (exception != null) {
                throw new LightExecutionException(exception);
            }

            return result;

        }

        @Override
        public <R> LightFuture<R> thenApply(Function<T, R> function) {
            var newTask = new LightFutureImpl<>(() -> {
                try {
                    return function.apply(LightFutureImpl.this.get());
                } catch (LightExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
            nextTasks.push(newTask);
            return newTask;
        }

        private void calculate() throws InterruptedException {
            ThreadSafeQueue<LightFutureImpl> needToAdd;
            synchronized (this) {
                try {
                    result = task.get();
                } catch (Throwable e) {
                    exception = e;
                }
                needToAdd = nextTasks;
                nextTasks = taskQueue;
                task = null;
                notifyAll();
            }
            while(!needToAdd.isEmpty()) {
                taskQueue.push(needToAdd.pop());
            }
        }
    }
}
