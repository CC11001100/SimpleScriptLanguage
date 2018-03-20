package org.cc11001100.parser;

/**
 * @author CC11001100
 */
public class StoneException extends RuntimeException {

    public StoneException() {
    }

    public StoneException(String message) {
        super(message);
    }

    public StoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoneException(Throwable cause) {
        super(cause);
    }

    public StoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
