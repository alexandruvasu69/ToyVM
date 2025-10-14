package nl.tue.vmcourse.toy.lang.exceptions;

import nl.tue.vmcourse.toy.builtins.TypeOfBuiltin;
import nl.tue.vmcourse.toy.lang.NullValue;

public class ToyDefineFunctionException extends ToyTypeException {

    public ToyDefineFunctionException(Object obj) {
        super("operation \"defineFunction\" not defined for " + (!(obj instanceof NullValue) ? new TypeOfBuiltin().invoke(obj) + " " : "") + obj);
    }
    
}
