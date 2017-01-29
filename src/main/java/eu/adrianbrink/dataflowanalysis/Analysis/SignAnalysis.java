package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;
import eu.adrianbrink.parser.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by sly on 29/01/2017.
 */
// this determines the direction we move in (forward/backward) and whether it is may/must.
public class SignAnalysis implements IAnalysis {
    private Lattice lattice;
    // TODO: Change Bottom and Top to the symbols, because otherwise the variable names could clash.
    @Override
    public Lattice generateLattice(CFG cfg) {
        Set<Assignment> assignmentSet = cfg.getAssignments();
        List<String> assignmentLeftValues = new ArrayList<>();
        assignmentLeftValues.add("Bottom");
        for (Assignment assignment : assignmentSet) {
            assignmentLeftValues.add(assignment.x);
        }
        assignmentLeftValues.add("Top");
        Lattice lattice = new Lattice(cfg.getCfgNodes().size(), assignmentLeftValues);
        // initialise the lattice to bottom
        lattice.setAll(0, true);
        this.lattice = lattice;
        return lattice;
    }

    @Override
    public Function<LatticeElement, LatticeElement> getTransferFunction(AST statementOrExpression) {
        if (statementOrExpression instanceof Assignment) {
            int index = lattice.getIndex(((Assignment) statementOrExpression).x);
            return (LatticeElement element1) -> {
                LatticeElement newElement = element1.set(index, true);
                return newElement;
            };
        }
        return null;
    }
}
