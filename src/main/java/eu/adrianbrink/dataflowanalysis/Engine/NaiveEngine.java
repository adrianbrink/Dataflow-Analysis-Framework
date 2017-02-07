package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class NaiveEngine implements IAnalysisEngine {

    private CFG cfg;
    private boolean isFixedPoint, isBackwards;
    private IAnalysisFramework framework;
    public NaiveEngine(CFG cfg, IAnalysisFramework framework) {
        this.cfg = cfg;
        this.isBackwards = framework.isBackward();
        this.framework = framework;
    }

    @Override
    public void run() {
        if (!isBackwards) {
            do {
                this.runTransferFunctionsForward();
                this.updateLatticesForward();
            } while (!isFixedPoint);
        } else {
            do {
                this.runTransferFunctionsBackward();
                this.updateLatticesBackward();
            } while (!isFixedPoint);
        }
    }

    private void runTransferFunctionsBackward() {
        for (CFGNode node : this.cfg.getCFGNodes()) {
            Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
            ILattice out = node.getCfgState().getOut();
            ILattice in = transferFunction.apply(out);
            node.getCfgState().setIn(in);
        }
    }

    private void runTransferFunctionsForward() {
        for (CFGNode node : this.cfg.getCFGNodes()) {
            Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
            ILattice in = node.getCfgState().getIn();
            ILattice out = transferFunction.apply(in);
            node.getCfgState().setOut(out);
        }
    }

    private void updateLatticesForward() {
        boolean isFixed = true;
        for (CFGNode node : this.cfg.getCFGNodes()) {
            if (!node.isEntryPoint()) {
                List<ILattice> outLattices = new ArrayList<>();
                for (CFGNode cfgNode : node.getPrevious()) {
                    outLattices.add(cfgNode.getCfgState().getOut());
                }
                ILattice newOut=
                        outLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
                isFixed &= newOut.isEquals(node.getCfgState().getIn());
                node.getCfgState().setIn(newOut);
            }
        }
        isFixedPoint = isFixed;
    }

    private void updateLatticesBackward() {
        boolean isFixed = true;
        for (CFGNode node : this.cfg.getCFGNodes()) {
            if (!node.isExitPoint()) {
                List<ILattice> inLattices = new ArrayList<>();
                for (CFGNode cfgNode : node.getNext()) {
                    inLattices.add(cfgNode.getCfgState().getIn());
                }
                ILattice newIn =
                        inLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
                isFixed &= newIn.isEquals(node.getCfgState().getOut());
                node.getCfgState().setOut(newIn);
            }
        }
        isFixedPoint = isFixed;

    }
}
