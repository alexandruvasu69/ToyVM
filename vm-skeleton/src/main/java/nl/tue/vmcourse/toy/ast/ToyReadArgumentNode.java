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
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    public int getParameterCount() {
        return parameterCount;
    }
}
