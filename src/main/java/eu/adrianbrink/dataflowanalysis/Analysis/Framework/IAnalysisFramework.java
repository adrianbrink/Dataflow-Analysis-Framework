package eu.adrianbrink.dataflowanalysis.Analysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by sly on 30/01/2017.
 */
public interface IAnalysisFramework<A> {
    // this function returns all elements of the lattice. In case of Sign: Top, Bottom, +, -
    // public Set<A> getDomain();

    // this function returns all program parameters that I am interested in. In case of Sign: all variables in the program
    public Set<A> getProgramParameters();

    // this defines the transformation between the in lattice and the out lattice for any given
    // this function is defined in terms of gen() and kill()
    public Function<ILattice, ILattice> transferFunction(CFGNode node);

    // returns the initial lattice
    public ILattice initialLattice();
}
