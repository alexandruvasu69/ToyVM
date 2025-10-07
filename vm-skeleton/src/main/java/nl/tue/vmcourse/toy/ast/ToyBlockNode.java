package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.bci.IAstVisitor;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToyBlockNode extends ToyStatementNode {
    private final ToyStatementNode[] statements;

    public ToyBlockNode(ToyStatementNode[] nodes) {
        super();
        this.statements = nodes;
    }

    public Iterable<? extends ToyStatementNode> getStatements() {
        return List.of(statements);
    }

    public String printTree(String functionName) {
        return "ToyBlockNode{" +
                "functionName=" + functionName +
                ", statements=" + Arrays.toString(statements) +
                '}';
    }

    @Override
    public List<ToyAstNode> getChildren() {
        return new ArrayList<>(Arrays.asList(statements));
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
