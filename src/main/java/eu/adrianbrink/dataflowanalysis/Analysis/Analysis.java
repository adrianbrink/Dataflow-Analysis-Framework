package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Lattice.Lattice;

/**
 * Created by sly on 28/01/2017.
 */
public class Analysis {
    private CFG cfg;
    private Lattice lattice;
    private IAnalysis analysis;

    public Analysis(CFG cfg, Lattice lattice, IAnalysis analysis) {
        this.cfg = cfg;
        this.lattice = lattice;
        this.analysis = analysis;
    }
}
