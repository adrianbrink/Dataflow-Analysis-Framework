package main.com.availableexpressions.lattice

import main.com.parser.{AST, Expression}

class Lattice

object Lattice {
  def astToExpressionList(lAst: List[AST], out : List[Expression]) : List[Expression] = lAst match {
    case h :: t => h match {
      case e : Expression => astToExpressionList(t,appendIfListDosentContainThatExpression(out,e))
      case _ => astToExpressionList(t,out)
    }
    case _ => out
  }

  def appendIfListDosentContainThatExpression(lAst : List[Expression], exp : Expression) : List[Expression] = {
    if(lAst.contains(exp)) return lAst
    lAst.::(exp)
  }

  def getLattice(lAst : List[AST]): List[Set[Expression]] = astToExpressionList(lAst,List.empty).toSet.subsets().toList
}