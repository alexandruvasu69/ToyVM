package nl.tue.vmcourse.toy.bci;

public enum Opcode {
    ICONST(0x01, OperandKind.INT32),
    SCONST(0x02, OperandKind.INT32),
    BCONST(0x03, OperandKind.INT32),
    FCONST(0x04, OperandKind.INT32),

    LOAD(0x10, OperandKind.INT32),
    STORE(0x11, OperandKind.INT32),
    LOAD_ARG(0x12, OperandKind.INT32),

    LOAD_QUICK(0x13),
    STORE_QUICK(0x14),

    ADD(0x20),
    MUL(0x21),
    SUB(0x22),
    DIV(0x23),
    NEG(0x24),
    UMIN(0x25),

    ADD_I(0x26),

    EQ(0x30),
    LT(0x31),
    LE(0x32),

    JMP(0x40, OperandKind.INT32),
    JZ(0x41, OperandKind.INT32),
    JNZ(0x42, OperandKind.INT32),
    AND_CHECK_LEFT(0x43),
    AND_CHECK_RIGHT(0x44),
    OR_CHECK_LEFT(0x45),
    OR_CHECK_RIGHT(0x46),
    IF_CHECK(0x47),
    WHILE_CHECK(0x48),

    CALL(0x50, OperandKind.INT32, OperandKind.INT32),
    RET(0x51, OperandKind.INT32),

    CALL_PRINT(0x52),
    CALL_NEW(0x53),
    CALL_QUICK(0x54),

    WRITE(0x60, OperandKind.INT32, OperandKind.INT32),
    READ(0x61, OperandKind.INT32, OperandKind.INT32);

    public final byte code;
    public final OperandKind[] operands;

    Opcode(int code, OperandKind... operands) {
        this.code = (byte) (code & 0xFF);
        this.operands = operands;
    }

    public enum OperandKind {
        INT32(4);

        public final int size;
        OperandKind(int size) {
            this.size = size;
        }
    }

    public int totalSize() {
        int size = 1;
        for(OperandKind op : operands) {
            size += op.size;
        }
        return size;
    }

    public boolean hasOperands() {
        return operands.length > 0;
    }

    public int numberOfOperands() {
        return operands.length;
    }

    private static final Opcode[] BY_CODE = new Opcode[255];
    static {
        for (Opcode op : Opcode.values()) {
            BY_CODE[op.code & 0xFF] = op;
        }
    }

    public static Opcode fromByte(int code) {
        Opcode op = BY_CODE[code & 0xFF];
        if(op == null) {
            throw new IllegalArgumentException();
        }
        return op;
    }
}
