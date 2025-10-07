package nl.tue.vmcourse.toy.ast;

import java.math.BigInteger;

import nl.tue.vmcourse.toy.bci.IAstVisitor;

public class ToyBigIntegerLiteralNode extends ToyExpressionNode {
    private final BigInteger bigInteger;

    public ToyBigIntegerLiteralNode(BigInteger bigInteger) {
        super();
        this.bigInteger = bigInteger;
    }

    @Override
    public String toString() {
        return "ToyBigIntegerLiteralNode{" +
                "bigInteger=" + bigInteger +
                '}';
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    @Override
    public void accept(IAstVisitor visitor) {
        visitor.visit(this);
    }
}
