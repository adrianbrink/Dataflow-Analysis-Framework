package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

/**
 * Created by sly on 30/01/2017.
 */

// This tracks the in and out lattices for every node in the CFG
public class CFGState {
    private CFGNode node;
    private ILattice in;
    private ILattice out;
}
