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

public class AstToBciAssembler {
    private ProgramBuilder programBuilder = new ProgramBuilder();
    private Map<String, Integer> locals = new HashMap<>();
    private int nextSlot = 0;
    private final Deque<LoopContext> loopStack = new ArrayDeque<>();

    private class LoopContext {
        Label head;
        Label tail;
        LoopContext(Label head, Label tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public static ToyAbstractFunctionBody build(ToyStatementNode methodBlock) {
        AstToBciAssembler assembler = new AstToBciAssembler();
        byte[] code = assembler.compileAst(methodBlock);
        
        // TODO code is one argument; depending in impl other arguments might be needed (e.g., constant pool?)
        return new ToyBciLoop(code);
    }

    private byte[] compileAst(ToyStatementNode methodBlock) {
        emitStatement(methodBlock);
        Program program = programBuilder.build();
        for(byte b : program.code) {
            System.out.printf("%02X ", (b & 0xFF));
        }
        // TODO should explore AST and produce BC instructions.
        return new byte[] {42};
    }

    private void emitStatement(ToyAstNode node) {
        if(node instanceof ToyBlockNode) {
            ToyBlockNode blockNode = (ToyBlockNode)node;
            if(blockNode.getChildren().size() > 0) {
                for(ToyAstNode n : blockNode.getChildren()) {
                    emitStatement(n);
                }
            }
        } else if(node instanceof ToyBreakNode) {
            LoopContext loop = loopStack.peek();
            if(loop == null) {
                throw new IllegalStateException();
            }
            programBuilder.emitJump(Opcode.JMP, loop.tail);
        } else if(node instanceof ToyContinueNode)  {
            LoopContext loop = loopStack.peek();
            if(loop == null) {
                throw new IllegalStateException();
            }
            programBuilder.emitJump(Opcode.JMP, loop.head);
        } else if(node instanceof ToyIfNode) {
            ToyIfNode ifnode = (ToyIfNode)node;
            emitExpression(ifnode.getConditionNode());
            Label labelElse = programBuilder.newLabel();
            Label labelEnd = programBuilder.newLabel();
            programBuilder.emitJump(Opcode.JZ, labelElse);
            emitStatement(ifnode.getThenPartNode());
            programBuilder.emitJump(Opcode.JMP, labelEnd);
            programBuilder.mark(labelElse);
            if(ifnode.getElsePartNode() != null) {
                emitStatement(ifnode.getElsePartNode());
            }
            programBuilder.mark(labelEnd);
        } else if(node instanceof ToyWhileNode) {
            ToyWhileNode whileNode = (ToyWhileNode)node;

            Label labelStart = programBuilder.newLabel();
            Label labelEnd = programBuilder.newLabel();

            LoopContext lc = new LoopContext(labelStart, labelEnd);
            loopStack.push(lc);

            programBuilder.mark(labelStart);
            emitExpression(whileNode.getConditionNode());
            programBuilder.emitJump(Opcode.JZ, labelEnd);
            emitStatement(whileNode.getBodyNode());
            programBuilder.emitJump(Opcode.JMP, labelStart);
            programBuilder.mark(labelEnd);
            loopStack.pop();
        } else if(node instanceof ToyExpressionNode) {
            emitExpression(node);
        }
    }

    private void emitExpression(ToyAstNode node) {
        if(node instanceof ToyAddNode) {
            ToyAddNode addNode = (ToyAddNode)node;
            emitExpression(addNode.getLeftUnboxed());
            emitExpression(addNode.getRightUnboxed());
            programBuilder.emit(Opcode.ADD);
        } else if(node instanceof ToyUnboxNode) {
          ToyUnboxNode unboxNode = (ToyUnboxNode)node;
          emitExpression(unboxNode.getLeftNode());
        } else if(node instanceof ToyLongLiteralNode) {
            ToyLongLiteralNode integerNode = (ToyLongLiteralNode)node;
            int index = programBuilder.addConst(integerNode.getValue());
            programBuilder.emit(Opcode.ICONST);
            programBuilder.emitInt(index);
        } else if(node instanceof ToyBigIntegerLiteralNode) {
            ToyBigIntegerLiteralNode integerNode = (ToyBigIntegerLiteralNode)node;
            int index = programBuilder.addConst(integerNode.getBigInteger());
            programBuilder.emit(Opcode.ICONST);
            programBuilder.emitInt(index);
        } else if(node instanceof ToyBooleanLiteralNode) {
            ToyBooleanLiteralNode booleanNode = (ToyBooleanLiteralNode)node;
            int index = programBuilder.addConst(booleanNode.getValue());
            programBuilder.emit(Opcode.BCONST);
            programBuilder.emitInt(index);
        } else if(node instanceof ToyEqualNode) {
            ToyEqualNode equalNode = (ToyEqualNode)node;
            emitExpression(equalNode.getLeftUnboxed());
            emitExpression(equalNode.getRightUnboxed());
            programBuilder.emit(Opcode.EQ);
        } else if(node instanceof ToyMulNode) {
            ToyMulNode mulNode = (ToyMulNode)node;
            emitExpression(mulNode.getLeftUnboxed());
            emitExpression(mulNode.getRightUnboxed());
            programBuilder.emit(Opcode.MUL);
        } else if(node instanceof ToyDivNode) {
            ToyDivNode mulNode = (ToyDivNode)node;
            emitExpression(mulNode.getLeftUnboxed());
            emitExpression(mulNode.getRightUnboxed());
            programBuilder.emit(Opcode.DIV);
        } else if(node instanceof ToyInvokeNode) {
            ToyInvokeNode invokeNode = (ToyInvokeNode)node;
            for(ToyExpressionNode expressionNode : invokeNode.getToyExpressionNodes()) {
                emitExpression(expressionNode);
            }
            ToyFunctionLiteralNode functionLiteralNode = (ToyFunctionLiteralNode) invokeNode.getFunctionNode();
            int funcIndex = programBuilder.addConst(functionLiteralNode.getName());

            programBuilder.emit(Opcode.CALL);
            programBuilder.emitInt(funcIndex);
            programBuilder.emitInt(invokeNode.getToyExpressionNodes().length);
        } else if(node instanceof ToyParenExpressionNode) {
            ToyParenExpressionNode parenExpressionNode = (ToyParenExpressionNode)node;
            emitExpression(parenExpressionNode.getExpressionNode());
        }
    }
}
