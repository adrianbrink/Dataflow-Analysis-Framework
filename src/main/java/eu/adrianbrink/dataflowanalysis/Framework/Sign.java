package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by sly on 29/01/2017.
 */
public class Sign implements IAnalysisFramework {

    public Function<ILattice, ILattice> transferFunction(CFGNode node) {
        return null;
    }

    public Set<String> getProgramParameters(CFG cfg) {
        Set<String> programParameters = new HashSet<>();
        for (CFGNode node : cfg.getCFGNodes()) {
            AST assignmentOrExpression = node.getStatementOrExpression();
            if (assignmentOrExpression instanceof Assignment) {
                programParameters.add(((Assignment) assignmentOrExpression).x);
            }
        }
        return programParameters;
    }

    @Override
    public LatticeElement initialElement() {
        BitSet bitSet = new BitSet();
        bitSet.set(0, false);
        bitSet.set(0, false);
        LatticeElement<BitSet> latticeElement = new LatticeElement<>(bitSet);
        return latticeElement;
    }

//    // TODO: These transfer functions are troubling me, since they involve a lot of type casting and I don't know whether that is okay
//    @Override
//    public Function<ILattice, ILattice> transferFunction(CFGNode node) {
//        AST assignmentOrExpression = node.getStatementOrExpression();
//        if (assignmentOrExpression instanceof Assignment) {
//            Assignment assignment = (Assignment) assignmentOrExpression;
//            String variableName = assignment.x;
//            Expression expression = assignment.e;
//            // TODO: I assume here that the only thing that affects the sign of a variable is a number expression
//            if (expression instanceof Number) {
//                Number number = (Number) expression;
//                if (number.getN() >= 0) {
//                    return (ILattice lattice1) -> {
//                        BitSet bitSet1 = (BitSet) lattice1.getValueForParameter(variableName);
//                        BitSet newBitSet = (BitSet) bitSet1.clone();
//                        newBitSet.or(this.plus);
//                        ILattice newLattice = lattice1.deepCopy();
//                        newLattice.setValueForParameter(variableName, newBitSet);
//                        return newLattice;
//                    };
//                } else {
//                    return (ILattice lattice1) -> {
//                        BitSet bitSet1 = (BitSet) lattice1.getValueForParameter(variableName);
//                        BitSet newBitSet = (BitSet) bitSet1.clone();
//                        newBitSet.or(this.minus);
//                        ILattice newLattice = lattice1.deepCopy();
//                        newLattice.setValueForParameter(variableName, newBitSet);
//                        return newLattice;
//                    };
//                }
//            }
//        } else {
//            // any node that isn't an assignment has no effect on the state of my lattices and hence I just return the original lattice
//            return (ILattice lattice1) -> {
//                return lattice1;
//            };
//        }
//        // shouldn't be reachable, since everything will be caught by the last else
//        return null;
//    }
}
