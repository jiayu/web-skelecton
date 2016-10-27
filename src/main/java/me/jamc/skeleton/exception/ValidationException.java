package me.jamc.skeleton.exception;

/**
 * Created by Jamc on 10/27/16.
 */
public class ValidationException extends RuntimeException {

    private String message;

    public ValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
