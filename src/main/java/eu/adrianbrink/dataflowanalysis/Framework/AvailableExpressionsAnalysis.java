package eu.adrianbrink.dataflowanalysis.Framework;

import eu.adrianbrink.dataflowanalysis.CFG.CFG;
import eu.adrianbrink.dataflowanalysis.CFG.CFGNode;
import eu.adrianbrink.dataflowanalysis.Lattice.AvailableExpressionLattice;
import eu.adrianbrink.dataflowanalysis.utils.SetUtil;
import eu.adrianbrink.parser.*;
import eu.adrianbrink.parser.Number;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * Created by abit on 3/7/17.
 */
public class AvailableExpressionsAnalysis implements IAnalysisFramework<AvailableExpressionLattice> {

    public AvailableExpressionLattice bottom;
    final private static boolean isBackwards = false;
    private final HashMap<String, Set<String>> cache = new HashMap<>();
    private static final AvailableExpressionLattice empty = new AvailableExpressionLattice(Collections.EMPTY_SET);
    @Override
    public AvailableExpressionLattice getInitialLattice(CFG cfg) {
        if (bottom == null)
            bottom = new AvailableExpressionLattice(extractExpressionsFrom(cfg));
        return bottom;
    }

    private Set<String> extractExpressionsFrom(CFG cfg) {
        AST currentAST;
        TreeSet<String> set = new TreeSet<>();
        for (CFGNode cfgNode : cfg.getCFGNodes()) {
            if ((currentAST = cfgNode.getStatementOrExpression()) != null)
                set.addAll(extractExpressionsFrom(currentAST));
        }
        return set;
    }

    private Set<String> extractExpressionsFrom(AST ast) {
        Set<String> result = new TreeSet<>();
       if (ast instanceof Variable || ast instanceof Number || ast instanceof BoolConstant)
           return result;
       else if (ast instanceof Statement)
           return extractExpressionsFromStatement((Statement)ast, result );
       else
           return extractSubFromExpression((Expression)ast, result);
    }

    @Override
    public Function<AvailableExpressionLattice, AvailableExpressionLattice> transferFunction(CFGNode cfgNode) {
        if (cfgNode.isEntryPoint())
            return in -> empty;
        if (cfgNode.isExitPoint())
            return Function.identity();
        AST currentAST = cfgNode.getStatementOrExpression();
        if (currentAST instanceof Assignment) {
           String var = ((Assignment) currentAST).x;
           return in -> {
               Set<String> availableExpressions = getSubExprIfCached(currentAST);
               availableExpressions.addAll(in.availableExpr);
               Set<String> out = SetUtil.filter(availableExpressions, s -> !s.contains(var));
               return new AvailableExpressionLattice(out);
           };
        }
        return  in -> {
            Set<String> availableExpressions = getSubExprIfCached(currentAST);
            availableExpressions.addAll(in.availableExpr);
            return new AvailableExpressionLattice(availableExpressions);
        };
    }

    private Set<String> getSubExprIfCached(AST ast) {
            return extractExpressionsFrom(ast);
    }

    private Set<String> extractExpressionsFromStatement(Statement statement, Set<String> result) {
        if (statement instanceof Skip)
            return result;
        if (statement instanceof Sequence) {
            return this.<Sequence>cast(statement,
                    st -> extractExpressionsFromStatement(st.s2, extractExpressionsFromStatement(st.s1, result)));
        } else if (statement instanceof Assignment) {
            return this.<Assignment>cast(statement, as -> extractSubFromExpression(as.e, result));
        } else if (statement instanceof While) {
            return this.<While>cast(statement,
                    w -> extractSubFromExpression(w.b, extractExpressionsFromStatement(w.s,result)));
        } else if (statement instanceof If) {
            return this.<If>cast(statement, i -> extractExpressionsFromStatement(i.s2,
                    extractExpressionsFromStatement(i.s1, extractSubFromExpression(i.b, result))));
        } else if (statement instanceof Output) {
            return this.<Output>cast(statement, o -> extractSubFromExpression(o.e, result));
        }
        return handleError(statement);
    }

    private Set<String> extractSubFromExpression(Expression expression, Set<String> result) {
        if (expression instanceof Variable || expression instanceof Number || expression instanceof BoolConstant)
            return result;
        result.add(expression.toString());
        if (expression instanceof LessThan) {
            return this.<LessThan>cast(expression,
                    lt -> extractSubFromExpression(lt.e2, extractSubFromExpression(lt.e1, result)));
        } else if (expression instanceof Conjunction) {
            return this.<Conjunction>cast(expression,
                    cn -> extractSubFromExpression(cn.b2, extractSubFromExpression(cn.b1, result)));
        } else if (expression instanceof Disjunction) {
            return this.<Disjunction>cast(expression,
                    dj -> extractSubFromExpression(dj.b2, extractSubFromExpression(dj.b1, result)));
        } else if (expression instanceof Equality) {
            return this.<Equality>cast(expression,
                    eq -> extractSubFromExpression(eq.e2, extractSubFromExpression(eq.e1, result)));
        } else if (expression instanceof Negation) {
            return this.<Negation>cast(expression,
                    n -> extractSubFromExpression(n.b, result));
        } else if (expression instanceof Addition) {
            return this.<Addition>cast(expression,
                    ad -> extractSubFromExpression(ad.e2, extractSubFromExpression(ad.e1, result)));
        } else if (expression instanceof Multiplication) {
            return this.<Multiplication>cast(expression,
                    m -> extractSubFromExpression(m.e2, extractSubFromExpression(m.e1, result)));
        }
        return handleError(expression);
    }

    private static Set<String> handleError(AST ast) {
        throw new IllegalArgumentException("Unhandled case for type: " + ast.getClass().getName());
    }

    private static <T extends AST> Set<String> cast(AST ast, Function<T, Set<String>> f) {
        T t = (T) ast;
        return f.apply(t);
    }

    @Override
    public boolean isBackward() {
        return isBackwards;
    }
}
