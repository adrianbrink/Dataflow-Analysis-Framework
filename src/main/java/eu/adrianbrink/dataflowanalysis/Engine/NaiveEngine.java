package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

/**
 * Created by sly on 30/01/2017.
 */
public class NaiveEngine implements IAnalysisEngine {
    private CFG cfg;
    private ILattice lattice;

    public NaiveEngine(CFG cfg, ILattice lattice) {
        this.cfg = cfg;
        this.lattice = lattice;
    }

    @Override
    public void run() {
//        ILattice previousLattice = this.lattice.deepCopy();
//        for (CFGState state : this.states) {
//            if (state.getIn() == null) {
//                continue;
//            } else {
//                // TODO: I don't have access to the CFGStates of my precs nodes. Maybe my CFGNodes should carry the information to which state they belong.
//                state.getNode().getPrevious()
//                Function<ILattice, ILattice> transferFunction = this.framework.transferFunction(state.getNode());
//                transferFunction.apply()
//            }
//        }
    }
}
