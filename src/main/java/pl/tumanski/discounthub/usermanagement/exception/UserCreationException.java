package pl.tumanski.discounthub.usermanagement.exception;

import pl.tumanski.discounthub.exception.CreationException;

public class UserCreationException extends CreationException {
    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
