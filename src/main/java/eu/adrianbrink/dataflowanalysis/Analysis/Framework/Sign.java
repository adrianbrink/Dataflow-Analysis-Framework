package eu.adrianbrink.dataflowanalysis.Analysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.SignLattice;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;
import eu.adrianbrink.parser.Expression;
import eu.adrianbrink.parser.Number;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by sly on 29/01/2017.
 */
// This class is implemented by the user.
    // TODO: It could be nice if this class didn't depend on CFG
public class Sign implements IAnalysisFramework {
    private CFG cfg;
    private BitSet plus;
    private BitSet minus;

    public Sign(CFG cfg) {
        this.cfg = cfg;

        this.plus = new BitSet(2);
        this.plus.set(0, true);
        this.plus.set(1, false);

        this.minus = new BitSet(2);
        this.minus.set(0, false);
        this.minus.set(1, true);
    }

    @Override
    public ILattice initialLattice() {
        ILattice initialLattice = new SignLattice(this.getProgramParameters());
        initialLattice.initialise();
        return initialLattice;
    }

    @Override
    public Set<String> getProgramParameters() {
        Set<String> programParameters = new HashSet<>();
        for (CFGNode node : this.cfg.getCfgNodes()) {
            AST assignmentOrExpression = node.getStatementOrExpression();
            if (assignmentOrExpression instanceof Assignment) {
                programParameters.add(((Assignment) assignmentOrExpression).x);
            }
        }
        return programParameters;
    }

    // TODO: These transfer functions are troubling me, since they involve a lot of type casting and I don't know whether that is okay
    @Override
    public Function<ILattice, ILattice> transferFunction(CFGNode node) {
        AST assignmentOrExpression = node.getStatementOrExpression();
        if (assignmentOrExpression instanceof Assignment) {
            Assignment assignment = (Assignment) assignmentOrExpression;
            String variableName = assignment.x;
            Expression expression = assignment.e;
            // TODO: I assume here that the only thing that affects the sign of a variable is a number expression
            if (expression instanceof Number) {
                Number number = (Number) expression;
                if (number.getN() >= 0) {
                    return (ILattice lattice1) -> {
                        BitSet bitSet1 = (BitSet) lattice1.getValueForParameter(variableName);
                        BitSet newBitSet = (BitSet) bitSet1.clone();
                        newBitSet.or(this.plus);
                        ILattice newLattice = lattice1.deepCopy();
                        newLattice.setValueForParameter(variableName, newBitSet);
                        return newLattice;
                    };
                } else {
                    return (ILattice lattice1) -> {
                        BitSet bitSet1 = (BitSet) lattice1.getValueForParameter(variableName);
                        BitSet newBitSet = (BitSet) bitSet1.clone();
                        newBitSet.or(this.minus);
                        ILattice newLattice = lattice1.deepCopy();
                        newLattice.setValueForParameter(variableName, newBitSet);
                        return newLattice;
                    };
                }
            }
        } else {
            // any node that isn't an assignment has no effect on the state of my lattices and hence I just return the original lattice
            return (ILattice lattice1) -> {
                return lattice1;
            };
        }
        // shouldn't be reachable, since everything will be caught by the last else
        return null;
    }
}
