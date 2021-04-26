package org.example.exception;

import java.time.DateTimeException;

public class IncorrectUsernameException extends DateTimeException {


    public IncorrectUsernameException(String message) {
        super(message);
    }

    public IncorrectUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
