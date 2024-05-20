package pl.tumanski.discounthub.usermanagement.exception;

import pl.tumanski.discounthub.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
