package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;

import java.util.function.Function;

/**
 * Created by sly on 28/01/2017.
 */
// should take the CFG and give it to the analysis so that it can create the lattice
    // then the analysis runs, which is triggered through run on this class.
public class NaiveAnalysis extends Analysis {
    public NaiveAnalysis(CFG cfg, IAnalysis analysis) {
        super(cfg, analysis);
    }

    public void run() {
        Lattice lattice = this.getAnalysis().generateLattice(this.getCfg());
        this.setLattice(lattice);
        // This would be so much cleaner if I could write a for-each loop that gives me the item and its index in a tuple.
        // TODO: This loop needs to run until the lattice does not change and hence I have reached a least-fixed point. So I need an outer loop that copies the lattice and then compares it to the updated lattice. If they are different run again, if nothing changed I can print the lattice.
        // the problem with cycling through nodes from here is that IAnalysis will know whether it is a forward or backward analysis and hence IAnalysis should cycle through nodes
        for (int i = 0; i < this.getCfg().getCfgNodes().size(); i++) {
            CFGNode node = this.getCfg().getCfgNodes().get(i);
            // this is the case for the entry and exit nodes
            if (node.getStatementOrExpression() == null) {
                continue;
            } else {
                AST statementOrExpression = node.getStatementOrExpression();
                Function<LatticeElement, LatticeElement> transferFunction = this.getAnalysis().getTransferFunction(statementOrExpression);
                LatticeElement currentLatticeElement = this.getLattice().getLatticeElement(i);
                LatticeElement newLatticeElement = transferFunction.apply(currentLatticeElement);
                this.getLattice().updateLatticeElement(newLatticeElement, i);
            }
        }
    }
}
