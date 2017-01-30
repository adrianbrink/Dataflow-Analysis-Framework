package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.dataflowanalysis.Analysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Analysis.Framework.Sign;
import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.SignLattice;
import eu.adrianbrink.dataflowanalysis.utils.ParserHelper;
import eu.adrianbrink.parser.Statement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        List<Statement> statementList = ParserHelper.parse(exampleProgram);
        System.out.println(" ============ ");
        for (Statement s: statementList) {
            System.out.println(s.toString());
        }

        ArrayList<CFGNode> cfgNodes = new ArrayList<>();
        CFG cfg = new CFG(cfgNodes, statementList);
        System.out.println(" ============ ");
        IAnalysisFramework signAnalysis = new Sign(cfg);
        System.out.println(" ============ ");
        ILattice signLattice = new SignLattice(signAnalysis.getProgramParameters());
        System.out.println(" ============ ");
    }
}

/*
x := 0; y := 1
if (x + 10 < 20){ x := x * 2 } else { skip }
z := 2
while (x < 3){ x := x + 1 }
 */