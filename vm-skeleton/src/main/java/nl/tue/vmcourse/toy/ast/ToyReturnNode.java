package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyReturnNode extends ToyStatementNode {
    private final ToyExpressionNode valueNode;

    public ToyReturnNode(ToyExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public String toString() {
        return "ToyReturnNode{" +
                "valueNode=" + valueNode +
                '}';
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    public ToyExpressionNode getValueNode() {
        return valueNode;
    }
}
