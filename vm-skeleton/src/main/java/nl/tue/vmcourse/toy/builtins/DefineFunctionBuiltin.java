package nl.tue.vmcourse.toy.builtins;

import java.util.Map;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.NullValue;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.lang.exceptions.ToyDefineFunctionException;
import nl.tue.vmcourse.toy.lang.exceptions.ToyTypeException;

public class DefineFunctionBuiltin extends ToyAbstractFunctionBody {
    private final Map<String, RootCallTarget> allFunctions;

    public DefineFunctionBuiltin(Map<String, RootCallTarget> allFunctions) {
        this.allFunctions = allFunctions;
    }

    public Object invoke(Object code) {
        return new EvalBuiltin(allFunctions).invoke("sl", code);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();

        if(args.length == 0) {
            throw new ToyDefineFunctionException(NullValue.INSTANCE);
        }

        if(!(args[0] instanceof String)) {
            throw new ToyDefineFunctionException(args[0]);
        }

        return this.invoke(args[0]);
    }
    
}
