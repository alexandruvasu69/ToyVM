package nl.tue.vmcourse.toy.ast;

import java.util.Arrays;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyInvokeNode extends ToyExpressionNode {
    private final ToyExpressionNode functionNode;
    private final ToyExpressionNode[] toyExpressionNodes;

    public ToyInvokeNode(ToyExpressionNode functionNode, ToyExpressionNode[] toyExpressionNodes) {
        super();
        this.functionNode = functionNode;
        this.toyExpressionNodes = toyExpressionNodes;
    }

    @Override
    public String toString() {
        return "ToyInvokeNode{" +
                "functionNode=" + functionNode +
                ", toyExpressionNodes=" + Arrays.toString(toyExpressionNodes) +
                '}';
    }

    public ToyExpressionNode getFunctionNode() {
        return functionNode;
    }

    public ToyExpressionNode[] getToyExpressionNodes() {
        return toyExpressionNodes;
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
