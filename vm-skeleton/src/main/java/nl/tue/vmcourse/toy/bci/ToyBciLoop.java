package nl.tue.vmcourse.toy.bci;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

import org.codehaus.commons.nullanalysis.Nullable;

import nl.tue.vmcourse.toy.ToyLauncher;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyRootNode;
import nl.tue.vmcourse.toy.lang.CallFrame;
import nl.tue.vmcourse.toy.lang.FrameDescriptor;
import nl.tue.vmcourse.toy.lang.NullValue;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.jit.JITCompiler;

public class ToyBciLoop extends ToyAbstractFunctionBody {

    private static final int JIT_COMPILATION_THRESHOLD = 3;

    private final Program program;
    private final JITCompiler compiler;
    private final Map<String, RootCallTarget> allFunctions;
    private final RootCallTarget[] functionCache;
    private short slotCache[];
    private Deque<ToyRootNode> callStack = new ArrayDeque<>();

    public ToyBciLoop(Program program, Map<String, RootCallTarget> allFunctions) {
        this.program = program;
        this.compiler = new JITCompiler();
        this.allFunctions = allFunctions;
        this.slotCache = new short[program.code.length];
        this.functionCache = new RootCallTarget[program.code.length];
        this.callStack = new ArrayDeque<>();
        
    }

    public Object execute(VirtualFrame frame) {
        int pc = 0;
        int executions = 0;
        Object objRegister = null;
        int intRegister1 = 41;
        int intRegister2 = 1;

        final Deque<Object> programStack = new ArrayDeque<>();

        CallFrame cf = RootCallTarget.peekTopFrame();
        FrameDescriptor fd = cf.frameDescriptor;
        Object[] locals = new Object[fd.getFrameSlots().size()];
        cf.attachLocals(locals);

        if(ToyLauncher.DUMP_BYTECODE) {
            System.out.println("BYTECODE: ");
            for(byte b : program.code) {
                System.out.printf("%02X ", (b & 0xFF));
            } 
            System.out.print("\n");
        }
        try {
        while (pc < program.code.length) {
            executions++;
            int op = (program.code[pc++]) & 0xFF;
            switch (Opcode.fromByte(op)) {
                case ICONST -> {
                    int index = readInt(program.code, pc);
                    programStack.push(NullValue.boxValue(program.constants[index]));
                    pc+=4;
                    break;
                }
                case SCONST -> {
                    int index = readInt(program.code, pc);
                    programStack.push(program.constants[index]);
                    pc+=4;
                    break;
                }
                case BCONST -> {
                    int index = readInt(program.code, pc);
                    programStack.push(program.constants[index]);
                    pc+=4;
                    break;
                }
                case FCONST -> {
                    int index = readInt(program.code, pc);
                    String functionName = (String)program.constants[index];
                    try {
                        RootCallTarget function = allFunctions.get(functionName);
                        programStack.push(function);
                    } catch(NullPointerException e) {
                        programStack.push(NullValue.INSTANCE);
                    }
                    pc+=4;
                    break;
                }
                case CALL -> {
                    int argCount = readInt(program.code, pc);
                    Object[] args = new Object[argCount];
                    for(int i = argCount - 1; i >= 0; i--) {
                        args[i] = programStack.pop();
                    }
                    try {
                        RootCallTarget function = (RootCallTarget)programStack.pop();
                        Object returned = function.invoke(args);
                        programStack.push(NullValue.boxValue(returned));

                        String functionName = function.getRootNode().getFunctionName();

                        functionCache[pc - 1] = function;
                        if(functionName.equals("new")) {
                            program.code[pc - 1] = Opcode.CALL_NEW.code;
                        } else if (functionName.equals("print") && argCount > 0) {
                            program.code[pc - 1] = Opcode.CALL_PRINT.code;
                        }
                    } catch(NullPointerException exception) {
                        throw new RuntimeException("Nu such function: ");
                    }

                    pc+=4;
                    break;
                }
                case CALL_NEW -> {
                    Object returned = functionCache[pc - 1].invoke(programStack.pop());
                    programStack.push(returned);
                    pc += 4;
                    break;
                }
                case CALL_PRINT -> {
                    functionCache[pc - 1].invoke(programStack.pop());
                    pc += 4;
                    break;
                }
                case ADD -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();

                    if(left instanceof Long && right instanceof Long) {
                        long result = (Long)left + (Long)right;
                        program.code[pc - 1] = Opcode.ADD_I.code;
                        programStack.push(result);
                    } else if(left instanceof String && right instanceof String) {
                        String result = (String)left + (String) right;
                        programStack.push(result);
                    } else {
                        programStack.push(addGeneric(left, right));
                    }
                    break;
                }
                case ADD_I -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();

                    if(left instanceof Long && right instanceof Long) {
                        programStack.push((Long)left + (Long)right);
                    } else {
                        programStack.push(left);
                        programStack.push(right);
                        program.code[pc - 1] = Opcode.ADD.code;
                        pc--;
                    }

                    break;
                }
                case MUL -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();
                    
                    if(left instanceof Long && right instanceof Long) {
                        try {
                            Long res = Math.multiplyExact((Long)left, (Long)right);
                            programStack.push(res);
                        } catch(ArithmeticException e) {
                            BigInteger res = BigInteger.valueOf((Long)left).multiply(BigInteger.valueOf((Long)right));
                            programStack.push(res);
                        }
                    } else {
                        throw new RuntimeException("TODO");
                    }
                    
                    break;
                }
                case SUB -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();
                    
                    if(left instanceof Long && right instanceof Long) {
                        programStack.push((Long)left - (Long)right); 
                    } else {
                        throw new RuntimeException();
                    }

                    break;
                }
                case DIV -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();
                    
