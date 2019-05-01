package me.nikolyukin;

import java.util.function.Supplier;

/**
 * Интерфейс пула потоков. Позволяет добавлять задачи и завершать работу пула. Все принятые задачи
 * представлены интерфейсов LightFuture.
 */
public interface ThreadPool {

    /**
     * Метод добавляет задачу в пул потоков и возвращает объект интерфейса LightFuture.
     *
     * @param task - задача, которую необходимо вычислить.
     * @param <T> - тип результата вычисления.
     * @return объект интерфейса LightFuture, с помощью которого можно работать с принятой
     * задачей.
     */
    <T> LightFuture<T> submit(Supplier<T> task);

    /**
     * Метод завершает выполнение всех потоков с помощью Thread.interrupt(). Не дожидается полной
     * остановки всех потоков.
     */
    void shutdown();
}
