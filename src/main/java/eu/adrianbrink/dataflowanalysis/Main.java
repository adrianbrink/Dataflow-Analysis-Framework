package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.dataflowanalysis.Analysis.Analysis;
import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.Engine.ChaoticEngine;
import eu.adrianbrink.dataflowanalysis.Engine.NaiveEngine;
import eu.adrianbrink.dataflowanalysis.Engine.WorklistEngine;
import eu.adrianbrink.dataflowanalysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Framework.LivenessAnalysis;
import eu.adrianbrink.dataflowanalysis.Framework.SignAnalysis;
import eu.adrianbrink.dataflowanalysis.utils.ParserHelper;
import eu.adrianbrink.parser.Statement;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class Main
{
    public static void main( String[] args ) throws IOException
    {

        File exampleProgram = new File(System.getProperty("user.dir") + "/examples" + "/simple_while.txt");
        List<Statement> statementList = ParserHelper.parse(exampleProgram);

        IAnalysisFramework framework = new SignAnalysis();
        System.out.println("+++++++++++++++++++++++++++++++++");
        CFG cfg = CFG.constructCFG(statementList);
        System.out.println("+++++++++++++++++++++++++++++++++");
        CFG.addTransferFunctions(cfg, framework);
        //ILattice lattice = new SignLattice(sign.getProgramParameters(cfg), sign.initialElement());
        System.out.println("+++++++++++++++++++++++++++++++++");
        CFG.initialiseCFGState(cfg, framework);
        System.out.println("+++++++++++++++++++++++++++++++++");
        Analysis analysis = new Analysis(new WorklistEngine(cfg, framework));
        System.out.println("+++++++++++++++++++++++++++++++++");
        analysis.run();
        System.out.println("+++++++++++++++++++++++++++++++++");
    }
}

/*
x := 0; y := 1
if (x + 10 < 20){ x := x * 2 } else { skip }
z := 2
while (x < 3){ x := x + 1 }
 */