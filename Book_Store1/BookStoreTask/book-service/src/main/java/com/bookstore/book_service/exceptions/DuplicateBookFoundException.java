package com.bookstore.book_service.exceptions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DuplicateBookFoundException extends RuntimeException {
    public DuplicateBookFoundException(@NotNull @Size(min = 1, max = 255) String s) {
        super(s);
    }
}
