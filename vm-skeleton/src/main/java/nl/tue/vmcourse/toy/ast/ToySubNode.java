package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToySubNode extends ToyExpressionNode {
    private final ToyExpressionNode leftUnboxed;
    private final ToyExpressionNode rightUnboxed;

    public ToySubNode(ToyExpressionNode leftUnboxed, ToyExpressionNode rightUnboxed) {
        super();
        this.leftUnboxed = leftUnboxed;
        this.rightUnboxed = rightUnboxed;
    }

    @Override
    public String toString() {
        return "ToySubNode{" +
                "leftUnboxed=" + leftUnboxed +
                ", rightUnboxed=" + rightUnboxed +
                '}';
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    public ToyExpressionNode getLeftUnboxed() {
        return leftUnboxed;
    }

    public ToyExpressionNode getRightUnboxed() {
        return rightUnboxed;
    }
}
