package nl.tue.vmcourse.toy.lang.exceptions;

public class ToyException extends RuntimeException {
    public ToyException() {
        super("Exception occurred, see trace.log for more info");
    }

    public ToyException(String msg) {
        super(msg);
    }
}
