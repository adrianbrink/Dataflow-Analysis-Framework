package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;
import eu.adrianbrink.parser.Number;

import java.util.*;
import java.util.function.Function;

/**
 * Created by sly on 29/01/2017.
 */
// this determines the direction we move in (forward/backward) and whether it is may/must.
public class SignAnalysis {
    private Lattice lattice;
    private CFG cfg;

    public SignAnalysis(CFG cfg) {
        this.cfg = cfg;
        this.lattice = SignAnalysis.generateLattice(cfg);
    }

    // TODO: Abstract away into IAlgorithm class. Enables different algorithms such as naive or chaotic to be used
    public void run() {
        Lattice previous = this.lattice.deepCopy();

        // start the main algorithm loop
        for (int i = 0; i < this.cfg.getSize(); i++) {
            CFGNode cfgNode = this.cfg.getCFGNode(i);
            // transferFunction might be null in case of entry and exit node but then we skip calling apply on it
            Function<BitSet, BitSet> transferFunction = this.getTransferFunction(cfgNode);
            Map<String, BitSet> latticeRow = this.lattice.getLatticeElement(i);
            // this is the case for entry and exit nodes
            if (cfgNode.getStatementOrExpression() == null) {
                continue;
            } else {
                AST statementOrExpression = cfgNode.getStatementOrExpression();
                if (statementOrExpression instanceof Assignment) {
                    BitSet initialBitSet = latticeRow.get(((Assignment) statementOrExpression).x);
                    transferFunction.apply(initialBitSet);
                }
            }
        }
    }

    // will be abstracted away into the IAnalysis class and will need to be implemented. Enables different analsyis
    // TODO: This should be put in the interface and return whatever you are interested in (Expressions for AvailableExpressions or Assignments for SignAnalysis)
    private static Set<Assignment> getAssignments(CFG cfg) {
        Set<Assignment> assignmentsSet = new HashSet<>();
        for (CFGNode node : cfg.getCfgNodes()) {
            AST assignment = node.getStatementOrExpression();
            if (assignment instanceof Assignment) {
                assignmentsSet.add((Assignment) assignment);
            } else {
                continue;
            }
        }
        return assignmentsSet;
    }

    // will be abstracted away into the IAnalysis class and will need to be implemented by clients
    // TODO: Change Bottom and Top to the symbols, because otherwise the variable names could clash.
    // TODO: This should also be in the interface and you should have to define your own lattice.
    private static Lattice generateLattice(CFG cfg) {
        Set<Assignment> assignmentSet = SignAnalysis.getAssignments(cfg);
        List<String> variables = new ArrayList<>();
        // all the variables, like x and y
        for (Assignment assignment : assignmentSet) {
            variables.add(assignment.x);
        }

        List<String> latticeElements = new ArrayList<>();
        latticeElements.add("Bottom");
        latticeElements.add("+");
        latticeElements.add("-");
        latticeElements.add("Top");
        Lattice lattice = new Lattice(cfg.getCfgNodes().size(), latticeElements, variables);
        // initialise the lattice to bottom
        lattice.setAll("Bottom");
        return lattice;
    }

    public Function<BitSet, BitSet> getTransferFunction(CFGNode cfgNode) {
        AST statementOrExpression = cfgNode.getStatementOrExpression();
        if (statementOrExpression instanceof Assignment) {
            Assignment assignment = (Assignment) statementOrExpression;
            Number number = (Number) assignment.e;
            int indexTop = this.lattice.getLatticeValue("Top");
            int indexPlus = this.lattice.getLatticeValue("+");
            int indexMinus = this.lattice.getLatticeValue("-");
            int indexBottom = this.lattice.getLatticeValue("Bottom");
            return (BitSet initial) -> {
                if (initial.get(indexTop)) {
                    return initial;
                } else if ((initial.get(indexPlus) && (number.getN() >= 0))) {
                    return initial;
                } else if ((initial.get(indexMinus) && (number.getN() < 0))) {
                    return initial;
                } else {
                    initial.clear();
                    initial.set(indexTop);
                    return initial;
                }
            };
        }
        // this case is possible for the entry and exit nodes, but not a problem since the invoking code handles that
        return null;
    }
}
