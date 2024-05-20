package pl.tumanski.discounthub.postmanagement.exception;

import pl.tumanski.discounthub.exception.CreationException;

public class PostCreationException extends CreationException {
    public PostCreationException(String message) {
        super(message);
    }

    public PostCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
