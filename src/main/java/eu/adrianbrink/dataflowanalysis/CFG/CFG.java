package eu.adrianbrink.dataflowanalysis.CFG;

import eu.adrianbrink.parser.Statement;

import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
public class CFG {
    private CFGNode entryNode;

    public CFG(List<Statement> statements) {
        this.entryNode = new CFGNode(null, null);
        CFG.constructCFG(entryNode, statements);
    }

    public CFGNode getEntryNode() {
        return this.entryNode;
    }

    private static void constructCFG(CFGNode entryNode, List<Statement> statements) {
        // this is to keep track of the latest node
        // this assignment is dirty, since if it isn't initialised due to the first if statement then something is horribly broken
        CFGNode currentNode = new CFGNode(null, null);
        System.out.println(statements.get(0));
        System.out.println(statements.get(1));
        System.out.println(statements.get(2));

        for (int i = 0; i < statements.size(); i++) {
            System.out.println(i);
            if (i == 0) {
                // first real statement, append it to the entryNode
                CFGNode nextNode = new CFGNode(statements.get(i), null);
                entryNode.setNext(nextNode);
                currentNode = nextNode;
                continue;
            } else if (i == statements.size()-1) {
                // last statement and hence the next node is the exit node
                CFGNode exitNode = new CFGNode(null, null);
                CFGNode nextNode = new CFGNode(statements.get(i), exitNode);
                currentNode.setNext(nextNode);
                return;
            } else {
                CFGNode nextNode = new CFGNode(statements.get(i), null);
                currentNode.setNext(nextNode);
                currentNode = currentNode.getNext();
                continue;
            }
        }

//        for (Statement statement : statements) {
//            // first real statement, append it to the entryNode
//            if (statements.indexOf(statement) == 0) {
//                CFGNode nextNode = new CFGNode(statement, null);
//                entryNode.setNext(nextNode);
//                currentNode = nextNode;
//                continue;
//            } else if (statements.indexOf(statement) == statements.size()) {
//                // last statement and hence the next node is the exit node
//                CFGNode nextNode = new CFGNode(statement, null);
//                CFGNode exitNode = new CFGNode(null, null);
//                nextNode.setNext(exitNode);
//                return;
//            } else {
//                CFGNode nextNode = new CFGNode(statement, null);
//                currentNode.setNext(nextNode);
//                continue;
//            }
//        }
    }
}
