package test.com.availableexpressions.cfg

import main.com.parser.Parser
import main.com.availableexpressions.cfg.CFG._
/**
  * Created by antreas on 12/9/16.
  */
object toCFGTest {
  def main(args: Array[String]): Unit = {
    val ast = Parser.parse("x:=5; while(x<5 || !z=2) {while(x<2) {x:=4; z:=6}}; skip; if (x=1) {x:=4} else {x:=x+2}")

    val cfg = toListStatements(ast)
    val dm = ""
  }
}
