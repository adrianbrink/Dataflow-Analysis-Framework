package main.com.availableexpressions.sensitive

import main.com.parser._

trait ProgramPoint
case class EntryN(var next: ProgramPoint = null, var list: List[Expression] = List.empty) extends ProgramPoint
case class ExitN(prv: ProgramPoint, var list: List[Expression] = List.empty) extends ProgramPoint
case class CondW(prv: ProgramPoint, cond: BoolExpression, var tr: ProgramPoint = null, var next: ProgramPoint = null,
                 var list: List[Expression] = List.empty) extends ProgramPoint
case class StatementN(prv: ProgramPoint, stat: Statement, var next: ProgramPoint = null,
                      var list: List[Expression] = List.empty) extends ProgramPoint
case class AssignmentN(prv: ProgramPoint, asgn: Assignment, var next: ProgramPoint = null,
                       var list: List[Expression] = List.empty) extends ProgramPoint
case class CondIf(prv: ProgramPoint, cond: BoolExpression, var tr: ProgramPoint = null, var fl: ProgramPoint = null,
                  var list: List[Expression] = List.empty) extends ProgramPoint
case class ConfluenceIf(iff: ProgramPoint, ls: ProgramPoint, var next: ProgramPoint = null,
                        var list: List[Expression] = List.empty) extends ProgramPoint

object ProgramPoint {

  def stat2pp(list: List[Statement]): ProgramPoint = {
    val entryN = EntryN()
    stat2pp(entryN, list)
    entryN
  }

  private def stat2pp(prev: ProgramPoint, list: List[Statement]): ExitN = {
    list match {
      case Nil => {
        val exit = ExitN(prev)
        prev match {
          case wh: CondW => wh.next = exit
          case cI: ConfluenceIf => cI.next = exit
          case st: StatementN => st.next = exit
        }
        exit
      }
      case h :: t => stat2pp(makeProgramPointFor(prev, h, false), t)
    }
  }

  private def makeProgramPointFor(prev: ProgramPoint, stat: Statement, iff: Boolean): ProgramPoint = {
    stat match {
      case seq: Sequence => {
        val s1 = makeProgramPointFor(prev, seq.s1, iff)
        makeProgramPointFor(s1, seq.s2, iff)
      }
      case wh: While => {
        val cw = CondW(prev, wh.b)
        addNext(prev, cw, iff)
        val last = makeProgramPointFor(cw, wh.s, true)
        addNext(last, cw, iff)
      }
      case i_f: If => {
        val ci = addNext(prev, CondIf(prev, i_f.b), iff)
        val s1 = makeProgramPointFor(ci, i_f.s1, true)
        val s2 = makeProgramPointFor(ci, i_f.s2, false)
        val coI = ConfluenceIf(s1, s2)
        addNext(s1, coI, iff)
        addNext(s2, coI, iff)
      }
      case asg: Assignment => addNext(prev, AssignmentN(prev, asg), iff)
      case st: Statement => addNext(prev, StatementN(prev, st), iff)
    }
  }

  private def addNext(prev: ProgramPoint, next: ProgramPoint, iff: Boolean): ProgramPoint = {
    prev match {
      case stm: StatementN => stm.next = next
      case asg: AssignmentN => asg.next = next
      case coI: ConfluenceIf => coI.next = next
      case cw: CondW if iff => cw.tr = next
      case cw: CondW  => cw.next = next
      case ci: CondIf if iff => ci.tr = next
      case ci: CondIf => ci.fl = next
      case e: EntryN => e.next = next
    }
    next
  }
}
