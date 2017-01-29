package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.dataflowanalysis.Analysis.NaiveAnalysis;
import eu.adrianbrink.dataflowanalysis.Analysis.SignAnalysis;
import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.utils.ParserHelper;
import eu.adrianbrink.parser.Statement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
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
        //bitSetTest();

        File exampleProgram = new File(System.getProperty("user.dir") + "/examples" + "/simple_while.txt");
        List<Statement> statementList = ParserHelper.parse(exampleProgram);
        System.out.println(" xx ");
        for (Statement s: statementList) {
            System.out.println(s.toString());
        }
        List<CFGNode> cfgNodes = new ArrayList<>();
        CFG cfg = new CFG(cfgNodes, statementList);
        System.out.println("xxx");

        SignAnalysis signAnalysis = new SignAnalysis();
        signAnalysis.generateLattice(cfg);
        System.out.println("xxx");

        NaiveAnalysis naiveAnalysis = new NaiveAnalysis(cfg, signAnalysis);
        naiveAnalysis.run();
        System.out.println("xxx");

//        List<Statement> statementList2 = ParserHelper.parse("x := 1; y := 10; z := 100");
//        for (Statement s: statementList2) {
//            System.out.println(s.toString());
//        }
//        CFG cfg2 = new CFG(statementList2);



        System.out.println( "Hello World!" );
    }

    private static void bitSetTest() {
        BitSet bits1 = new BitSet(7);
        BitSet bits2 = new BitSet(7);

        // set some bits
//        for(int i = 0; i < 7; i++) {
//            if((i % 2) == 0) bits1.set(i);
//            if((i % 3) != 0) bits2.set(i);
//        }
        bits1.set(0);
        bits1.set(1);
        bits1.set(2);
        bits2.set(0);

        System.out.println("BitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println("\nBitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();

        //And
        bits1.and(bits2);

        System.out.println("b1 = b1 AND b2\nBitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println();
        System.out.println("BitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();
    }
}

/*
x := 0; y := 1
if (x + 10 < 20){ x := x * 2 } else { skip }
z := 2
while (x < 3){ x := x + 1 }
 */