package pl.tumanski.discounthub.exception;

public class CreationException extends RuntimeException {
    public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
