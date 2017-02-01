package eu.adrianbrink.dataflowanalysis.Analysis;

import eu.adrianbrink.dataflowanalysis.Engine.IAnalysisEngine;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class Analysis {
    private IAnalysisEngine engine;

    public Analysis(IAnalysisEngine engine) {
        this.engine = engine;
    }

    public void run() {
        engine.run();
    }
}
