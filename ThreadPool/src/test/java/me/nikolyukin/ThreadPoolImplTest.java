package me.nikolyukin;

import static java.lang.Thread.interrupted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
        int n = 20;
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
        int n = 20;
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
}