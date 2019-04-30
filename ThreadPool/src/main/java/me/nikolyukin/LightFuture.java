package me.nikolyukin;

import java.util.function.Function;

public interface LightFuture<T> {
    boolean isReady();
    T get() throws LightExecutionException;
    <R> LightFuture<R> thenApply(Function<T, R> function);
}
