package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.utils.ParserHelper;
import eu.adrianbrink.parser.Statement;

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
//        File exampleProgram = new File(System.getProperty("user.dir") + "/examples" + "/simple_while.txt");
//        List<Statement> statementList1 = ParserHelper.parse(exampleProgram);
//        for (Statement s: statementList1) {
//            System.out.println(s.toString());
//        }

        List<Statement> statementList2 = ParserHelper.parse("x := 1; y := 10; z := 100");
        CFG cfg = new CFG(statementList2);

        

        System.out.println( "Hello World!" );
    }
}
