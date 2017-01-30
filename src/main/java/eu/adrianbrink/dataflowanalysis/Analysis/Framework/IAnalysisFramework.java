package eu.adrianbrink.dataflowanalysis.Analysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.function.Function;

/**
 * Created by sly on 30/01/2017.
 */
public interface IAnalysisFramework<A> {
    // This function returns all the elements that are needed for the lattice. For example all variables for sign analysis or all expressions for AvailableExpressionsAnalysis
    public A getDomain(CFG cfg);

    // this defines the transformation between the in lattice and the out lattice for any given node
    public Function<ILattice, ILattice> transferFunction(CFGNode cfg);
}
