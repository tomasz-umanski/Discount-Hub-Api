package pl.tumanski.discounthub.postmanagement.exception;

import pl.tumanski.discounthub.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
