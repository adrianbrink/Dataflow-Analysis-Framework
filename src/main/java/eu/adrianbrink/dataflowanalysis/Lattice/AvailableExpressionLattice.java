package eu.adrianbrink.dataflowanalysis.Lattice;

import eu.adrianbrink.dataflowanalysis.utils.SetUtil;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Collections;
import java.util.Set;

/**
 * Lattice representation for Available Expressions analysis.
 */

@Immutable
public class AvailableExpressionLattice implements ILattice<AvailableExpressionLattice> {
    public final Set<String> availableExpr;

    public AvailableExpressionLattice(Set<String> availableExpr) {
        //Set<String> tmp = new TreeSet<>();
        //tmp.addAll(availableExpr);
        this.availableExpr = Collections.unmodifiableSet(availableExpr);
    }

    @Override
    public AvailableExpressionLattice join(AvailableExpressionLattice that) {
        return new AvailableExpressionLattice(SetUtil.filter(availableExpr,  s -> that.availableExpr.contains(s)));
    }

    @Override
    public boolean isEquals(AvailableExpressionLattice that) {
        return  availableExpr.size() == that.availableExpr.size() &&
                availableExpr.stream().allMatch(that.availableExpr::contains);

    }

}
