package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;

/**
 * Created by sly on 28/01/2017.
 */
// This is an abstract type that defines a general analysis. A concrete type implements it and as such implements either a Naive, Chaotic or Worklist approach.
public abstract class Analysis {
    private CFG cfg;
    private Lattice lattice;
    private IAnalysis analysis;

    public Analysis(CFG cfg, IAnalysis analysis) {
        this.cfg = cfg;
        this.analysis = analysis;
    }

    public CFG getCfg() {
        return this.cfg;
    }

    public IAnalysis getAnalysis() {
        return this.analysis;
    }

    public Lattice getLattice() {
        return this.lattice;
    }

    public void setLattice(Lattice lattice) {
        this.lattice = lattice;
    }
}
