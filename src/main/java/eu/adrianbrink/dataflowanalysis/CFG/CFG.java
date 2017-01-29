package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sly on 26/01/2017.
 */
// TODO: Ideally the analysis class should not never interact with CFGNodes directly but only through the CFG class. I should try and hide that implementation from the client code.
public class CFG {
    private List<CFGNode> cfgNodes;

    public CFG(List<CFGNode> cfgNodes, List<Statement> statements) {
        this.cfgNodes = cfgNodes;
        CFG.constructCFG(cfgNodes, statements);
    }

    public List<CFGNode> getCfgNodes() {
        return this.cfgNodes;
    }

    public Set<Assignment> getAssignments() {
        Set<Assignment> assignmentsSet = new HashSet<>();
        for (CFGNode node : this.cfgNodes) {
            AST assigment = node.getStatementOrExpression();
            if (assigment instanceof Assignment) {
                assignmentsSet.add((Assignment) assigment);
            } else {
                continue;
            }
        }
        return assignmentsSet;
    }

    private static void constructCFG(List<CFGNode> cfgNodes, List<Statement> statements) {
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
        return null;
    }
}