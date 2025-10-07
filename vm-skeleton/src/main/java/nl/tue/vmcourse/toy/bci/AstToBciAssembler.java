package nl.tue.vmcourse.toy.bci;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import nl.tue.vmcourse.toy.ast.ToyAddNode;
import nl.tue.vmcourse.toy.ast.ToyAstNode;
import nl.tue.vmcourse.toy.ast.ToyBigIntegerLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyBlockNode;
import nl.tue.vmcourse.toy.ast.ToyBooleanLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyBreakNode;
import nl.tue.vmcourse.toy.ast.ToyContinueNode;
import nl.tue.vmcourse.toy.ast.ToyDivNode;
import nl.tue.vmcourse.toy.ast.ToyEqualNode;
import nl.tue.vmcourse.toy.ast.ToyExpressionNode;
import nl.tue.vmcourse.toy.ast.ToyFunctionLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyIfNode;
import nl.tue.vmcourse.toy.ast.ToyInvokeNode;
import nl.tue.vmcourse.toy.ast.ToyLongLiteralNode;
import nl.tue.vmcourse.toy.ast.ToyMulNode;
import nl.tue.vmcourse.toy.ast.ToyParenExpressionNode;
import nl.tue.vmcourse.toy.ast.ToyReadArgumentNode;
import nl.tue.vmcourse.toy.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.ast.ToyUnboxNode;
import nl.tue.vmcourse.toy.ast.ToyWhileNode;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.parser.ToyLangVisitor;

public class AstToBciAssembler {
    public static ToyAbstractFunctionBody build(ToyStatementNode methodBlock, Map<String, RootCallTarget> allFunctions) {
        AstToBciAssembler assembler = new AstToBciAssembler();
        Program program = assembler.compileAst(methodBlock);
        for(String s : allFunctions.keySet()) {
            System.out.println("FUNCTION: " + allFunctions.get(s).getRootNode().getFunctionName());
        }
        
        return new ToyBciLoop(program, allFunctions);
    }

    private Program compileAst(ToyStatementNode methodBlock) {
        AstVisitor visitor = new AstVisitor();
        Program program = visitor.build(methodBlock);
        
        return program;
    }
}
