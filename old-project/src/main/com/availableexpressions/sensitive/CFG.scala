package main.com.availableexpressions.sensitive

import main.com.parser._

class CFG(entry: EntryN)

object CFG {

  def astToCFG(lAst: List[AST]): List[OldCfg] = astToCFG(lAst, List.empty) reverse

  private def astToCFG(lAst: List[AST], lp: List[OldCfg]): List[OldCfg] = {
    lAst match  {
      case Nil => lp
      case h :: t => h match {
        case a: Assignment => {
          val pp = lp.headOption.getOrElse(OldCfg(List.empty))
          val res = pp.list.filter(x => !(x.toString contains a.x))
          astToCFG(t, OldCfg(res) :: lp)
        }
        case e: Expression =>
          val pp = lp.headOption.getOrElse(OldCfg(List.empty))
          val res: List[Expression] = e :: pp.list
          val (prp, rl) = consumeASTListAndGetProgramPoint(t, res, e.line)
          astToCFG(rl, prp :: lp)
      }
    }
  }

  private def consumeASTListAndGetProgramPoint(lAst: List[AST], ppList: List[Expression], prLine: Int):
  (OldCfg, List[AST]) = {
    lAst match {
      case h :: t =>
        h match {
          case e: Expression if prLine == e.line =>
            consumeASTListAndGetProgramPoint(t, e :: ppList, e.line)
          case _ => (OldCfg(ppList), lAst)
        }
      case Nil => (OldCfg(ppList), lAst)
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