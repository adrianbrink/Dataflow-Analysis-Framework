package eu.adrianbrink.dataflowanalysis.Engine;

import eu.adrianbrink.dataflowanalysis.Analysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;

/**
 * Created by sly on 30/01/2017.
 */
public interface IAnalysisEngine {

    void run(IAnalysisFramework iAnalysisFramework, ILattice lattice);

}
