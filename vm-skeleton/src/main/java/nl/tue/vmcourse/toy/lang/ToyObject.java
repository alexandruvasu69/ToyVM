package nl.tue.vmcourse.toy.lang;

import java.util.HashMap;
import java.util.Map;

public class ToyObject {
    private final Map<String, Object> storage = new HashMap<>();

    public Object getValue(String property) {
        return storage.get(property);
    }

    public void setProperty(String property, Object value) {
        storage.put(property, value);
    }

    public int getSize() {
        return this.storage.size();
    }

    public void deleteProperty(String property) {
        storage.remove(property);
    }
}
