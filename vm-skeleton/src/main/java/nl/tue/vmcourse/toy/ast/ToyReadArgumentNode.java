package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyReadArgumentNode extends ToyExpressionNode {
    private final int parameterCount;

    public ToyReadArgumentNode(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    @Override
    public String toString() {
        return "ToyReadArgumentNode{" +
                "parameterCount=" + parameterCount +
                '}';
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public int getParameterCount() {
        return parameterCount;
    }
}
