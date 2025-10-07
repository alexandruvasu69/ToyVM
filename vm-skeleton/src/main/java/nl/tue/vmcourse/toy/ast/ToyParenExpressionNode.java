package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyParenExpressionNode extends ToyExpressionNode {
    private final ToyExpressionNode expressionNode;

    public ToyParenExpressionNode(ToyExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    @Override
    public String toString() {
        return "ToyParenExpressionNode{" +
                "expressionNode=" + expressionNode +
                '}';
    }

    public ToyExpressionNode getExpressionNode() {
        return expressionNode;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
