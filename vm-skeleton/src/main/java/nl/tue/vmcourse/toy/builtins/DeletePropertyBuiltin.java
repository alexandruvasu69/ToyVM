package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class DeletePropertyBuiltin extends ToyAbstractFunctionBody {

    public Object invoke(Object o, Object p) {
        ToyObject obj = (ToyObject)o;
        String property = (String)p;

        obj.deleteProperty(property);
        return null;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] arg = frame.getArguments();
        return this.invoke(arg[0], arg[1]);
    }
    
}
