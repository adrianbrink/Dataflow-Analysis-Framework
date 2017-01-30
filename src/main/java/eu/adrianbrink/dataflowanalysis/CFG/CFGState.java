package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

/**
 * Created by sly on 30/01/2017.
 */

// This tracks the in and out lattices for every node in the CFG
public class CFGState {
    private ILattice in;
    private ILattice out;

    public CFGState(ILattice in, ILattice out) {
        this.in = in;
        this.out = out;
    }

    public ILattice getIn() {
        return this.in;
    }


    public ILattice getOut() {
        return this.out;
    }
}
