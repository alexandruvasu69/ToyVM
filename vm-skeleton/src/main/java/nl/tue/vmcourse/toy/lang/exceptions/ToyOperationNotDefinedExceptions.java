package nl.tue.vmcourse.toy.lang.exceptions;

import nl.tue.vmcourse.toy.builtins.TypeOfBuiltin;
import nl.tue.vmcourse.toy.lang.ToyType;

public class ToyOperationNotDefinedExceptions {

    private ToyOperationNotDefinedExceptions() {}

    public static ToyTypeException throwException(String operation, Object a, Object b) {
        String msg = "operation \"" + operation +
            "\"" + " not defined for " +
            prettifyObject(a) +
            ", " + prettifyObject(b);
        return new ToyTypeException(msg);
    }

    public static ToyTypeException throwException(String operation, Object a) {
        String msg = "operation \"" + operation +
            "\"" + " not defined for " +
            prettifyObject(a);
        return new ToyTypeException(msg);
    }

    private static ToyType getType(Object a) {
        return new TypeOfBuiltin().invoke(a);
    }

    private static String prettifyObject(Object a) {
        String obj;
        if(a instanceof String) {
            obj = "\"" + a + "\"";
        } else if(a instanceof AnyValue) {
            return a.toString(); 
        } else {
            obj = a.toString();
        }

        return getType(a) + " " + obj;
    }

    public enum AnyValue {
        INSTANCE;

        @Override
        public String toString() {
            return "ANY";
        }
    }
}
