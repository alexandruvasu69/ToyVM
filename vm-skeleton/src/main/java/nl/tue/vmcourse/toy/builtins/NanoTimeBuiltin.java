package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class NanoTimeBuiltin extends ToyAbstractFunctionBody {
    public long invoke() {
        return System.nanoTime();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.invoke();
    }
    
}
