package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class NaiveEngine implements IAnalysisEngine {
    private CFG cfg;
    private boolean isFixedPoint = false;

    public NaiveEngine(CFG cfg) {
        this.cfg = cfg;
    }

    @Override
    public void run() {
        do {
            this.runTransferFunctions();
            this.updateLattices();
        } while (!isFixedPoint);
        System.out.println("xxx");
    }


    private void runTransferFunctions() {
        for (CFGNode node : this.cfg.getCFGNodes()) {
            Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
            ILattice in = node.getCfgState().getIn();
            ILattice out = transferFunction.apply(in);
            node.getCfgState().setOut(out);
        }
    }

    private void updateLattices() {
        boolean isFixed = true;
        for (CFGNode node : this.cfg.getCFGNodes()) {
            if (!node.isEntryPoint()) {
                List<ILattice> outLattices = new ArrayList<>();
                for (CFGNode cfgNode : node.getPrevious()) {
                    outLattices.add(cfgNode.getCfgState().getOut());
                }
                int index = outLattices.size() - 1;
                ILattice newOut = (ILattice) outLattices.get(0).join(outLattices.get(index));
                isFixed &= newOut.isEquals(node.getCfgState().getIn());
                node.getCfgState().setIn(newOut);
            }
        }
        isFixedPoint = isFixed;
    }
}
