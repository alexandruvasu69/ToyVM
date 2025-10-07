package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyUnaryMinNode extends ToyExpressionNode {

    private final ToyExpressionNode exp;

    public ToyUnaryMinNode(ToyExpressionNode exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "ToyUnaryMinNode{" +
                "exp=" + exp +
                '}';
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
