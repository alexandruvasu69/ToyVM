package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyDivNode extends ToyExpressionNode {
    private final ToyExpressionNode leftUnboxed;
    private final ToyExpressionNode rightUnboxed;

    public ToyDivNode(ToyExpressionNode leftUnboxed, ToyExpressionNode rightUnboxed) {
        this.leftUnboxed = leftUnboxed;
        this.rightUnboxed = rightUnboxed;
    }

    @Override
    public String toString() {
        return "ToyDivNode{" +
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
