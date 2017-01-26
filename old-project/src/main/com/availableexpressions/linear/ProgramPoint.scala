package main.com.availableexpressions.linear

import main.com.parser._

case class ProgramPoint(list: List[Expression])

object ProgramPoint {

  def dataFlowAnalysisFor(lAst: List[AST]): List[ProgramPoint] = dataFlowAnalysis(lAst, List.empty) reverse

  private def dataFlowAnalysis(lAst: List[AST], lp: List[ProgramPoint]): List[ProgramPoint] = {
    lAst match  {
      case Nil => lp
      case h :: t => h match {
        case a: Assignment => {
          val pp = lp.headOption.getOrElse(ProgramPoint(List.empty))
          val res = pp.list.filter(x => !(x.toString contains a.x))
          dataFlowAnalysis(t, ProgramPoint(res) :: lp)
        }
        case e: Expression =>
          val pp = lp.headOption.getOrElse(ProgramPoint(List.empty))
          val res: List[Expression] = e :: pp.list
          val (prp, rl) = consumeASTListAndGetProgramPoint(t, res, e.line)
          dataFlowAnalysis(rl, prp :: lp)
      }
    }
  }

  private def consumeASTListAndGetProgramPoint(lAst: List[AST], ppList: List[Expression], prLine: Int):
  (ProgramPoint, List[AST]) = {
    lAst match {
      case h :: t =>
        h match {
          case e: Expression if prLine == e.line =>
            consumeASTListAndGetProgramPoint(t, e :: ppList, e.line)
          case _ => (ProgramPoint(ppList), lAst)
        }
      case Nil => (ProgramPoint(ppList), lAst)
    }
  }

}