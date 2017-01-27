package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
// This is super inconveniet, but I need to represent if and while nodes.
public class CFGBranch extends CFGNode {
    private Expression expression;
    private CFGNode firstBranch = null;
    private CFGNode secondBranch = null;

    public CFGBranch(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public void setNext(List<CFGNode> nodes) {
        if (this.firstBranch == null) {
            this.firstBranch = nodes.get(0);
        } else {
            this.secondBranch = nodes.get(0);
        }
    }

    public List<CFGNode> getNext() {
        ArrayList<CFGNode> list = new ArrayList<>();
        list.add(this.firstBranch);
        list.add(this.secondBranch);
        return list;
    }
}
