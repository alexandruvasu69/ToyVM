package nl.tue.vmcourse.toy.lang;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class CallFrame {
    public final String functionName;
    public final FrameDescriptor frameDescriptor;
    public Object[] locals;

    public CallFrame(String functionName, FrameDescriptor frameDescriptor) {
        this.functionName = functionName;
        this.frameDescriptor = frameDescriptor;
    }

    public String toString() {
        if (this.locals != null) {
            StringBuilder sb = new StringBuilder();
            for(Entry<String, Integer> entry : frameDescriptor.getFrameSlots().entrySet()) {
                String key = entry.getKey();
                int valueKey = entry.getValue();
                Object value = (valueKey >= 0 && valueKey < locals.length) ? locals[valueKey] : null;
                sb.append(", " + key + "=" + value);
            }
            return "Frame: root " + this.functionName + sb.toString();
        }
        return null;
    }

    public void attachLocals(Object[] locals) {
        this.locals = locals;
    }
}
