package nl.tue.vmcourse.toy.lang;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import nl.tue.vmcourse.toy.interpreter.ToyRootNode;

public class RootCallTarget {
    private final ToyRootNode rootNode;
    private static Deque<CallFrame> CALL_STACK = new ArrayDeque<>();

    public RootCallTarget(ToyRootNode rootNode) {
        this.rootNode = rootNode;
    }

    public Object invoke(Object... arguments) {
        VirtualFrame frame = new VirtualFrame(arguments);
        String functionName = (this.rootNode != null) ? rootNode.getFunctionName() : "builtin";
        CALL_STACK.push(new CallFrame(functionName, this.rootNode.getFrameDescriptor()));

        try {
            return rootNode.execute(frame);
        } finally {
            CALL_STACK.pop();
        }
    }

    public ToyRootNode getRootNode() {
        return rootNode;
    }

    public static List<CallFrame> getFrameSnapshot() {
        return new ArrayList<>(CALL_STACK);
    }

    public static CallFrame peekTopFrame() {
        return CALL_STACK.peek();
    }

    public static String frameSnapshotToString() {
        StringBuilder sb = new StringBuilder();
        int stackSize = getFrameSnapshot().size();
        for(int i = 0; i < stackSize; i++) {
            CallFrame cf = getFrameSnapshot().get(i);
            String cfString = cf.toString();
            if(cfString != null) {
                sb.append(cfString);
                if(i != stackSize - 1) {
                    sb.append("\n");
                }
            }
            
        }

        return sb.toString();
    }
}
