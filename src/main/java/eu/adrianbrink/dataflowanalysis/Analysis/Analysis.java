package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.Analysis.Engine.IAnalysisEngine;
import eu.adrianbrink.dataflowanalysis.Analysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

/**
 * Created by sly on 30/01/2017.
 */

// this class combines the Engine with the Framework and runs the different analyses.
public class Analysis {
    private IAnalysisFramework framework;
    private IAnalysisEngine engine;
    private  ILattice lattice;


    public Analysis(IAnalysisEngine engine, IAnalysisFramework framework, ILattice lattice) {
        this.engine = engine;
        this.framework = framework;
        this.lattice = lattice;
    }

    public void run() {
        engine.run(framework, lattice);
    }
}
