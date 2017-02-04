package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;
import eu.adrianbrink.parser.Expression;
import eu.adrianbrink.parser.Number;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Adrian Brink on 29/01/2017.
 */
public class SignAnalysis implements IAnalysisFramework {

    public Function<LatticeElement<BitSet>, LatticeElement<BitSet>> transferFunction(CFGNode node, ILattice lattice) {
        // first check whether it is an assignment
        // for SignAnalysis only assignments matter
        if (node.getStatementOrExpression() instanceof Assignment) {
            Expression expression = ((Assignment) node.getStatementOrExpression()).e;
            // check which expressions are relevant for SignAnalysis
            // bool expressions, conjunctions, disjunctions, equality, negation are not relevant
            // relevant: number, addition, multiplication, variable
            if (expression instanceof Number) {
                if (((Number) expression).getN() >= 0) {
                    return (LatticeElement<BitSet> one) -> {
                        BitSet plusBitSet = new BitSet();
                        plusBitSet.set(0, true);
                        plusBitSet.set(1, false);
                        LatticeElement<BitSet> two = new LatticeElement<>(plusBitSet);
                        LatticeElement<BitSet> newLatticeElement = lattice.join(one, two);
                        return newLatticeElement;
                    };
                } else {
                    return (LatticeElement<BitSet> one) -> {
                        BitSet minusBitSet = new BitSet();
                        minusBitSet.set(0, false);
                        minusBitSet.set(1, true);
                        LatticeElement<BitSet> two = new LatticeElement<>(minusBitSet);
                        LatticeElement<BitSet> newLatticeElement = lattice.join(one, two);
                        return newLatticeElement;
                    };
                }
            } else {
                return (LatticeElement<BitSet> one) -> {
                    return one;
                };
            }
        }
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
    public LatticeElement<BitSet> initialElement() {
        BitSet bitSet = new BitSet();
        bitSet.set(0, false);
        bitSet.set(0, false);
        LatticeElement<BitSet> latticeElement = new LatticeElement<>(bitSet);
        return latticeElement;
    }
}
