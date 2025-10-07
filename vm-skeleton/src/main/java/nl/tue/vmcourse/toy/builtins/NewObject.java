package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.ToyObject;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class NewObject extends ToyAbstractFunctionBody {
    public ToyObject invoke() {
        return new ToyObject();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.invoke();
    }
    
}
