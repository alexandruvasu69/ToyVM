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

public interface IAstVisitor<T> {
    T visit(ToyBlockNode node);

    T visit(ToyAddNode node);
    T visit(ToySubNode node);
    T visit(ToyMulNode node);
    T visit(ToyDivNode node);
    
    T visit(ToyParenExpressionNode node);

    T visit(ToyBigIntegerLiteralNode node);
    T visit(ToyBooleanLiteralNode node);
    T visit(ToyLongLiteralNode node);
    T visit(ToyStringLiteralNode node);

    T visit(ToyWhileNode node);
    T visit(ToyBreakNode node);
    T visit(ToyContinueNode node);

    T visit(ToyUnaryMinNode node);

    T visit(ToyLogicalNotNode node);
    T visit(ToyLogicalOrNode node);
    T visit(ToyLogicalAndNode node);
    T visit(ToyEqualNode node);
    T visit(ToyLessOrEqualNode node);
    T visit(ToyLessThanNode node);

    T visit(ToyIfNode node);
    T visit(ToyUnboxNode node);
    
    T visit(ToyInvokeNode node);
    T visit(ToyFunctionLiteralNode node);
    T visit(ToyReturnNode node);
    T visit(ToyWriteLocalVariableNode node);
    T visit(ToyReadArgumentNode node);
    T visit(ToyReadLocalVariableNode node);
    
    T visit(ToyReadPropertyNode node);
    T visit(ToyWritePropertyNode node);

    Program build(ToyStatementNode node);
}
