package nl.tue.vmcourse.toy.builtins;

import java.math.BigInteger;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.NullValue;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.ToyType;
import nl.tue.vmcourse.toy.lang.ToyTypeWrapper;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class TypeOfBuiltin extends ToyAbstractFunctionBody {
    public Object invoke(Object object) {
        if(object instanceof Long || object instanceof BigInteger) {
            return ToyType.NUMBER;
        } else if(object instanceof String) {
            return ToyType.STRING;
        } else if(object instanceof ToyObject) {
            return ToyType.OBJECT;
        } else if(object instanceof Boolean) {
            return ToyType.BOOLEAN;
        } else if(object instanceof NullValue) {
            return ToyType.NULL;
        } else if(object instanceof RootCallTarget) {
            return ToyType.FUNCTION;
        } else if(object instanceof ToyType) {
            return new ToyTypeWrapper((ToyType)object);
        }

        return "unknown";
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.invoke(frame.getArguments()[0]);
    }
    
}
