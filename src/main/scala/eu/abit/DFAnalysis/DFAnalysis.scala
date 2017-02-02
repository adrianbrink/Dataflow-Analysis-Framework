package eu.abit.DFAnalysis

import eu.abit.TransferFunction.TransferFunction
import eu.abit.lattice.Lattice
import eu.adrianbrink.dataflowanalysis.CFG.CFG

/**
  * Created by antreas on 2/2/17.
  */
abstract class DFAnalysis[A](cfg: CFG)
                         (lattice: Lattice[A])
                         (transferFunction: TransferFunction[A]) {
}
