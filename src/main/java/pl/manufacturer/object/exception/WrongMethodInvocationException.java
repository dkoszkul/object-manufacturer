package pl.manufacturer.object.exception;

public class WrongMethodInvocationException extends RuntimeException {

    public WrongMethodInvocationException(String message, Exception exception) {
        super(message, exception);
    }
}
