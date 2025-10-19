package nl.tue.vmcourse.toy.builtins;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.NullValue;
import nl.tue.vmcourse.toy.lang.UndefinedValue;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.strings.Rope;

public class PrintBuiltin extends ToyAbstractFunctionBody {
    public Object invoke(String foo) {
        System.out.println(foo);
        return null;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] arguments = frame.getArguments();

        if(arguments[0] instanceof UndefinedValue) {
            throw new RuntimeException("Runtime error on \"println\": Unknown object: " + "\"" + arguments[0] + "\"");
        }

        if(arguments.length > 0 && NullValue.getUnboxed(arguments[0]) != null) {
            this.invoke(arguments[0].toString());
            return null;
        } else {
            this.invoke("NULL");
            return null;
        }
    }
}
