package nl.tue.vmcourse.toy.lang.exceptions;

public class ToyTypeException extends ToyException {
    public ToyTypeException(String message) {
        super("Type error: " + message);
    }
}
