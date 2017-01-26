package test.com.availableexpressions.cfg

import main.com.availableexpressions.cfg.{OldCfg}
import main.com.parser.Parser
import main.com.availableexpressions.cfg.CFG._
object astToListTest {
  def main(args: Array[String]): Unit = {

    val ast = Parser.parse("z:=a+b;y:=a*b;while(a+b<y){a:=a+1;x:=a+b}")
    val lpp = OldCfg.astToList(ast)
    val fp = astToCFG(lpp)
    val a = ""
  }
}
