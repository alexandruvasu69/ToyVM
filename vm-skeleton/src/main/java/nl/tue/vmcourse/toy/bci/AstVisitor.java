package nl.tue.vmcourse.toy.bci;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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
import nl.tue.vmcourse.toy.ast.ToyNewObjectNode;
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
import nl.tue.vmcourse.toy.ast.ToyUndefNode;
import nl.tue.vmcourse.toy.ast.ToyWhileNode;
import nl.tue.vmcourse.toy.ast.ToyWriteLocalVariableNode;
import nl.tue.vmcourse.toy.ast.ToyWritePropertyNode;

public class AstVisitor implements IAstVisitor<Void> {
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
    public Void visit(ToyBlockNode node) {
        if(node.getChildren().size() > 0) {
                for(ToyAstNode n : node.getChildren()) {
                    n.accept(this);
                }
            }
        return null;
    }

    @Override
    public Void visit(ToyAddNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.ADD);
        return null;
    }

    @Override
    public Void visit(ToySubNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.SUB);
        return null;
    }

    @Override
    public Void visit(ToyMulNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.MUL);
        return null;
    }

    @Override
    public Void visit(ToyDivNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.DIV);
        return null;
    }

    @Override
    public Void visit(ToyParenExpressionNode node) {
        node.getExpressionNode().accept(this);
        return null;
    }

    @Override
    public Void visit(ToyBigIntegerLiteralNode node) {
        int index = programBuilder.addConst(node.getBigInteger());
        programBuilder.emit(Opcode.ICONST);
        programBuilder.emitInt(index);
        
        return null;
    }

    @Override
    public Void visit(ToyBooleanLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.BCONST);
        programBuilder.emitInt(index);
        return null;
    }

    @Override
    public Void visit(ToyLongLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.ICONST);
        programBuilder.emitInt(index);

        return null;
    }

    @Override
    public Void visit(ToyStringLiteralNode node) {
        int index = programBuilder.addConst(node.getValue());
        programBuilder.emit(Opcode.ICONST);
        programBuilder.emitInt(index);

        return null;
    }

    @Override
    public Void visit(ToyWhileNode node) {
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

        return null;
    }

    @Override
    public Void visit(ToyBreakNode node) {
        LoopContext loop = loopStack.peek();
        if(loop == null) {
            throw new IllegalStateException();
        }
        programBuilder.emitJump(Opcode.JMP, loop.end);
        return null;
    }

    @Override
    public Void visit(ToyContinueNode node) {
        LoopContext loop = loopStack.peek();
        if(loop == null) {
            throw new IllegalStateException();
        }
        programBuilder.emitJump(Opcode.JMP, loop.start);
        return null;
    }

    @Override
    public Void visit(ToyUnaryMinNode node) {
        // TODO: Implement unaryMin node
        return null;
    }

    @Override
    public Void visit(ToyLogicalNotNode node) {
        node.getToyLessOrEqualNode().accept(this);
        programBuilder.emit(Opcode.NEG);
        return null;
    }

    @Override
    public Void visit(ToyLogicalOrNode node) {
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

        return null;
    }

    @Override
    public Void visit(ToyLogicalAndNode node) {
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

        return null;
    }

    @Override
    public Void visit(ToyEqualNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.EQ);
        return null;
    }

    @Override
    public Void visit(ToyLessOrEqualNode node) {
        // TODO: Implement LE node
        return null;
    }

    @Override
    public Void visit(ToyLessThanNode node) {
        node.getLeftUnboxed().accept(this);
        node.getRightUnboxed().accept(this);
        programBuilder.emit(Opcode.LT);
        return null;
    }

    @Override
    public Void visit(ToyIfNode node) {
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

        return null;
    }

    @Override
    public Void visit(ToyUnboxNode node) {
        node.getLeftNode().accept(this);
        return null;
    }

    @Override
    public Void visit(ToyInvokeNode node) {
        for(ToyExpressionNode expressionNode : node.getToyExpressionNodes()) {
            expressionNode.accept(this);
        }
        
        programBuilder.emit(Opcode.CALL);
        node.getFunctionNode().accept(this);
        programBuilder.emitInt(node.getToyExpressionNodes().length);

        return null;
    }

    @Override
    public Void visit(ToyFunctionLiteralNode node) {
        int funcIndex = programBuilder.addConst(node.getName());
        programBuilder.emitInt(funcIndex);
        return null;
    }

    @Override
    public Void visit(ToyReturnNode node) {
        if(node.getValueNode() == null) {
            int index = programBuilder.addConst(ToyUndefNode.UNDEF);
            programBuilder.emit(Opcode.ICONST);
            programBuilder.emitInt(index);
        } else {
            node.getValueNode().accept(this);
        }

        programBuilder.emit(Opcode.RET);
        return null;
    }

    @Override
    public Void visit(ToyWriteLocalVariableNode node) {
        node.getValueNode().accept(this);
        programBuilder.emit(Opcode.STORE);
        programBuilder.emitInt(node.getFrameSlot());
        return null;
    }

    @Override
    public Void visit(ToyReadArgumentNode node) {
        programBuilder.emit(Opcode.LOAD_ARG);
        programBuilder.emitInt(node.getParameterCount());
        return null;
    }

    @Override
    public Void visit(ToyReadLocalVariableNode node) {
        programBuilder.emit(Opcode.LOAD);
        programBuilder.emitInt(node.getFrameSlot());
        return null;
    }

    @Override
    public Void visit(ToyNewObjectNode node) {
        // TODO: Implement NewObject node
        // throw new UnsupportedOperationException("Unimplemented method 'visit'");
        return null;
    }

    @Override
    public Void visit(ToyUndefNode node) {
        // TODO: Implement Undef node
        // throw new UnsupportedOperationException("Unimplemented method 'visit'");
        return null;
    }

    @Override
    public Void visit(ToyReadPropertyNode node) {
        // TODO: Implement ReadProperty node
        // throw new UnsupportedOperationException("Unimplemented method 'visit'");
        node.getNameNode().accept(this);
        programBuilder.emit(Opcode.READ);
        programBuilder.emitInt(((ToyReadLocalVariableNode)node.getReceiverNode()).getFrameSlot());

        return null;
    }

    @Override
    public Void visit(ToyWritePropertyNode node) {
        node.getNameNode().accept(this);
        node.getValueNode().accept(this);

        programBuilder.emit(Opcode.WRITE);
        programBuilder.emitInt(((ToyReadLocalVariableNode)node.getReceiverNode()).getFrameSlot());
        
        return null;
    }
}
