package nl.tue.vmcourse.toy.builtins;

import java.lang.reflect.InvocationHandler;


import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class HasPropertyBuiltin extends ToyAbstractFunctionBody {

    public Object invoke(Object o, Object p) {
        ToyObject obj = (ToyObject)o;
        if(!(p instanceof String)) {
            return false;
        }
        String prop = p.toString();
        Object val = obj.getValue(prop);
        if(val!=null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        if(!(args[0] instanceof ToyObject)) {
            throw new RuntimeException("Not an object!");
        }

        return this.invoke(args[0], args[1]);
    }
    
}
