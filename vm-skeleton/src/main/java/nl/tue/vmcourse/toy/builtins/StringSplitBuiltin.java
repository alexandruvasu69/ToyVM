package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class StringSplitBuiltin extends ToyAbstractFunctionBody {
    public ToyObject invoke(Object arg) {
        String str = (String)arg;
        ToyObject obj = new ToyObject();
        String[] arr = str.split(" ");
        for(int i = 0; i < arr.length; i++) {
            obj.setProperty(String.valueOf(i), arr[i]);
        }

        return obj;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        if(!(args[0] instanceof String)) {
            throw new RuntimeException("Not a string: cannot substring");
        }

        return this.invoke(args[0]);
    }
    
}
