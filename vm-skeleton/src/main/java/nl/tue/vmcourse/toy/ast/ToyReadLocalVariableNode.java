package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyReadLocalVariableNode extends ToyExpressionNode {
    private final Integer frameSlot;

    public ToyReadLocalVariableNode(Integer frameSlot) {
        super();
        this.frameSlot = frameSlot;
    }

    @Override
    public String toString() {
        return "ToyReadLocalVariableNode{" +
                "frameSlot=" + frameSlot +
                '}';
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }

    public Integer getFrameSlot() {
        return frameSlot;
    }

    
}
