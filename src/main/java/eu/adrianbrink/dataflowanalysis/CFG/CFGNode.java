package eu.adrianbrink.dataflowanalysis.CFG;

import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
public abstract class CFGNode {
    public CFGNode() { }

    public abstract void setNext(List<CFGNode> nodes);

    public abstract List<CFGNode> getNext();
}
