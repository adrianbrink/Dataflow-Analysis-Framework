package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.Engine.IAnalysisEngine;

/**
 * Created by sly on 30/01/2017.
 */

// this class combines the Engine with the Framework and runs the different analyses.
public class Analysis {
    private IAnalysisEngine engine;

    public Analysis(IAnalysisEngine engine) {
        this.engine = engine;
    }

    public void run() {

    }
}
