package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Adrian Brink on 26/01/2017.
 */
public class CFGNode {
    // TODO: Find a more general name for this
    private AST statementOrExpression = null;
    private List<CFGNode> previous = new ArrayList<>();
    private List<CFGNode> next = new ArrayList<>();
    private Function<LatticeElement, LatticeElement> transferFunction = null;

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

    public List<CFGNode> getPrevious() {
        return this.previous;
    }

    public void setTransferFunction(Function<LatticeElement, LatticeElement> transferFunction) {
        this.transferFunction = transferFunction;
    }

    public Function<LatticeElement, LatticeElement> getTransferFunction() {
        return this.transferFunction;
    }
}
