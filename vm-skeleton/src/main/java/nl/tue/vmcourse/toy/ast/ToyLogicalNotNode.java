package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyLogicalNotNode extends ToyExpressionNode {
    private final ToyExpressionNode toyLessOrEqualNode;

    public ToyLogicalNotNode(ToyLessOrEqualNode toyLessOrEqualNode) {
        super();
        this.toyLessOrEqualNode = toyLessOrEqualNode;
    }

    public ToyLogicalNotNode(ToyExpressionNode toyLessThanNode) {
        this.toyLessOrEqualNode = toyLessThanNode;
    }

    @Override
    public String toString() {
        return "ToyLogicalNotNode{" +
                "toyLessOrEqualNode=" + toyLessOrEqualNode +
                '}';
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public ToyExpressionNode getToyLessOrEqualNode() {
        return toyLessOrEqualNode;
    }
}
