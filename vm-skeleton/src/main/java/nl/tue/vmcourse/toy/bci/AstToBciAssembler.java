package nl.tue.vmcourse.toy.bci;

import java.util.Map;

import nl.tue.vmcourse.toy.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.RootCallTarget;

public class AstToBciAssembler {
    public static ToyAbstractFunctionBody build(ToyStatementNode methodBlock, Map<String, RootCallTarget> allFunctions) {
        AstToBciAssembler assembler = new AstToBciAssembler();
        Program program = assembler.compileAst(methodBlock);
        
        return new ToyBciLoop(program, allFunctions);
    }

    private Program compileAst(ToyStatementNode methodBlock) {
        AstVisitor visitor = new AstVisitor();
        Program program = visitor.build(methodBlock);
        
        return program;
    }
}
