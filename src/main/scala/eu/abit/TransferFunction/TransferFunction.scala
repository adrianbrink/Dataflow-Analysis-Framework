package eu.abit.TransferFunction

import eu.abit.lattice.LatticeElement
import eu.adrianbrink.parser.{Expression, Statement, Variable}

/*
  Represents the Transfer Function for each program point.
 */
abstract class TransferFunction[A] {
  /*
    @variable env: Represents the environment of the analysis
    @variable latticeElement: Represents the lattice element of the previous/next program point
    @variable stat: The statement at the program point being analyzed.
   */
  def transferFunctionForStatements(env: Map[Variable, LatticeElement[A]])
                                   (latticeElement: LatticeElement[A])
                                   (stat: Statement): LatticeElement[A]

  /*
    @variable env: Represents the environment of the analysis
    @variable latticeElement: Represents the lattice element of the previous/next program point
    @variable exp: The expression at the program point being analyzed.
   */
  // Not sure if we need that though since we can get the expressions from the statements.
  def transferFunctionForExpressions(env: Map[Variable, LatticeElement[A]])
                                    (latticeElement: LatticeElement[A])
                                    (exp: Expression)
}
