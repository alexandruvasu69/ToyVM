package nl.tue.vmcourse.toy.lang;

public class ToyTypeWrapper extends ToyType {
    private ToyType value;

    public ToyTypeWrapper(ToyType value) {
        super("Type");
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Type";
    }

    @Override
    public boolean equals(Object type) {
        String name = ((ToyType)type).toString();
        return this.toString().equals(name);
    }
}
