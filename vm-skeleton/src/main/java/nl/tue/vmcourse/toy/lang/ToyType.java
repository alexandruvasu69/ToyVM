package nl.tue.vmcourse.toy.lang;

public class ToyType {
    public static final ToyType NUMBER = new ToyType("Number");
    public static final ToyType STRING = new ToyType("String");
    public static final ToyType BOOLEAN = new ToyType("Boolean");
    public static final ToyType NULL = new ToyType("NULL");
    public static final ToyType FUNCTION = new ToyType("Function");
    public static final ToyType OBJECT = new ToyType("Object");

    private String name;

    protected ToyType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object type) {
        String name = ((ToyType)type).toString();
        return this.name.equals(name);
    }
}