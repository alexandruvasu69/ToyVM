package nl.tue.vmcourse.toy.strings;

public abstract class Rope {
    public abstract int getLen();
    public abstract Rope concat(Rope rope);
    public abstract String init();
    public abstract char getChar(int index);

    @Override
    public String toString() {
        return init();
    }
    
}