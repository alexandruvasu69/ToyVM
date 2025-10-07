package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyAddNode extends ToyExpressionNode {
    private final ToyExpressionNode leftUnboxed;
    private final ToyExpressionNode rightUnboxed;

    public ToyAddNode(ToyExpressionNode leftUnboxed, ToyExpressionNode rightUnboxed) {
        super();
        this.leftUnboxed = leftUnboxed;
        this.rightUnboxed = rightUnboxed;
    }

    @Override
    public String toString() {
        return "ToyAddNode{" +
                "leftUnboxed=" + leftUnboxed +
                ", rightUnboxed=" + rightUnboxed +
                '}';
    }

    public ToyExpressionNode getLeftUnboxed() {
        return leftUnboxed;
    }

    public ToyExpressionNode getRightUnboxed() {
        return rightUnboxed;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
