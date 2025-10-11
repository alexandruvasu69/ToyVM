package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class HasSizeBuiltin extends ToyAbstractFunctionBody {
    public boolean invoke(Object obj) {
        if(obj instanceof ToyObject || obj instanceof String) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        return this.invoke(args[0]);
    }
    
}
