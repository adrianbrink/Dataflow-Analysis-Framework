package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.dataflowanalysis.Lattice.EnvironmentLattice;

/**
 * Created by sly on 30/01/2017.
 */

// This tracks the in and out lattices for every node in the CFG
public class CFGState {
    private EnvironmentLattice in;
    private EnvironmentLattice out;

    public CFGState(EnvironmentLattice in, EnvironmentLattice out) {
        this.in = in;
        this.out = out;
    }

    public EnvironmentLattice getIn() {
        return this.in;
    }

    public void setIn(EnvironmentLattice in) {
        this.in = in;
    }

    public EnvironmentLattice getOut() {
        return this.out;
    }

    public void setOut(EnvironmentLattice out) {
        this.out = out;
    }
}
