package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.ToyAddNode;
import nl.tue.vmcourse.toy.ast.ToyBigIntegerLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyBlockNode;
import nl.tue.vmcourse.toy.ast.ToyBooleanLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyBreakNode;
import nl.tue.vmcourse.toy.ast.ToyContinueNode;
import nl.tue.vmcourse.toy.ast.ToyDivNode;
import nl.tue.vmcourse.toy.ast.ToyEqualNode;
import nl.tue.vmcourse.toy.ast.ToyFunctionLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyIfNode;
import nl.tue.vmcourse.toy.ast.ToyInvokeNode;
import nl.tue.vmcourse.toy.ast.ToyLessOrEqualNode;
import nl.tue.vmcourse.toy.ast.ToyLessThanNode;
import nl.tue.vmcourse.toy.ast.ToyLogicalAndNode;
import nl.tue.vmcourse.toy.ast.ToyLogicalNotNode;
import nl.tue.vmcourse.toy.ast.ToyLogicalOrNode;
import nl.tue.vmcourse.toy.ast.ToyLongLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyMulNode;
import nl.tue.vmcourse.toy.ast.ToyParenExpressionNode;
import nl.tue.vmcourse.toy.ast.ToyReadArgumentNode;
import nl.tue.vmcourse.toy.ast.ToyReadLocalVariableNode;
import nl.tue.vmcourse.toy.ast.ToyReadPropertyNode;
import nl.tue.vmcourse.toy.ast.ToyReturnNode;
import nl.tue.vmcourse.toy.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.ast.ToyStringLiteralNode;
import nl.tue.vmcourse.toy.ast.ToySubNode;
import nl.tue.vmcourse.toy.ast.ToyUnaryMinNode;
import nl.tue.vmcourse.toy.ast.ToyUnboxNode;
import nl.tue.vmcourse.toy.ast.ToyWhileNode;
import nl.tue.vmcourse.toy.ast.ToyWriteLocalVariableNode;
import nl.tue.vmcourse.toy.ast.ToyWritePropertyNode;

public interface IAstVisitor {
    void visit(ToyBlockNode node);

    void visit(ToyAddNode node);
    void visit(ToySubNode node);
    void visit(ToyMulNode node);
    void visit(ToyDivNode node);
    
    void visit(ToyParenExpressionNode node);

    void visit(ToyBigIntegerLiteralNode node);
    void visit(ToyBooleanLiteralNode node);
    void visit(ToyLongLiteralNode node);
    void visit(ToyStringLiteralNode node);

    void visit(ToyWhileNode node);
    void visit(ToyBreakNode node);
    void visit(ToyContinueNode node);

    void visit(ToyUnaryMinNode node);

    void visit(ToyLogicalNotNode node);
    void visit(ToyLogicalOrNode node);
    void visit(ToyLogicalAndNode node);
    void visit(ToyEqualNode node);
    void visit(ToyLessOrEqualNode node);
    void visit(ToyLessThanNode node);

    void visit(ToyIfNode node);
    void visit(ToyUnboxNode node);
    
    void visit(ToyInvokeNode node);
    void visit(ToyFunctionLiteralNode node);
    void visit(ToyReturnNode node);
    void visit(ToyWriteLocalVariableNode node);
    void visit(ToyReadArgumentNode node);
    void visit(ToyReadLocalVariableNode node);
    
    void visit(ToyReadPropertyNode node);
    void visit(ToyWritePropertyNode node);

    Program build(ToyStatementNode node);
}
