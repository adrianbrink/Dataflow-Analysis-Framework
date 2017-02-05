package eu.adrianbrink.dataflowanalysis.Framework;

import com.sun.xml.internal.ws.assembler.jaxws.AddressingTubeFactory;
import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.LivenessLattice;
import eu.adrianbrink.parser.*;
import eu.adrianbrink.parser.Number;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by antreas on 2/5/17.
 */
public class LivenessAnalysis implements IAnalysisFramework<LivenessLattice> {

    @Override
    public boolean isBackward() {
        return true;
    }

    @Override
    public Function<LivenessLattice, LivenessLattice> transferFunction(CFGNode cfgNode) {
        return  l -> new LivenessLattice(helper(cfgNode.getStatementOrExpression(), l));
    }

    private static Set<String> helper(AST ast, LivenessLattice lattice ) {
        Set<String> res = new HashSet();
        if (ast instanceof Expression) {
            if (ast instanceof Variable) {
                res.add(((Variable) ast).id);
            } else if (ast instanceof Addition) {
                Addition add = (Addition) ast;
                res.addAll(helper(add.e1, lattice));
                res.addAll(helper(add.e2, lattice));
            } else if (ast instanceof Multiplication) {
                Multiplication mul = (Multiplication) ast;
                res.addAll(helper(mul.e1, lattice));
                res.addAll(helper(mul.e2, lattice));
            } else if (ast instanceof Conjunction ) {
                Conjunction con = (Conjunction) ast;
                res.addAll(helper(con.b1, lattice));
                res.addAll(helper(con.b2, lattice));
            } else if (ast instanceof  Disjunction) {
                Disjunction dis = (Disjunction) ast;
                res.addAll(helper(dis.b1, lattice));
                res.addAll(helper(dis.b2, lattice));

            } else if (ast instanceof  Equality) {
                Equality eq = (Equality) ast;
                res.addAll(helper(eq.e1, lattice));
                res.addAll(helper(eq.e2, lattice));

            } else if (ast instanceof LessThan) {
                LessThan lt = (LessThan) ast;
                res.addAll(helper(lt.e1, lattice));
                res.addAll(helper(lt.e2, lattice));
            } else if (ast instanceof Negation) {
                Negation neg = (Negation) ast;
                res.addAll(helper(neg.b, lattice));
            }
        } else if (ast instanceof Statement) {
            if (ast instanceof If) {
                return helper(((If) ast).b, lattice);
            } else if (ast instanceof While) {
                return helper(((While) ast).b, lattice);
            } else if (ast instanceof Assignment) {
                return helper(((Assignment) ast).e, lattice);
            } else if (ast instanceof Output) {
                return helper(((Output) ast).e, lattice);
            }
        }
        res.addAll(lattice.elements);
        return res;
    }
    @Override
    public LivenessLattice getInitialLattice(CFG cfg) {
        return new LivenessLattice(Collections.emptySet());
    }
}
