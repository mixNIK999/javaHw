package me.nikolyukin;

import static java.lang.Thread.interrupted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreadPoolImplTest {

    private ThreadPool singleThreadPool;
    private final int multiThreadPoolSize = 10;
    private ThreadPool multiThreadPool;

    @BeforeEach
    void init() {
        singleThreadPool = new ThreadPoolImpl(1);
        multiThreadPool = new ThreadPoolImpl(multiThreadPoolSize);
    }

    @AfterEach
    void shutdownAll() {
        singleThreadPool.shutdown();
        multiThreadPool.shutdown();
    }

    @Test
    void submitSimpleSingle() throws LightExecutionException {
        int n = 2 * multiThreadPoolSize;
        var singleThreadPoolRes = new ArrayList<LightFuture<String>>(n);
        for (int i = 0; i < n; i++) {
            singleThreadPoolRes.add(singleThreadPool.submit(() -> "test"));
        }
        for (int i = n - 1; i >= 0; i--) {
            assertEquals("test", singleThreadPoolRes.get(i).get());
        }
    }

    @Test
    void submitSimpleMulti() throws LightExecutionException {
        int n = 2 * multiThreadPoolSize;
        var multiThreadPoolRes = new ArrayList<LightFuture<String>>(n);
        for (int i = 0; i < n; i++) {
            multiThreadPoolRes.add(multiThreadPool.submit(() -> "test"));
        }
        for (int i = n - 1; i >= 0; i--) {
            assertEquals("test", multiThreadPoolRes.get(i).get());
        }
    }

    @Test
    void shutdownSimple() {
        var isRunning = new AtomicBoolean(false);
        singleThreadPool.submit(() -> {
            isRunning.set(true);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                } finally {
                    isRunning.set(false);
                }
            }
            return 0;
        });
        singleThreadPool.shutdown();
        assertFalse(isRunning.get());
    }

    @Test
    void countThreads() throws LightExecutionException {
        var latch = new CountDownLatch(multiThreadPoolSize);
        int n = multiThreadPoolSize - 1;
        final Supplier<String> task = () -> {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "done";
        };

        var multiThreadPoolRes = new ArrayList<LightFuture<String>>(n);
        for (int i = 0; i < n; i++) {
            multiThreadPoolRes.add(multiThreadPool.submit(task));
        }

        for (int i = n - 1; i >= 0; i--) {
            assertFalse(multiThreadPoolRes.get(i).isReady());
        }

        multiThreadPoolRes.add(multiThreadPool.submit(task));
        for (int i = n; i >= 0; i--) {
            assertEquals("done", multiThreadPoolRes.get(i).get());
        }
    }

    @Test
    void supplierWithException() {
        var res = singleThreadPool.submit(() -> {
            throw new RuntimeException("massage");
        });
        assertThrows(LightExecutionException.class, res::get);
        try {
            res.get();
        } catch (LightExecutionException e) {
            assertEquals("massage", e.getCause().getMessage());
        }
    }

    @Test
    void thenApplyDeep() throws LightExecutionException {
        int n = 2 * multiThreadPoolSize;
        var latch = new CountDownLatch(1);
        var multiThreadPoolRes = new ArrayList<LightFuture<Integer>>(n);
        Function<Integer, Integer> inc = (i) -> i + 1;
        multiThreadPoolRes.add(multiThreadPool.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }));
        for (int i = 0; i < n - 1; i++) {
            multiThreadPoolRes.add(multiThreadPoolRes.get(i).thenApply(inc));
        }

        for (int i = n - 1; i >= 0; i--) {
            assertFalse(multiThreadPoolRes.get(i).isReady());
        }

        assertEquals("done", multiThreadPool.submit(() -> "done").get());
        latch.countDown();
        for (int i = n - 1; i >= 0; i--) {
            assertEquals(Integer.valueOf(i), multiThreadPoolRes.get(i).get());
        }
    }

    @Test
    void thenApplyWide() throws LightExecutionException {
        int n = 2 * multiThreadPoolSize;
        var latch = new CountDownLatch(1);
        var multiThreadPoolRes = new ArrayList<LightFuture<Integer>>(n);
        Function<Integer, Integer> inc = (i) -> i + 1;
        multiThreadPoolRes.add(multiThreadPool.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }));
        for (int i = 0; i < n - 1; i++) {
            multiThreadPoolRes.add(multiThreadPoolRes.get(0).thenApply(inc));
        }

        for (int i = n - 1; i >= 0; i--) {
            assertFalse(multiThreadPoolRes.get(i).isReady());
        }
        assertEquals("done", multiThreadPool.submit(() -> "done").get());

        latch.countDown();
        assertEquals(Integer.valueOf(0), multiThreadPoolRes.get(0).get());
        for (int i = 0; i < n; i++) {
            multiThreadPoolRes.add(multiThreadPoolRes.get(0).thenApply(inc));
        }

        for (int i = 1; i < multiThreadPoolRes.size(); i++) {
            assertEquals(Integer.valueOf(1), multiThreadPoolRes.get(i).get());
        }
    }
}