package eu.adrianbrink.dataflowanalysis.Engine;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class NaiveEngine implements IAnalysisEngine {

    @Override
    public void run() {
    }

//    private void initialiseLattices() {
//        for (CFGNode node : this.cfg.getCFGNodes()) {
//            //node.setIn(this.lattice.newLattice(node.getParameter()));
//            //node.setOut(this.lattice.newLattice(node.getParameter()));
//        }
//    }
//
//    private void runTransferFunctions() {
//        for (CFGNode node : this.cfg.getCFGNodes()) {
//            Function<LatticeElement, LatticeElement> transferFunction = node.getTransferFunction();
//            LatticeElement latticeElement = transferFunction.apply(node.getIn().getLatticeElement(node.getParameter()));
//            node.getOut().setLatticeElement(node.getParameter(), latticeElement);
//        }
//    }
//
//    private void updateLattices() {
//        for (CFGNode node : this.cfg.getCFGNodes()) {
//            if (node.getPrevious() == null) {
//                continue;
//            } else if (node.getPrevious().size() == 1) {
//                node.setIn(node.getIn().deepCopy());
//            } else {
//                for (CFGNode previousNode : node.getPrevious()) {
//
//                }
//            }
//        }
//    }
}