                    boolean checkLeft = (left instanceof Long);
                    boolean checkRight = (right instanceof Long);
                    if(!checkLeft || !checkRight) {
                        throw new RuntimeException();
                    }

                    Long result = (Long)left / (Long)right;
                    programStack.push(result);
                    break;
                }
                case JMP -> {
                    int jumpCounter = readInt(program.code, pc);
                    pc += jumpCounter + 4;
                    break;
                }
                case JZ -> {
                    Object condition = programStack.pop();
                    boolean checkCondition = condition instanceof Boolean;
                    if(!checkCondition) {
                        throw new RuntimeException();
                    }
                    int jumpCounter = readInt(program.code, pc);
                    if(!((boolean)condition)) {
                        pc += jumpCounter + 4;
                    } else {
                        pc += 4;
                    }
                    break;
                }
                case JNZ -> {
                    Object condition = programStack.pop();
                    boolean checkCondition = condition instanceof Boolean;
                    if(!checkCondition) {
                        throw new RuntimeException();
                    }
                    int jumpCounter = readInt(program.code, pc);
                    if(((boolean)condition)) {
                        pc += jumpCounter + 4;
                    } else {
                        pc += 4;
                    }
                    break;
                }
                case EQ -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();
                    
                    if(left.getClass() != right.getClass()) {
                        programStack.push(false);
                        break;
                    }

                    programStack.push((left.toString()).equals(right.toString()));
                    break;
                }
                case LOAD_ARG -> {
                    int argIndex = readInt(program.code, pc);
                    Object arg = frame.getArguments()[argIndex];
                    programStack.push(arg);

                    pc += 4;
                    break;
                }
                case STORE -> {
                    int slot = readInt(program.code, pc);

                    locals[slot] = programStack.pop();
                    slotCache[pc - 1] = (short)slot;
                    program.code[pc - 1] = Opcode.STORE_QUICK.code;

                    pc+=4;
                    break;
                }
                case STORE_QUICK -> {
                    locals[slotCache[pc - 1]] = programStack.pop();

                    pc += 4;
                    break;
                }
                case LOAD -> {
                    int slot = readInt(program.code, pc);
                    programStack.push(locals[slot]);
                    slotCache[pc - 1] = (short)slot;
                    program.code[pc - 1] = Opcode.LOAD_QUICK.code;

                    pc+=4;
                    break;
                }
                case LOAD_QUICK -> {
                    programStack.push(locals[slotCache[pc - 1]]);

                    pc+=4;
                    break;
                }
                case NEG -> {
                    Object operand = programStack.pop();
                    if(!(operand instanceof Boolean)) {
                        throw new RuntimeException();
                    }
                    programStack.push(!(boolean)operand);
                    break;
                }
                case UMIN -> {
                    Object operand = programStack.pop();
                    if(operand instanceof Long) {
                        programStack.push(-(Long)operand);
                        break;
                    }
                    
                    throw new RuntimeException("Unary min only works on long values");
                }
                case WRITE -> {
                    // int objIndex = readInt(program.code, pc);
                    // pc += 4;
                    // ToyObject obj = (ToyObject)locals[objIndex];
                    Object value = programStack.pop();
                    try {
                        String key = programStack.pop().toString();
                        ToyObject obj = (ToyObject)programStack.pop();
                        obj.setProperty(key, value);
                        programStack.push(obj);
                    } catch(NullPointerException e) {
                        throw new RuntimeException("TODO");
                    }

                    break;
                }
                case READ -> {
                    // int objIndex = readInt(program.code, pc);
                    // pc += 4;
                    // ToyObject obj = (ToyObject)locals[objIndex];
                    Object property = programStack.pop().toString();
                    try {
                        ToyObject obj = (ToyObject)programStack.pop();
                        Object value = obj.getValue(property.toString());
                        programStack.push(value);
                    } catch(NullPointerException e) {
                        throw new RuntimeException("Undefined property: " + property);
                    }

                    break;
                }
                case RET -> {
                    return programStack.pop();
                }
                case LT -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();
                    if(left instanceof Long && right instanceof Long) {
                        programStack.push((long)left < (long)right);
                    } else if((left instanceof BigInteger || left instanceof Long) && (right instanceof BigInteger || right instanceof Long)) {
                        BigInteger a = (left instanceof BigInteger) ? (BigInteger)left : BigInteger.valueOf((Long)left);
                        BigInteger b = (right instanceof BigInteger) ? (BigInteger)right : BigInteger.valueOf((Long)right);

                        programStack.push((a.compareTo(b) < 0) ? true : false);
                    } else {
                        throw new RuntimeException("Value types not compatible for comparison");
                    }
                    
                    break;
                }
                case LE -> {
                    Object right = programStack.pop();
                    Object left = programStack.pop();

                    if(left instanceof Long && right instanceof Long) {
                        programStack.push((long)left <= (long)right);
                    } else if((left instanceof BigInteger || left instanceof Long) && (right instanceof BigInteger || right instanceof Long)) {
                        BigInteger a = (left instanceof BigInteger) ? (BigInteger)left : BigInteger.valueOf((Long)left);
                        BigInteger b = (right instanceof BigInteger) ? (BigInteger)right : BigInteger.valueOf((Long)right);

                        programStack.push((a.compareTo(b) <= 0) ? true : false);
                    } else {
                        throw new RuntimeException("Value types not compatible for comparison");
                    }

                    break;
                }
                // case 42 -> {
                //     if (executions <= JIT_COMPILATION_THRESHOLD) {
                //         continue;
                //     }
                //     objRegister = compiler.compileAndRun(intRegister1, intRegister2);
                //     return "Hello from your friendly BCI! (and your JIT: " + objRegister + ")";
                // }
                // case 43 -> pc++;f
                // case ..
                default -> throw new RuntimeException("PC: " + pc);
            }
        }} catch(Exception e) {
            System.err.println("FUNCTION: " + cf.functionName);
            System.err.println("PC: " + pc + ", EX: " + executions);
            System.err.println(e);
        }
        return null;
    }

    private int readInt(byte code[], int pc) {
        return (code[pc] & 0xFF) | ((code[pc + 1] & 0xFF) << 8) | ((code[pc + 2] & 0xFF) << 16) | ((code[pc + 3] & 0xFF) << 24);
    }
    
    private Object addGeneric(Object left, Object right) {
        if(left instanceof String || right instanceof String) {
            return String.valueOf(left) + String.valueOf(right);
        } 

        throw new RuntimeException("TODO");
    }
}
