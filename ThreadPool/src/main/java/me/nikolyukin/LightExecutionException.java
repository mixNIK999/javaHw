package me.nikolyukin;

public class LightExecutionException extends Exception {

    public LightExecutionException() {
    }

    public LightExecutionException(String message) {
        super(message);
    }

    public LightExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public LightExecutionException(Throwable cause) {
        super(cause);
    }
}
