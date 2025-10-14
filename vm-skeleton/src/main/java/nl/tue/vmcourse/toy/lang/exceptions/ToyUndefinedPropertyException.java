package nl.tue.vmcourse.toy.lang.exceptions;

public class ToyUndefinedPropertyException extends ToyException {
    public ToyUndefinedPropertyException(Object property) {
        super("Undefined property: " + property);
    }
}
