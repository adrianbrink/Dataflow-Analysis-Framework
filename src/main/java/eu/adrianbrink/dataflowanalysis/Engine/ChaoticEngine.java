package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by sly on 30/01/2017.
 */
public class ChaoticEngine implements IAnalysisEngine {

    private CFG cfg;
    private boolean isFixedPoint, isBackwards;
    private IAnalysisFramework framework;
    public ChaoticEngine(CFG cfg, IAnalysisFramework framework) {
        this.cfg = cfg;
        this.isBackwards = framework.isBackward();
        this.framework = framework;
    }

    @Override
    public void run() {
        if (isBackwards) {
            do {
                this.runIterationBackward();
            } while (!isFixedPoint);
        } else {
            do {
                this.runIterationForward();
            } while (!isFixedPoint);
        }
    }

    private void runIterationForward() {
        boolean isFixed = true;
        for (CFGNode node : cfg.getCFGNodes()) {
            if (!node.isEntryPoint()) {
                List<ILattice> outLattices = new ArrayList<>();
                for (CFGNode prev : node.getPrevious()) {
                    outLattices.add(prev.getCfgState().getOut());
                }
                ILattice newOut=
                        outLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
                isFixed &= newOut.isEquals(node.getCfgState().getIn());
                node.getCfgState().setIn(newOut);

                Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
                ILattice in = node.getCfgState().getIn();
                ILattice out = transferFunction.apply(in);
                node.getCfgState().setOut(out);
            }
        }
        isFixedPoint = isFixed;
    }
    private void runIterationBackward() {
        boolean isFixed = true;
        for (CFGNode node : cfg.getCFGNodes()) {
            if (!node.isExitPoint()) {
                Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
                List<ILattice> inLattices = new ArrayList<>();
                for (CFGNode next : node.getNext()) {
                    inLattices.add(next.getCfgState().getIn());
                }
                ILattice newIn=
                        inLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
                isFixed &= newIn.isEquals(node.getCfgState().getOut());
                node.getCfgState().setOut(newIn);

                ILattice out = node.getCfgState().getOut();
                ILattice in = transferFunction.apply(out);
                node.getCfgState().setIn(in);
            }
        }
        isFixedPoint = isFixed;
    }

}
