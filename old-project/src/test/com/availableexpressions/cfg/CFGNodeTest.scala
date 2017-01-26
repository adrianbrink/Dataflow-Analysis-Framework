package test.com.availableexpressions.cfg

import main.com.parser.Parser
import main.com.availableexpressions.cfg.CFG.toListStatements
import main.com.availableexpressions.cfg.ProgramPoint.stat2pp

object CFGNodeTest {
  def main(args: Array[String]): Unit = {
    //val ast = Parser.parse("if(x<5 || !z=2) {x:=4 ; y:=x+1} else {x:=3}; skip; if (x=1) {x:=4} else {x:=x+2}; while" +
      //"(x=0){x:=x+1}")
    val ast1 = Parser.parse("x:=5;while(x<5 || !z=2) {while(x<2) {x:=4; z:=6}}; skip; if (x=1) {x:=4} else {x:=x+2}")
    val list  = toListStatements(ast1)
    val node = stat2pp(list)
    val s = "dwe"
  }
}
