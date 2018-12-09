package pl.manufacturer.object.exception;

public class ObjectIsNotAnEntityException extends RuntimeException {

    public ObjectIsNotAnEntityException(String message) {
        super(message);
    }
}
