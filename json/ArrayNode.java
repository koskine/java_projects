/*
    Author: Iiro Koskinen H299947
*/

import java.util.ArrayList;
import java.util.Iterator;


public class ArrayNode extends Node implements Iterable<Node> {
    private ArrayList<Node> list;


    public ArrayNode() {
        list = new ArrayList<Node>();
    }

    public void add(Node node) {
        list.add(node);
    }

    public int size() {
        return list.size();
    }
    
    public Iterator<Node> iterator() {
        return list.iterator();
    }
}
