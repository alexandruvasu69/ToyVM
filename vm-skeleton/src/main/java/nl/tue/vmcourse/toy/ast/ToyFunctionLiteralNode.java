package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyFunctionLiteralNode extends ToyExpressionNode {
    private final String name;

    public ToyFunctionLiteralNode(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "ToyFunctionLiteralNode{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    @Override
    public <R> R accept(IAstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
