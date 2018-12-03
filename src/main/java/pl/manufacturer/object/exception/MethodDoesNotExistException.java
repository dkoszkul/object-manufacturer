package pl.manufacturer.object.exception;

public class MethodDoesNotExistException extends RuntimeException {

    public MethodDoesNotExistException(String message) {
        super(message);
    }
}
