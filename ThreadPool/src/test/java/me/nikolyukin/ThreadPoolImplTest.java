package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreadPoolImplTest {
    private ThreadPool singleThreadPool;
    private ThreadPool multiThreadPool;

    @BeforeEach
    void init() {
        singleThreadPool = new ThreadPoolImpl(1);
        multiThreadPool = new ThreadPoolImpl(10);
    }

    @AfterEach
    void shutdownAll() {
        singleThreadPool.shutdown();
        multiThreadPool.shutdown();
    }

    @Test
    void submitSimpleSingle() throws LightExecutionException {
        int n = 20;
        var singleThreadPoolRes  = new ArrayList<LightFuture<String>>(n);
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
        var multiThreadPoolRes  = new ArrayList<LightFuture<String>>(n);
        for (int i = 0; i < n; i++) {
            multiThreadPoolRes.add(singleThreadPool.submit(() -> "test"));
        }
        for (int i = n - 1; i >= 0; i--) {
            assertEquals("test", multiThreadPoolRes.get(i).get());
        }
    }

    @Test
    void shutdown() {
    }
}