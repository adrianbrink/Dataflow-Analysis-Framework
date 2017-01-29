package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.AST;

import java.util.function.Function;

/**
 * Created by sly on 28/01/2017.
 */
public interface IAnalysis {
    public Function<LatticeElement, LatticeElement> getTransferFunction(AST statementOrExpression);
    public Lattice generateLattice(CFG cfg);
}
