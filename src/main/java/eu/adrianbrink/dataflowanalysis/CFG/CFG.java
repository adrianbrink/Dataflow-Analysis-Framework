package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
public class CFG {
    private CFGNode entryNode;

    public CFG(List<Statement> statements) {
        this.entryNode = new CFGEntry();
        CFG.constructCFG(entryNode, statements);
    }

    public CFGNode getEntryNode() {
        return this.entryNode;
    }

    private static void constructCFG(CFGNode entryNode, List<Statement> statements) {
        // this is to keep track of the latest node
        // this assignment is dirty, since if it isn't initialised due to the first if statement then something is horribly broken

        CFGNode currentNode = entryNode;

        for (Statement statement : statements) {
            CFGNode newCFGNode = CFG.expandStatement(currentNode, statement);
            currentNode = newCFGNode;
        }

        // find the last node by walking along all the next pointers and then add the exitNode
//        CFGNode tmpNode = entryNode;
//        while (tmpNode.getNext() != null) {
//            tmpNode = (CFGNode) tmpNode.getNext();
//        }
//        List<CFGNode> exitNode = new ArrayList<CFGNode>();
//        exitNode.add(new CFGExit());
//        tmpNode.setNext(exitNode);
    }

    // takes the latest node that has been constructed and returns the last node that it created
    // TODO: expandStatement is misleading since it also deals with things that are expressions are sequences
    private static CFGNode expandStatement(CFGNode currentNode, Statement statement) {
        if (statement instanceof Assignment || statement instanceof Skip || statement instanceof Output) {
            CFGStatement newNode = new CFGStatement(statement);
            List<CFGNode> newNodeList = new ArrayList<CFGNode>();
            newNodeList.add(newNode);
            currentNode.setNext(newNodeList);
            return newNode;
        } else if (statement instanceof Sequence) {
            CFGNode leftNode = CFG.expandStatement(currentNode, ((Sequence) statement).s1);
            CFGNode rightNode = CFG.expandStatement(leftNode, ((Sequence) statement).s2);
            return rightNode;
        } else if (statement instanceof If) {
            CFGBranch expressionNode = new CFGBranch(((If) statement).b);
            List<CFGNode> newNodeList = new ArrayList<CFGNode>();
            newNodeList.add(expressionNode);
            currentNode.setNext(newNodeList);
            CFGNode trueNode = CFG.expandStatement(expressionNode, ((If) statement).s1);
            CFGNode falseNode = CFG.expandStatement(expressionNode, ((If) statement).s2);
            CFGConfluence confluenceNode = new CFGConfluence();
            List<CFGNode> newConfluenceNodeList = new ArrayList<CFGNode>();
            newConfluenceNodeList.add(confluenceNode);
            trueNode.setNext(newConfluenceNodeList);
            falseNode.setNext(newConfluenceNodeList);
            return confluenceNode;
        } else if (statement instanceof While) {
            return null;
        }
        return null;
    }
//
//        CFGNode exitNode = new CFGNode(null);
//        currentNode.setNext(exitNode);

//        for (int i = 0; i < statements.size(); i++) {
//            if (i == 0) {
//                // first real statement, append it to the entryNode
//                CFGNode nextNode = new CFGNode(statements.get(i), null);
//                entryNode.setNext(nextNode);
//                currentNode = nextNode;
//                continue;
//            } else if (i == statements.size() - 1) {
//                // last statement and hence the next node is the exit node
//                CFGNode exitNode = new CFGNode(null, null);
//                CFGNode nextNode = new CFGNode(statements.get(i), exitNode);
//                currentNode.setNext(nextNode);
//                return;
//            } else {
//                CFGNode nextNode = new CFGNode(statements.get(i), null);
//                currentNode.setNext(nextNode);
//                currentNode = currentNode.getNext();
//                continue;
//            }
//        }
//    }

//    private static void expandStatement(List<Statement> statements, CFGNode entryNode) {
//        Statement currentStatement = statements.remove(0);
//        CFG.expandStatement(statements, entryNode, currentStatement);
//    }
//
//    private static void expandStatement(List<Statement> statements, CFGNode currentNode, Statement currentStatement) {
//        // base case
//        if (statements.size() == 0) {
//            return;
//        }
//
//        if (currentStatement instanceof Assignment || currentStatement instanceof Skip || currentStatement instanceof Output) {
//            // simple assignment node, can't recourse downwards and hence is the base case
//            Statement nextStatement = statements.remove(0);
//            CFGNode newCFGNode = new CFGNode(currentStatement);
//            currentNode.setNext(newCFGNode);
//            CFG.expandStatement(statements, newCFGNode, nextStatement);
//        }
//        } else if (statement instanceof Sequence) {
//            // sequence-statement
//
//            return new CFGNode(statement);
//        } else if (statement instanceof While) {
//            // while-statement
//            return new CFGNode(statement);
//        } else {
//            // if-statement
//            return new CFGNode(statement);
//        }
//    }
}
