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
    private final ILattice lattice;
    private final IAnalysisFramework framework;
    private final CFG cfg;

    public NaiveEngine(CFG cfg, IAnalysisFramework framework, ILattice lattice) {
        this.lattice = lattice;
        this.framework = framework;
        this.cfg = cfg;

        for (CFGNode node : cfg.getCfgNodes()) {
            ILattice bottom = framework.initialLattice();
            if (node.isEntryPoint())
                node.setState(new CFGState(null, bottom));
             else if (node.isExitPoint())
                node.setState(new CFGState(bottom, null));
             else
                node.setState(new CFGState(bottom, bottom));
            // I do not think we need that but i did not want to change everything :)
            states.add(node.getState());
        }
    }



    @Override
    public void run() {
        //ILattice previousLattice = this.lattice.deepCopy();
        for (CFGNode node : cfg.getCfgNodes()) {
            CFGState state = node.getState();
            if (state != null && state.getIn() != null) {
                // TODO: I don't have access to the CFGStates of my precs nodes. Maybe my CFGNodes should carry the information to which state they belong.
                Function<ILattice, ILattice> transferFunction = this.framework.transferFunction(node);
                //state.getNode().getPrevious()
                //transferFunction.apply()
            }
        }
    }
}
