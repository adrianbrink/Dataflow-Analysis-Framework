package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.utils.ParserHelper;
import eu.adrianbrink.parser.Statement;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
// TODO: The parser can't parse programs with an ending ; . This should work and should be fixed.
public class Main
{
    public static void main( String[] args ) throws IOException
    {
        File exampleProgram = new File(System.getProperty("user.dir") + "/examples" + "/simple_while.txt");
        List<Statement> statementList1 = ParserHelper.parse(exampleProgram);
        System.out.println(" xx ");
        for (Statement s: statementList1) {
            System.out.println(s.toString());
        }

        CFG cfg1 = new CFG(statementList1);
        System.out.println("xxx");
//        List<Statement> statementList2 = ParserHelper.parse("x := 1; y := 10; z := 100");
//        for (Statement s: statementList2) {
//            System.out.println(s.toString());
//        }
//        CFG cfg2 = new CFG(statementList2);



        System.out.println( "Hello World!" );
    }
}

/*
x := 0; y := 1
while (x < 3){ x := x + 1 }
if (x + 10 < 20){ x := x * 2 } else { skip }
 */