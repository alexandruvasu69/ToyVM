package nl.tue.vmcourse.toy.bci;

public final class Program {
    public final byte[] code;
    public final Object[] constants;

    public Program(byte[] code, Object[] constants) {
        this.code = code;
        this.constants = constants;
    }
}
