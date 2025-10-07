package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class PrintBuiltin extends ToyAbstractFunctionBody {
    public Object invoke(String foo) {
        System.out.println(foo);
        return null;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] arguments = frame.getArguments();
        if(arguments != null && arguments.length > 0) {
            this.invoke(arguments[0].toString());
            return null;
        } else {
            this.invoke(null);
            return null;
        }
    }
}
