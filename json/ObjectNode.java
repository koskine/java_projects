import java.util.Iterator;
import java.util.TreeMap;


/*
    Author: Iiro Koskinen H299947
*/


public class ObjectNode extends Node implements Iterable<String> {
    private TreeMap<String, Node> map;

    public ObjectNode() {
        map = new TreeMap<String, Node>();
    }

    public Node get(String key) {
        return map.get(key);
    }

    public void set(String key, Node node) {
        map.put(key, node);
    }

    public int size() {
        return map.size();
    }

    public Iterator<String> iterator() {
        return map.keySet().iterator();
    }
}
