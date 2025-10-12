package nl.tue.vmcourse.toy.builtins;

import java.util.Map;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

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
        return this.invoke(args[0]);
    }
    
}
