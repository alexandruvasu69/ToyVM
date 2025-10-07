package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyWriteLocalVariableNode extends ToyExpressionNode {
    private final ToyExpressionNode valueNode;
    private final Integer frameSlot;
    private final ToyExpressionNode nameNode;
    private final boolean newVariable;

    public ToyWriteLocalVariableNode(ToyExpressionNode valueNode, Integer frameSlot, ToyExpressionNode nameNode, boolean newVariable) {
        super();
        this.valueNode = valueNode;
        this.frameSlot = frameSlot;
        this.nameNode = nameNode;
        this.newVariable = newVariable;
    }

    @Override
    public String toString() {
        return "ToyWriteLocalVariableNode{" +
                "valueNode=" + valueNode +
                ", frameSlot=" + frameSlot +
                ", nameNode=" + nameNode +
                ", newVariable=" + newVariable +
                '}';
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public ToyExpressionNode getValueNode() {
        return valueNode;
    }

    public Integer getFrameSlot() {
        return frameSlot;
    }

    public ToyExpressionNode getNameNode() {
        return nameNode;
    }

    public boolean isNewVariable() {
        return newVariable;
    }
}
