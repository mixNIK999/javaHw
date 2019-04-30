package me.nikolyukin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreadPoolImplTest {
    private ThreadPool singleThreadPool;
    private ThreadPool fourThreadPool;
    private ThreadPool tenThreadPool;

    @BeforeEach
    void init() {
        singleThreadPool = new ThreadPoolImpl(1);
        fourThreadPool = new ThreadPoolImpl(4);
        tenThreadPool = new ThreadPoolImpl(10);
    }

    @AfterEach
    void shutdownAll() {
        singleThreadPool.shutdown();
        fourThreadPool.shutdown();
        tenThreadPool.shutdown();
    }

    @Test
    void submit() {
    }

    @Test
    void shutdown() {
    }
}