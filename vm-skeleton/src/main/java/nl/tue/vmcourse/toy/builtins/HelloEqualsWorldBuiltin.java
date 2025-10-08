package nl.tue.vmcourse.toy.builtins;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.CallFrame;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class HelloEqualsWorldBuiltin extends ToyAbstractFunctionBody {
    private void invoke() {
        List<CallFrame> stack = RootCallTarget.getFrameSnapshot();
        CallFrame cf = stack.get(stack.size() - 2);
        Object[] slotsWithHello = cf.frameDescriptor.getFrameSlots().entrySet().stream().filter(e -> 
            (e.getKey().toLowerCase().equals("hello"))
        ).map(e -> e.getValue()).toArray();

        for(int i = 0; i < slotsWithHello.length; i++) {
            cf.locals[(int)slotsWithHello[i]] = "world";
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.invoke();
        return null;
    }
    
}
