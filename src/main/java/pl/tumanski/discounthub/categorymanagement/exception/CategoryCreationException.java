package pl.tumanski.discounthub.categorymanagement.exception;

import pl.tumanski.discounthub.exception.CreationException;

public class CategoryCreationException extends CreationException {
    public CategoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
