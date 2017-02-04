package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.EnvironmentLattice;
import eu.adrianbrink.dataflowanalysis.Lattice.SignLattice;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;
import eu.adrianbrink.parser.Expression;

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
            if (SignAnalysis.eval(expression) >= 0) {
                return (EnvironmentLattice one) -> {
                    BitSet newBitSet = new BitSet();
                    newBitSet.set(0, true);
                    EnvironmentLattice two = one.join(one);
                    SignLattice lattice = new SignLattice();
                    lattice.element = newBitSet;
                    two.environment.put(variable, lattice);
                    return two;
                };
            }
        } else {
            return (EnvironmentLattice one) -> {
                EnvironmentLattice two = one.join(one);
                return two;
            };
        }
        return null;
    }

    public Set<String> programParameters(CFG cfg) {
        Set<String> programParameters = new HashSet<>();
        for (CFGNode node : cfg.getCFGNodes()) {
            AST assignmentOrExpression = node.getStatementOrExpression();
            if (assignmentOrExpression instanceof Assignment) {
                programParameters.add(((Assignment) assignmentOrExpression).x);
            }
        }
        return programParameters;
    }

    private static int eval(Expression expression) {
        return 0;
    }
}
