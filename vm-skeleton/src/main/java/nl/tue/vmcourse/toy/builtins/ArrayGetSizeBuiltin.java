package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class ArrayGetSizeBuiltin extends ToyAbstractFunctionBody{

    public Object invoke(ToyObject obj) {
        return obj.getSize();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.invoke((ToyObject)frame.getArguments()[0]);
    }
    
}
