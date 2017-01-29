package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // TODO: This should be put in the interface and return whatever you are interested in (Expressions for AvailableExpressions or Assignments for SignAnalysis)
    private static Set<Assignment> getAssignments(CFG cfg) {
        Set<Assignment> assignmentsSet = new HashSet<>();
        for (CFGNode node : cfg.getCfgNodes()) {
            AST assigment = node.getStatementOrExpression();
            if (assigment instanceof Assignment) {
                assignmentsSet.add((Assignment) assigment);
            } else {
                continue;
            }
        }
        return assignmentsSet;
    }

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

//    public Function<BitSet, BitSet> getTransferFunction(AST statementOrExpression) {
//        if (statementOrExpression instanceof Assignment) {
//            Expression expression = ((Assignment) statementOrExpression).e.;
//            return (BitSet element1) -> {
//
//                if (element1.get())
//                BitSet newElement = element1.set(index, true);
//                return newElement;
//            };
//        }
//        return null;
//    }
}
