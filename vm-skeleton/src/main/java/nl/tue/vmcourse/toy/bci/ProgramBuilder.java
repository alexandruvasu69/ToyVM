package nl.tue.vmcourse.toy.bci;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProgramBuilder {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream(256);
    private final List<Object> constants = new ArrayList<>();
    private final List<Label> allLabels = new ArrayList<>();

    public int pc() {
        return out.size();
    }

    public void emit(Opcode opcode) {
        out.write(opcode.code);
    }

    public void emitInt(int i) {
        out.write(i & 0xFF);
        out.write((i >>> 8) & 0xFF);
        out.write((i >>> 16) & 0xFF);
        out.write((i >>> 24) & 0xFF);
    }

    public Label newLabel() {
        Label l = new Label();
        allLabels.add(l);
        return l;
    }
    
    public void mark(Label l) {
        if(l.pos != -1) {
            throw new IllegalStateException();
        }
        l.pos = pc();
    }

    public void emitJump(Opcode jumpOp, Label l) {
        if(jumpOp != Opcode.JMP && jumpOp != Opcode.JNZ && jumpOp != Opcode.JZ) {
            throw new IllegalArgumentException();
        }
        emit(jumpOp);
        int opPos = pc();
        emitInt(0);
        l.patches.add(opPos);
    }

    public int addConst(Object c) {
        constants.add(c);
        return constants.size() - 1;
    }

    public Program build() {
        byte[] code = out.toByteArray();
        for(Label l : allLabels) {
            if(l.pos < 0) {
                throw new IllegalStateException();
            }
            for(int patch : l.patches) {
                int pcAfterOperand = patch + 4;
                System.out.println("PC AFTER OPERAND: " + pcAfterOperand);
                int relativePos = l.pos - pcAfterOperand;
                System.out.println("LABEL POS: " + l.pos);
                System.out.println("RELATIVE POS: " + relativePos);

                code[patch] = (byte) (relativePos & 0xFF);
                code[patch + 1] = (byte) ((relativePos >>> 8) & 0xFF);
                code[patch + 2] = (byte) ((relativePos >>> 16) & 0xFF);
                code[patch + 3] = (byte) ((relativePos >>> 24) & 0xFF);
            }
        }
        Object[] pool = constants.toArray();
        return new Program(code, pool);
    }
}
