package eu.adrianbrink.dataflowanalysis.Analysis.Engine;

import eu.adrianbrink.dataflowanalysis.Analysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.CFG.CFGState;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by sly on 30/01/2017.
 */
public class NaiveEngine implements IAnalysisEngine {
    private List<CFGState> states;
    private ILattice lattice;
    private IAnalysisFramework framework;

    public NaiveEngine(CFG cfg, IAnalysisFramework framework, ILattice lattice) {
        this.lattice = lattice;
        this.framework = framework;

        for (CFGNode node : cfg.getCfgNodes()) {
            CFGState state;
            ILattice initialInLattice = framework.initialLattice();
            ILattice initialOutLattice = framework.initialLattice();
            if ((node.getStatementOrExpression() == null) && (node.getPrevious() == null)) {
                state = new CFGState(node, null, initialOutLattice);
            } else if ((node.getStatementOrExpression() == null) && (node.getNext() == null)) {
                state = new CFGState(node, initialInLattice, null);
            } else {
                state = new CFGState(node, initialInLattice, initialOutLattice);
            }
            states.add(state);
        }
    }

    @Override
    public void run() {
        ILattice previousLattice = this.lattice.deepCopy();
        for (CFGState state : this.states) {
            if (state.getIn() == null) {
                continue;
            } else {
                // TODO: I don't have access to the CFGStates of my precs nodes. Maybe my CFGNodes should carry the information to which state they belong.
                state.getNode().getPrevious()
                Function<ILattice, ILattice> transferFunction = this.framework.transferFunction(state.getNode());
                transferFunction.apply()
            }
        }
    }
}
