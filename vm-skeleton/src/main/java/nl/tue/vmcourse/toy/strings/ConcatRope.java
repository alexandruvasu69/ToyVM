package nl.tue.vmcourse.toy.strings;

public class ConcatRope extends Rope {
    private Rope left;
    private Rope right;
    private int len;
    private String cache;

    public ConcatRope(Rope left, Rope right) {
        this.left = left;
        this.right = right;
        this.len = this.left.getLen() + this.right.getLen();
    }

    @Override
    public Rope concat(Rope rope) {
        return new ConcatRope(this, rope);
    }

    @Override
    public String init() {
        if(cache == null) {
            StringBuilder sb = new StringBuilder(len);
            for(int i = 0; i < len; i ++) {
                sb.append(getChar(i));
            }
            
            cache = sb.toString();
        }

        return cache;
    }

    @Override
    public int getLen() {
        return this.len;
    }

    @Override
    public char getChar(int index) {
        if(index < left.getLen()) {
            return left.getChar(index);
        } else {
            return right.getChar(index - left.getLen());
        }
    }
    
}
