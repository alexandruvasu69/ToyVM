package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.lang.exceptions.ToyException;

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

        if(args.length < 0) {
            throw new RuntimeException();
        }

        if(!(args[0] instanceof String)) {
            throw new RuntimeException("Not a string: cannot substring");
        }

        try {
            return this.invoke(args[0], args[1], args[2]);
        } catch(IndexOutOfBoundsException e) {
            throw new ToyException();
        }
    }
    
}
