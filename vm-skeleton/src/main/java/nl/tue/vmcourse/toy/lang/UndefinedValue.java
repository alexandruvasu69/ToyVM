package nl.tue.vmcourse.toy.lang;

public class UndefinedValue {
    private final String name;
    
    public UndefinedValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
