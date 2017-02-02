package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.dataflowanalysis.Framework.IAnalysisFramework;
import eu.adrianbrink.dataflowanalysis.Lattice.ILattice;
import eu.adrianbrink.dataflowanalysis.Lattice.LatticeElement;
import eu.adrianbrink.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by sly on 26/01/2017.
 */
public class CFG {
    private List<CFGNode> cfgNodes;

    public List<CFGNode> getCFGNodes() {
        return this.cfgNodes;
    }

    public static void addTransferFunctions(CFG cfg, IAnalysisFramework framework, ILattice lattice) {
        for (CFGNode node : cfg.getCFGNodes()) {
            Function<LatticeElement, LatticeElement> transferFunction = framework.transferFunction(node, lattice);
            node.setTransferFunction(transferFunction);
        }
    }

    public static CFG constructCFG(List<Statement> statements) {
        List<CFGNode> cfgNodes = new ArrayList<>();

        CFG cfg = new CFG();
        cfg.cfgNodes = cfgNodes;

        // create EntryNode
        CFGNode entryNode = new CFGNode(null);
        // add EntryNode to cfgNodes
        cfgNodes.add(entryNode);
        // create List that will be passed in as the previous nodes
        List<CFGNode> previousNodes = new ArrayList<>();
        previousNodes.add(entryNode);
        // go through all statements
        for (Statement statement : statements) {
            List<CFGNode> createdNodes = CFG.expandStatement(cfgNodes, previousNodes, statement);
            previousNodes = createdNodes;
        }

        CFGNode exitNode = new CFGNode(null);
        cfgNodes.add(exitNode);
        List<CFGNode> exitNodeList = new ArrayList<>();
        exitNodeList.add(exitNode);
        // set the previous nodes to point to the last node
        for (CFGNode node : previousNodes) {
            node.setNext(exitNodeList);
        }
        // set the last node to point to the previous node
        for (CFGNode node : exitNodeList) {
            node.setPrevious(previousNodes);
        }

        return cfg;
    }

    private static List<CFGNode> expandStatement(List<CFGNode> cfgNodes, List<CFGNode> previousNodes, AST statement) {
        if (statement instanceof Assignment || statement instanceof Skip || statement instanceof Output || statement instanceof Expression) {
            CFGNode newNode = new CFGNode(statement);
            cfgNodes.add(newNode);
            newNode.setPrevious(previousNodes);
            List<CFGNode> newNodeList = new ArrayList<>();
            newNodeList.add(newNode);
            for (CFGNode node : previousNodes) {
                node.setNext(newNodeList);
            }
            return newNodeList;
        } else if (statement instanceof Sequence) {
            List<CFGNode> firstNodes = CFG.expandStatement(cfgNodes, previousNodes, ((Sequence) statement).s1);
            List<CFGNode> secondNodes = CFG.expandStatement(cfgNodes, firstNodes, ((Sequence) statement).s2);
            return secondNodes;
        } else if (statement instanceof If) {
            List<CFGNode> ifNode = CFG.expandStatement(cfgNodes, previousNodes, ((If) statement).b);
            List<CFGNode> trueBranch = CFG.expandStatement(cfgNodes, ifNode, ((If) statement).s1);
            List<CFGNode> falseBranch = CFG.expandStatement(cfgNodes, ifNode, ((If) statement).s2);
            List<CFGNode> newNodes = new ArrayList<>();
            for (CFGNode node : trueBranch) {
                newNodes.add(node);
            }
            for (CFGNode node : falseBranch) {
                newNodes.add(node);
            }
            return newNodes;
        } else if (statement instanceof While) {
            List<CFGNode> whileNode = CFG.expandStatement(cfgNodes, previousNodes, ((While) statement).b);
            List<CFGNode> trueBranch = CFG.expandStatement(cfgNodes, whileNode, ((While) statement).s);
            // trueBranches next pointer is set here, because only in the case of while do I already know the next pointer before another expansion happens
            for (CFGNode node : trueBranch) {
                node.setNext(whileNode);
            }
            return whileNode;
        }
        // impossible to reach, since above all possible AST subclasses are handled
        return null;
    }
}