package nl.tue.vmcourse.toy.lang;

public enum NullValue {
    INSTANCE;

    public static Object getUnboxed(Object object) {
        return (object == INSTANCE) ? null : object;
    }

    public static Object boxValue(Object object) {
        return (object == null) ? INSTANCE : object;
    }
}
