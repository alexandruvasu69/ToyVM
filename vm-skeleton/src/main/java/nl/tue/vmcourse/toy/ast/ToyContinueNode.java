package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyContinueNode extends ToyStatementNode {

    @Override
    public String toString() {
        return "continue";
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
