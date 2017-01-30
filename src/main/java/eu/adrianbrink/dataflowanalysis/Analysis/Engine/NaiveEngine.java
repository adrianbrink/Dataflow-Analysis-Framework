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
    private List<CFGState> states = new ArrayList<>();
    private final CFG cfg;

    public NaiveEngine(CFG cfg) {
        this.cfg = cfg;
    }

    @Override
    public void run(IAnalysisFramework framework, ILattice lattice) {
        init(framework);

        ILattice previousLattice = lattice.deepCopy();
        for (CFGNode node : cfg.getCfgNodes()) {
            CFGState state = node.getState();
            if (state != null && state.getIn() != null) {
                // TODO: I don't have access to the CFGStates of my precs nodes. Maybe my CFGNodes should carry the information to which state they belong.
                Function<ILattice, ILattice> transferFunction = framework.transferFunction(node);
                //state.getNode().getPrevious()
                //transferFunction.apply()
            }
        }
    }

    private void init(IAnalysisFramework framework) {
        for (CFGNode node : cfg.getCfgNodes()) {
            ILattice bottom = framework.initialLattice();
            if (node.isEntryPoint())
                node.setState(new CFGState(null, bottom));
            else if (node.isExitPoint())
                node.setState(new CFGState(bottom, null));
            else
                node.setState(new CFGState(bottom, bottom));
            states.add(node.getState());
        }
    }
}
