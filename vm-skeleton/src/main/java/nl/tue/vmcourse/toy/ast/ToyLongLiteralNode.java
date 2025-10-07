package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyLongLiteralNode extends ToyExpressionNode {
    private final long value;

    public ToyLongLiteralNode(long value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        return "ToyLongLiteralNode{" +
                "value=" + value +
                '}';
    }

    public long getValue() {
        return value;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
