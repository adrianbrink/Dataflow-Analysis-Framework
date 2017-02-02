package eu.abit.DFAnalysis

import eu.abit.TransferFunction.TransferFunction
import eu.abit.lattice.Lattice
import eu.adrianbrink.dataflowanalysis.CFG.{CFG, CFGNode}

/**
  * A DFAnalysis starts with a control flow graph, dataflow constraints and a Lattice.
  * @tparam A: Type parameter for the elements' type of the lattice.
  */
@FunctionalInterface
abstract class DFAnalysis[A] {
  /**
    * Represents the algorithm that calculates the fixed point.
    * @param cfg: The control flow graph.
    * @param lattice: The lattice
    * @param constraints: The data flow constraints.
    * @return : List of CFGNode instances with the fixed point encapsulated in them
    */
  def computeFixPoint(cfg: CFG)(lattice: Lattice[A])(constraints: TransferFunction[A]): List[CFGNode]
}

object Test extends App {

}
