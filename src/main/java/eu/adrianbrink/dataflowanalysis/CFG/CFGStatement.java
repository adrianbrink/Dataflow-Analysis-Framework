package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
public class CFGStatement extends CFGNode {
    private CFGNode next;
    private Statement statement;

    public CFGStatement(Statement statement) {
        super();
        this.statement = statement;
    }

    public Statement getStatement() {
        return this.statement;
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
