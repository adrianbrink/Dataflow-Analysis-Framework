package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.AST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
// TODO: Only concrete datatype that has 2 lists of pres and sucs, a statement and
public class CFGNode {
    private List<CFGNode> previous = new ArrayList<>();
    private List<CFGNode> next = new ArrayList<>();
    // TODO: Find a more general name for this
    private AST statementOrExpression;

    public CFGNode(AST statementOrExpression) {
        this.statementOrExpression = statementOrExpression;
    }

    public void setPrevious(List<CFGNode> previous) {
        this.previous.addAll(previous);
    }

    public void setNext(List<CFGNode> next) {
        this.next.addAll(next);
    }

    public AST getStatementOrExpression() {
        return this.statementOrExpression;
    }

    public List<CFGNode> getNext() {
        return this.next;
    }

    public List<CFGNode> getPrevious() {return this.previous;}

    public CFGState getState() {return state;}

    public void setState(CFGState cfgState) {this.state = cfgState;}

    public boolean isEntryPoint() {
        return statementOrExpression == null && previous == null;}

    public boolean isExitPoint() {
        return next == null && statementOrExpression == null;
    }




}
