package me.nikolyukin;

/**
 * Исключения бросаемые реализациями LightFuture, когда выполнения задачи завершилось исключением.
 */
public class LightExecutionException extends Exception {

    /**
     * Создает исключение с ассоциированным исключением.
     *
     * @param cause результат выполнения задачи.
     */
    public LightExecutionException(final Throwable cause) {
        super(cause);
    }
}
