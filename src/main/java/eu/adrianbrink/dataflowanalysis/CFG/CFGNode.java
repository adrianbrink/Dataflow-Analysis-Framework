package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.Statement;

/**
 * Created by sly on 26/01/2017.
 */
public class CFGNode {
    private Statement statement;
    private CFGNode next;

    public CFGNode(Statement statement, CFGNode next) {
        this.statement = statement;
        this.next = next;
    }

    public void setNext(CFGNode next) {
        this.next = next;
    }

    public CFGNode getNext() {
        return this.next;
    }
}
