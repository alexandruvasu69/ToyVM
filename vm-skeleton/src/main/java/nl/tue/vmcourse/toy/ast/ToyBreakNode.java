package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyBreakNode extends ToyStatementNode {

    @Override
    public String toString() {
        return "break";
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
