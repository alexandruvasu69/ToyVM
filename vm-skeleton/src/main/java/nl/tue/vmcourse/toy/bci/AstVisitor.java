package nl.tue.vmcourse.toy.bci;

import java.util.ArrayDeque;
import java.util.Deque;

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

public class AstVisitor implements IAstVisitor {
    private ProgramBuilder programBuilder = new ProgramBuilder();
    private final Deque<LoopContext> loopStack = new ArrayDeque<>();

    private class LoopContext {
        Label start;
        Label end;
        LoopContext(Label start, Label end) {
            this.start = start;
            this.end = end;
        }
    }

     @Override
    public Program build(ToyStatementNode node) {
        node.accept(this);
        return programBuilder.build();
    }

    @Override
    public void visit(ToyBlockNode node) {
        if(node.getChildren().size() > 0) {
                for(ToyAstNode n : node.getChildren()) {
                    n.accept(this);
                }
            }
    }

    @Override
    public void visit(ToyAddNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.ADD);
    }

    @Override
    public void visit(ToySubNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.SUB);
    }

    @Override
    public void visit(ToyMulNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.MUL);
    }

    @Override
    public void visit(ToyDivNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.DIV);
    }

    @Override
    public void visit(ToyParenExpressionNode node) {
        node.getExpressionNode().accept(this);
    }

    @Override
    public void visit(ToyBigIntegerLiteralNode node) {
        int index = programBuilder.addConst(node.getBigInteger());
        programBuilder.emit(Opcode.ICONST);
        programBuilder.emitInt(index);
    }

    @Override
    public void visit(ToyBooleanLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);
    }

    @Override
    public void visit(ToyLongLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.ICONST);
        programBuilder.emitInt(index);
    }

    @Override
    public void visit(ToyStringLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.SCONST);
        programBuilder.emitInt(index);
    }

    @Override
    public void visit(ToyWhileNode node) {
        Label labelStart = programBuilder.newLabel();
        Label labelEnd = programBuilder.newLabel();

        LoopContext lc = new LoopContext(labelStart, labelEnd);
        loopStack.push(lc);

        programBuilder.mark(labelStart);
        node.getConditionNode().accept(this);
        programBuilder.emitJump(Opcode.JZ, labelEnd);
        node.getBodyNode().accept(this);
        programBuilder.emitJump(Opcode.JMP, labelStart);
        programBuilder.mark(labelEnd);
        loopStack.pop();
    }

    @Override
    public void visit(ToyBreakNode node) {
        LoopContext loop = loopStack.peek();
        if(loop == null) {
            throw new IllegalStateException();
        }
        programBuilder.emitJump(Opcode.JMP, loop.end);
    }

    @Override
    public void visit(ToyContinueNode node) {
        LoopContext loop = loopStack.peek();
        if(loop == null) {
            throw new IllegalStateException();
        }
        programBuilder.emitJump(Opcode.JMP, loop.start);
    }

    @Override
    public void visit(ToyUnaryMinNode node) {
        node.getExp().accept(this);
        programBuilder.emit(Opcode.UMIN);
    }

    @Override
    public void visit(ToyLogicalNotNode node) {
        node.getToyLessOrEqualNode().accept(this);
        programBuilder.emit(Opcode.NEG);
    }

    @Override
    public void visit(ToyLogicalOrNode node) {
        Label labelTrue = programBuilder.newLabel();
        Label labelEnd = programBuilder.newLabel();

        node.getLeftUnboxed().accept(this);
        programBuilder.emitJump(Opcode.JNZ, labelTrue);

        node.getRightUnboxed().accept(this);
        programBuilder.emitJump(Opcode.JNZ, labelTrue);

        int index = programBuilder.addConst(false);
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);

        programBuilder.emitJump(Opcode.JMP, labelEnd);

        programBuilder.mark(labelTrue);
         index = programBuilder.addConst(true);
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);

        programBuilder.mark(labelEnd);
    }

    @Override
    public void visit(ToyLogicalAndNode node) {
        Label labelFalse = programBuilder.newLabel();
        Label labelEnd = programBuilder.newLabel();
        
        node.getLeftUnboxed().accept(this);
        programBuilder.emitJump(Opcode.JZ, labelFalse);

        node.getRightUnboxed().accept(this);
        programBuilder.emitJump(Opcode.JZ, labelFalse);

        int index = programBuilder.addConst(true);
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);

        programBuilder.emitJump(Opcode.JMP, labelEnd);

        programBuilder.mark(labelFalse);
        index = programBuilder.addConst(false);
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);

        programBuilder.mark(labelEnd);
    }

    @Override
    public void visit(ToyEqualNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.EQ);
    }

    @Override
    public void visit(ToyLessOrEqualNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.LE);
    }

    @Override
    public void visit(ToyLessThanNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.LT);
    }

    @Override
    public void visit(ToyIfNode node) {
        node.getConditionNode().accept(this);
        Label labelElse = programBuilder.newLabel();
        Label labelEnd = programBuilder.newLabel();
        programBuilder.emitJump(Opcode.JZ, labelElse);
        node.getThenPartNode().accept(this);
        
        programBuilder.emitJump(Opcode.JMP, labelEnd);
        programBuilder.mark(labelElse);
        if(node.getElsePartNode() != null) {
            node.getElsePartNode().accept(this);
        }
        programBuilder.mark(labelEnd);
    }

    @Override
    public void visit(ToyUnboxNode node) {
        node.getLeftNode().accept(this);
    }

    @Override
    public void visit(ToyInvokeNode node) {
        node.getFunctionNode().accept(this);

        for(ToyExpressionNode expressionNode : node.getToyExpressionNodes()) {
            expressionNode.accept(this);
        }
        
        programBuilder.emit(Opcode.CALL);
        programBuilder.emitInt(node.getToyExpressionNodes().length);
    }

    @Override
    public void visit(ToyFunctionLiteralNode node) {
        int funcIndex = programBuilder.addConst(node.getName());
        programBuilder.emit(Opcode.FCONST);
        programBuilder.emitInt(funcIndex);
    }

    @Override
    public void visit(ToyReturnNode node) {
        if(node.getValueNode() == null) {
            int index = programBuilder.addConst(null);
            programBuilder.emit(Opcode.ICONST);
            programBuilder.emitInt(index);
        } else {
            node.getValueNode().accept(this);
        }

        programBuilder.emit(Opcode.RET);
    }

    @Override
    public void visit(ToyWriteLocalVariableNode node) {
        node.getValueNode().accept(this);
        programBuilder.emit(Opcode.STORE);
        programBuilder.emitInt(node.getFrameSlot());
    }

    @Override
    public void visit(ToyReadArgumentNode node) {
        programBuilder.emit(Opcode.LOAD_ARG);
        programBuilder.emitInt(node.getParameterCount());
    }

    @Override
    public void visit(ToyReadLocalVariableNode node) {
        programBuilder.emit(Opcode.LOAD);
        programBuilder.emitInt(node.getFrameSlot());
    }

    @Override
    public void visit(ToyReadPropertyNode node) {
        node.getReceiverNode().accept(this);
        node.getNameNode().accept(this);
        programBuilder.emit(Opcode.READ);
    }

    @Override
    public void visit(ToyWritePropertyNode node) {
        node.getReceiverNode().accept(this);
        node.getNameNode().accept(this);
        node.getValueNode().accept(this);

        programBuilder.emit(Opcode.WRITE);
    }
}
