package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.EnvironmentLattice;
import eu.adrianbrink.dataflowanalysis.Lattice.SignLattice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            System.out.println("xxxx");
            this.updateLattices();
        } while (!isFixedPoint);
    }


    private void runTransferFunctions() {
        for (CFGNode node : this.cfg.getCFGNodes()) {
            Function<EnvironmentLattice, EnvironmentLattice> transferFunction = node.getTransferFunction();
            EnvironmentLattice in = node.getCfgState().getIn();
            EnvironmentLattice out = transferFunction.apply(in);
            node.getCfgState().setOut(out);
        }
    }

    private void updateLattices() {
        boolean isFixed = true;
        for (CFGNode node : this.cfg.getCFGNodes()) {
            if (node.getPrevious().isEmpty()) {
                continue;
            } else {
                List<EnvironmentLattice> outLattices = new ArrayList<>();
                for (CFGNode cfgNode : node.getPrevious()) {
                    outLattices.add(cfgNode.getCfgState().getOut());
                }
                if (outLattices.size() == 1) {
                    EnvironmentLattice newOut = outLattices.get(0).join(outLattices.get(0));
                    isFixed &= this.isEqual(newOut, node.getCfgState().getIn());
                    node.getCfgState().setIn(newOut);
                } else {
                    EnvironmentLattice newOut = outLattices.get(0).join(outLattices.get(1));
                    isFixed &= this.isEqual(newOut, node.getCfgState().getIn());
                    node.getCfgState().setIn(newOut);
                }
            }
        }
        isFixedPoint = isFixed;
    }

    private boolean isEqual(EnvironmentLattice out, EnvironmentLattice in) {
        for (Map.Entry entry : out.environment.entrySet()) {
            String key = (String) entry.getKey();
            SignLattice outLattice = (SignLattice) entry.getValue();
            SignLattice inLattice = in.environment.get(key);
            if (!outLattice.element.equals(inLattice.element)) {
                return false;
            }
        }
        return true;
    }
}
