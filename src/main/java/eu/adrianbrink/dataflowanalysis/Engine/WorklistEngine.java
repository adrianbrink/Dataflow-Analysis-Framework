package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.EnvironmentLattice;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.*;
import java.util.function.Function;

/**
 * Created by sly on 30/01/2017.
 */
public class WorklistEngine implements IAnalysisEngine {
    private CFG cfg;
    private boolean isBackward;
    private Queue<CFGNode> queue;
    private IAnalysisFramework framework;

    public WorklistEngine(CFG cfg, IAnalysisFramework framework) {
        this.cfg = cfg;
        this.isBackward = framework.isBackward();
        this.queue = new LinkedList<>();
        this.framework = framework;
    }

    @Override
    public void run() {
        if (isBackward) {
            for (CFGNode node : this.cfg.getCFGNodes()) {
                if (!node.isExitPoint()) {
                    queue.offer(node);
                }
            }
            while (!queue.isEmpty()) {
                CFGNode node = queue.poll();
                updateLatticeBackward(node);
                if (runTransferFunctionBackward(node)) {
                    for (CFGNode dependent : node.getPrevious()) {
                        if (!queue.contains(dependent)) {
                            queue.offer(dependent);
                        }
                    }
                }
            }
        } else {
            for (CFGNode node : this.cfg.getCFGNodes()) {
                if (!node.isEntryPoint()) {
                    queue.offer(node);
                }
            }
            while (!queue.isEmpty()) {
                CFGNode node = queue.poll();
                updateLatticeForward(node);
                if (runTransferFunctionForward(node)) {
                    for (CFGNode dependent : node.getNext()) {
                        if (!queue.contains(dependent)) {
                            queue.offer(dependent);
                        }
                    }
                }
            }
        }
    }

    private void updateLatticeForward(CFGNode node) {
        List<ILattice> outLattices = new ArrayList<>();
        for (CFGNode prev : node.getPrevious()) {
            outLattices.add(prev.getCfgState().getOut());
        }
        ILattice newOut=
                outLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
        node.getCfgState().setIn(newOut);
    }

    private boolean runTransferFunctionForward(CFGNode node) {
        Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
        ILattice in = node.getCfgState().getIn();
        ILattice out = transferFunction.apply(in);
        boolean hasChanged = !node.getCfgState().getOut().isEquals(out);
        node.getCfgState().setOut(out);
        return hasChanged;
    }

    private void updateLatticeBackward(CFGNode node) {
        List<ILattice> inLattices = new ArrayList<>();
        for (CFGNode next : node.getNext()) {
            inLattices.add(next.getCfgState().getIn());
        }
        ILattice newIn =
                inLattices.stream().reduce(framework.getInitialLattice(cfg), (l1, l2) -> (ILattice)l1.join(l2));
        node.getCfgState().setOut(newIn);
    }

    private boolean runTransferFunctionBackward(CFGNode node) {
        Function<ILattice, ILattice> transferFunction = node.getTransferFunction();
        ILattice out = node.getCfgState().getOut();
        ILattice in = transferFunction.apply(out);
        boolean hasChanged = !node.getCfgState().getIn().isEquals(in);
        node.getCfgState().setIn(in);
        return hasChanged;
    }

}
