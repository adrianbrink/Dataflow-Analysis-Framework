package main.com.availableexpressions.util

import main.com.parser._

object Util {

  def astToList(ast: AST): List[AST] = astToList(ast, List.empty)

  private def astToList(ast: AST, astList: List[AST]): List[AST] = {
    ast match {
      case exp: Expression => unrollExpression(exp, astList)
      case stat: Statement => unrollStatement(stat, astList)
    }
  }

  private def unrollExpression(expression: Expression, astList: List[AST]): List[AST] = {
    expression match {
      case addition: Addition => addition :: unrollExpression(addition.e2, astList) ++ unrollExpression(addition.e2, astList)
      case multiplication: Multiplication => multiplication :: unrollExpression(multiplication.e1, astList) ++
        unrollExpression(multiplication.e2, astList)
      case boolE: BoolExpression =>  unrollBoolExpression(boolE, astList)
      case _ => astList
    }
  }

  private def unrollStatement(statement: Statement, astList: List[AST]): List[AST] = {
    statement match {
      case seq: Sequence => unrollStatement(seq.s1, astList) ++ unrollStatement(seq.s2, astList)
      case ass: Assignment => ass :: unrollExpression(ass.e, astList)
      case i: If => unrollBoolExpression(i.b, astList) ++ unrollStatement(i.s1, astList) ++ unrollStatement(i.s2, astList)
      case wh: While => unrollBoolExpression(wh.b, astList) ++ unrollStatement(wh.s, astList)
      case output: Output => unrollExpression(output.e, astList)
      case _ => astList
    }
  }

  private def unrollBoolExpression(boolExpression: BoolExpression, astList: List[AST]): List[AST] = {
    boolExpression match {
      case equal: Equality => equal :: unrollExpression(equal.e1, astList) ++ unrollExpression(equal.e2, astList)
      case conj: Conjunction => conj :: unrollBoolExpression(conj.b1, astList) ++ unrollBoolExpression(conj.b2, astList)
      case dis: Disjunction => dis :: unrollBoolExpression(dis.b1,astList) ++ unrollBoolExpression(dis.b2, astList)
      case lt: LessThan => lt :: unrollExpression(lt.e1, astList) ++ unrollExpression(lt.e2, astList)
      case ng: Negation => ng :: unrollBoolExpression(ng.b, astList)
      case _ => astList
    }
  }

  def toListStatements(ast: AST): List[Statement] = unroll(ast, List.empty)

  private def unroll(ast: AST, list: List[Statement]): List[Statement] = {
    ast match {
      case seq: Sequence => unroll(seq.s1, list) ++ unroll(seq.s2, list)
      case stat: Statement => stat :: list
      case _ => list
    }
  }
}
