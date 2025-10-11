package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class IsInstanceBuiltin extends ToyAbstractFunctionBody {

    public Object invoke(Object left, Object right) {
        Object rightType = new TypeOfBuiltin().invoke(right);
        if(left.equals(rightType)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        return this.invoke(args[0], args[1]);
    }
    
}
