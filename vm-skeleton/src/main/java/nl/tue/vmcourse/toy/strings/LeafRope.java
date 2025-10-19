package nl.tue.vmcourse.toy.strings;

public class LeafRope extends Rope{
    private String string;
    private String cache;

    public LeafRope(String string) {
        this.string = string;
    }

    @Override
    public Rope concat(Rope rope) {
        return new ConcatRope(this, rope);
    }

    @Override
    public String init() {
        return cache == null ? (cache=string) : cache;
    }

    @Override
    public int getLen() {
        return this.string.length();
    }

    @Override
    public char getChar(int index) {
        return string.charAt(index);
    }
    
}
