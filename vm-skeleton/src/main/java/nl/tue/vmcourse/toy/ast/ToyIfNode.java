package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyIfNode extends ToyStatementNode {
    private final ToyExpressionNode conditionNode;
    private final ToyStatementNode thenPartNode;
    private final ToyStatementNode elsePartNode;

    public ToyIfNode(ToyExpressionNode conditionNode, ToyStatementNode thenPartNode, ToyStatementNode elsePartNode) {
        this.conditionNode = conditionNode;
        this.thenPartNode = thenPartNode;
        this.elsePartNode = elsePartNode;
    }

    

    @Override
    public String toString() {
        return "ToyIfNode{" +
                "conditionNode=" + conditionNode +
                ", thenPartNode=" + thenPartNode +
                ", elsePartNode=" + elsePartNode +
                '}';
    }

    public ToyExpressionNode getConditionNode() {
        return conditionNode;
    }

    public ToyStatementNode getThenPartNode() {
        return thenPartNode;
    }

    public ToyStatementNode getElsePartNode() {
        return elsePartNode;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
