package eu.abit.TransferFunction

import eu.abit.lattice.LatticeElement
import eu.adrianbrink.parser._

import scala.annotation.tailrec

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
                                   (neighborLatticeElement: LatticeElement[A])
                                   (stat: Statement): LatticeElement[A]

  /*
    @variable env: Represents the environment of the analysis
    @variable latticeElement: Represents the lattice element of the previous/next program point
    @variable exp: The expression at the program point being analyzed.
   */
  // Not sure if we need that though since we can get the expressions from the statements.
  def transferFunctionForExpressions(env: Map[Variable, LatticeElement[A]])
                                    (neighborLatticeElement: LatticeElement[A])
                                    (exp: Expression): LatticeElement[A]
}

object Test extends App {

  val ex2 = new Addition(6, new Variable(1, "x"), new Number(0, 10))
  val ex1 = new Addition(6, new Variable(2, "y"), new Number(3, 12));
  val ex = new Addition(7, ex1, ex2)

  val previous = new LatticeElement[Expression](extractNonTrivialExpFrom(ex)(Set.empty))
  // x =  232 + -323 + 2333
  val assignX = new Assignment(10, "x", new Addition(10 ,new Addition(10, new Number(10, 232) , new Number(11, -323)), new Number(1, 2333)))



  val transferFunctionForAE = new TransferFunction[Expression] {
    override def transferFunctionForExpressions(env: Map[Variable, LatticeElement[Expression]])
                                               (previous: LatticeElement[Expression])
                                               (expression: Expression): LatticeElement[Expression] =
      throw new NotImplementedError();

    override def transferFunctionForStatements(env: Map[Variable, LatticeElement[Expression]])
                                             (previous: LatticeElement[Expression])
                                             (statement: Statement): LatticeElement[Expression] =
      statement match {
        case assignment: Assignment => {
          val expInDependentOnVar = previous.element.filter(e => !e.toString().contains(assignment.x))
          val additionalSet = extractNonTrivialExpFrom(assignment.e)(Set.empty)
          new LatticeElement[Expression](expInDependentOnVar ++ additionalSet)
        }
        case out: Output => new LatticeElement[Expression](extractNonTrivialExpFrom(out.e)(Set.empty))
        case iff: If => new LatticeElement[Expression](extractNonTrivialExpFrom(iff.b)(Set.empty))
        case wh: While => new LatticeElement[Expression](extractNonTrivialExpFrom(wh.b)(Set.empty))
        case _ => previous
      }
  }

  val resultLattice = transferFunctionForAE.transferFunctionForStatements(Map.empty[Variable, LatticeElement[Expression]])(previous)(assignX)
  val k = 1

  private def extractNonTrivialExpFrom(expression: Expression)(res: Set[Expression]): Set[Expression]  = expression match {
    case con: Conjunction => extractNonTrivialExpFrom(con.b1)(extractNonTrivialExpFrom(con.b2)(res ++ Set(con)))
    case dis: Disjunction => extractNonTrivialExpFrom(dis.b1)(extractNonTrivialExpFrom(dis.b2)(res ++ Set(dis)))
    case eq: Equality => extractNonTrivialExpFrom(eq.e1)(extractNonTrivialExpFrom(eq.e2)(res ++ Set(eq)))
    case neq: Negation => extractNonTrivialExpFrom(neq.b)(res ++ Set(neq))
    case lt: LessThan => extractNonTrivialExpFrom(lt.e1)(extractNonTrivialExpFrom(lt.e2)(res ++ Set(lt)))
    case ad: Addition => extractNonTrivialExpFrom(ad.e1)(extractNonTrivialExpFrom(ad.e2)(res++ Set(ad)))
    case _ => res
  }
}
