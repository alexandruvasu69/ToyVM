package nl.tue.vmcourse.toy.builtins;

import java.util.List;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.CallFrame;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class StackTraceBuiltin extends ToyAbstractFunctionBody {
    public Object invoke() {
        return RootCallTarget.frameSnapshotToString();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.invoke();
    }
    
}
