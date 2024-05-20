package pl.tumanski.discounthub.categorymanagement.exception;

import pl.tumanski.discounthub.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
