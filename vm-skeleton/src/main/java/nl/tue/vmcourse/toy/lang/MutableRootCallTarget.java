package nl.tue.vmcourse.toy.lang;

import static nl.tue.vmcourse.toy.lang.RootCallTarget.CALL_STACK;

public final class MutableRootCallTarget extends RootCallTarget {
    private RootCallTarget rootCallTarget;

    public MutableRootCallTarget(RootCallTarget rootCallTarget) {
        super(rootCallTarget.getRootNode());
        this.rootCallTarget = rootCallTarget;
    }

    public RootCallTarget getRootCallTarget() {
        return this.rootCallTarget;
    }

    public void setRootCallTarget(RootCallTarget rootCallTarget) {
        this.rootCallTarget = rootCallTarget;
    }

    @Override
    public Object invoke(Object... arguments) {
        VirtualFrame frame = new VirtualFrame(arguments);
        String functionName = (rootCallTarget.getRootNode() != null) ? rootCallTarget.getRootNode().getFunctionName() : "builtin";
        CALL_STACK.push(new CallFrame(functionName, rootCallTarget.getRootNode().getFrameDescriptor()));

        try {
            return rootCallTarget.getRootNode().execute(frame);
        } finally {
            CALL_STACK.pop();
        }
    }
    
}
