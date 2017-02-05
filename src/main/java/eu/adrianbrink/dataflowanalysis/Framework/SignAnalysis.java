package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.EnvironmentLattice;
import eu.adrianbrink.dataflowanalysis.Lattice.SignLattice;
import eu.adrianbrink.parser.*;
import eu.adrianbrink.parser.Number;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Adrian Brink on 29/01/2017.
 */
// user implemented
public class SignAnalysis implements IAnalysisFramework<EnvironmentLattice> {
    @Override
    public Function<EnvironmentLattice, EnvironmentLattice> transferFunction(CFGNode cfgNode) {
        AST statementOrExpression = cfgNode.getStatementOrExpression();
        if (statementOrExpression instanceof Assignment) {
            String variable = ((Assignment) statementOrExpression).x;
            Expression expression = ((Assignment) statementOrExpression).e;

            if ((expression instanceof Expression) && (!(expression instanceof BoolExpression))) {
                return (EnvironmentLattice one) -> {
                    BitSet bitSet = SignAnalysis.eval(expression, one);
                    EnvironmentLattice two = one.join(one);
                    SignLattice lattice = new SignLattice();
                    lattice.element = bitSet;
                    two.environment.put(variable, lattice);
                    return two;
                };
            }
        }
        return (EnvironmentLattice one) -> {
            EnvironmentLattice two = one.join(one);
            return two;
        };
    }
    @Override
    public EnvironmentLattice getInitialLattice(CFG cfg) {
        return new EnvironmentLattice(programParameters(cfg));
    }

    private Set<String> programParameters(CFG cfg) {
        Set<String> programParameters = new HashSet<>();
        for (CFGNode node : cfg.getCFGNodes()) {
            AST assignmentOrExpression = node.getStatementOrExpression();
            if (assignmentOrExpression instanceof Assignment) {
                programParameters.add(((Assignment) assignmentOrExpression).x);
            }
        }
        return programParameters;
    }

    private static BitSet eval(Expression expression, EnvironmentLattice lattice) {
        if (expression instanceof Number) {
            int number = ((Number) expression).getN();
            if (number >= 0) {
                BitSet bitSet = new BitSet();
                bitSet.set(0, true);
                bitSet.set(1, false);
                return  bitSet;
            } else {
                BitSet bitSet = new BitSet();
                bitSet.set(0, false);
                bitSet.set(1, true);
                return bitSet;
            }
        } else if (expression instanceof Addition) {
            Expression expression1 = ((Addition) expression).e1;
            Expression expression2 = ((Addition) expression).e2;
            BitSet bitSet1 = SignAnalysis.eval(expression1, lattice);
            BitSet bitSet2 = SignAnalysis.eval(expression2, lattice);
            bitSet1.or(bitSet2);
            return bitSet1;
        } else if (expression instanceof Multiplication) {
            Expression expression1 = ((Multiplication) expression).e1;
            Expression expression2 = ((Multiplication) expression).e2;
            BitSet bitSet1 = SignAnalysis.eval(expression1, lattice);
            BitSet bitSet2 = SignAnalysis.eval(expression2, lattice);
            BitSet minusBitSet = new BitSet();
            minusBitSet.set(0, false);
            minusBitSet.set(1, true);
            if (bitSet1.equals(minusBitSet) && bitSet2.equals(minusBitSet)) {
                BitSet plusBitSet = new BitSet();
                plusBitSet.set(0, true);
                plusBitSet.set(1, false);
                return plusBitSet;
            }
            bitSet1.or(bitSet2);
            return bitSet1;
            // Variable
        } else if (expression instanceof Variable) {
            String variable = ((Variable) expression).id;
            BitSet bitSet = lattice.environment.get(variable).element;
            BitSet bitSet1 = (BitSet) bitSet.clone();
            return bitSet1;
        } else {
            // never reachable
            return null;
        }
    }
}
