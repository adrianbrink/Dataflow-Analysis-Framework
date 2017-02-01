package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;

import java.util.Set;
import java.util.function.Function;

/*
When implementing your specific analysis you need to implement three functions:
- transferFunction()
    - this needs to return the appropriate transfer function for every language construct
- getProgramParameters()
    - this needs to return all the programParameters you are interested in, e.g.: variables
- initialElement()
    - this needs to return what should be the initial LatticeElement for every program parameter
 */

/**
 * Created by sly on 30/01/2017.
 */
public interface IAnalysisFramework<A, B> {

    public Set<A> getProgramParameters(CFG cfg);

    public Function<ILattice, ILattice> transferFunction(CFGNode node);

    public LatticeElement<B> initialElement();

}
