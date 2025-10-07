package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyWhileNode extends ToyStatementNode {
    private final ToyExpressionNode conditionNode;
    private final ToyStatementNode bodyNode;

    public ToyWhileNode(ToyExpressionNode conditionNode, ToyStatementNode bodyNode) {
        this.conditionNode = conditionNode;
        this.bodyNode = bodyNode;
    }

    

    @Override
    public String toString() {
        return "ToyWhileNode{" +
                "conditionNode=" + conditionNode +
                ", bodyNode=" + bodyNode +
                '}';
    }

    public ToyExpressionNode getConditionNode() {
        return conditionNode;
    }

    public ToyStatementNode getBodyNode() {
        return bodyNode;
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
