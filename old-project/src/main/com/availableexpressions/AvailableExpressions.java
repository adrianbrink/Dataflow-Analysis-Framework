package main.com.availableexpressions;

import main.com.parser.Addition;
import main.com.parser.Parser;
import main.com.parser.Statement;

/**
 * Created by adrianbrink on 23/11/2016.
 */
public class AvailableExpressions {
    public static void main(String[] args) {
        System.out.println("test");
        Addition test = null;

        Statement s = Parser.parse("if(x<5 || !z=2) {x:=4 ; y:=x+1} else {x:=3}; skip; if (x=1) {x:=4} else {x:=2}");
        Statement s1 =  Parser.parse("skip");
        Statement s2 =  Parser.parse("x:=a ; y:=b");
        Statement s3 = Parser.parse("while(tt) {x:=x+-1 ; output(x*y)}");

     // Statement s = Parser.parseFile("C:\\Code\\fileToParse.txt");
     /*   Statement ss = Parser.parse("var x,y,z,a,b;\n" +
                "z = a+b;\n" +
                "y = a*b;\n" +
                "while (y > a+b) {\n" +
                "a = a+1;\n" +
                "x = a+b;\n" +
                "}\n");
*/
    }
}
