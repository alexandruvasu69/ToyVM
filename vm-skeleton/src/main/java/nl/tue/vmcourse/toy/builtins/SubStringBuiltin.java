package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class SubStringBuiltin extends ToyAbstractFunctionBody {

    public String invoke(Object arg, Object s, Object e) {
        String str  = (String)arg;
        int start = ((Long)s).intValue();
        int end = ((Long)e).intValue();

        return str.substring(start, end);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        return this.invoke(args[0], args[1], args[2]);
    }
    
}
