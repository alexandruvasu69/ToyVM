package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class GetSizeBuiltin extends ToyAbstractFunctionBody {

    public int invoke(Object obj) {
        if(!(new HasSizeBuiltin().invoke(obj))) {
            throw new RuntimeException("Object doesn't have size");
        }

        if(obj instanceof ToyObject) {
            return ((ToyObject)obj).getSize();
        } else {
            return ((String)obj).length();
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        return this.invoke(args[0]);
    }
    
}
