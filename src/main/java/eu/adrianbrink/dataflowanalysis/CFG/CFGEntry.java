package eu.adrianbrink.dataflowanalysis.CFG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sly on 27/01/2017.
 */
public class CFGEntry extends CFGNode {
    private CFGNode next;

    public CFGEntry() {
        super();
    }

    public void setNext(List<CFGNode> next) {
        this.next = next.get(0);
    }

    public List<CFGNode> getNext() {
        ArrayList<CFGNode> list = new ArrayList<>();
        list.add(next);
        return list;
    }
}
