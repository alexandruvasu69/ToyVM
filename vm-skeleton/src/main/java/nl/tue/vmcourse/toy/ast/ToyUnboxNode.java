package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyUnboxNode extends ToyExpressionNode {
    private final ToyExpressionNode leftNode;

    public ToyUnboxNode(ToyExpressionNode leftNode) {
        super();
        this.leftNode = leftNode;
    }

    @Override
    public String toString() {
        return "ToyUnboxNode{" +
                "leftNode=" + leftNode +
                '}';
    }

    public ToyExpressionNode getLeftNode() {
        return leftNode;
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
