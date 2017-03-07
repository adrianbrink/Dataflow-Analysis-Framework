package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by antreas on 3/7/17.
 */
public class AvailableExpressionLattice implements ILattice<AvailableExpressionLattice> {
    final Set<String> availableExpr;

    public AvailableExpressionLattice(Set<String> availableExpr) {
        this.availableExpr = availableExpr;
    }

    @Override
    public AvailableExpressionLattice join(AvailableExpressionLattice that) {
        return new AvailableExpressionLattice(filter(availableExpr, that.availableExpr::contains));
    }

    @Override
    public boolean isEquals(AvailableExpressionLattice that) {
        return  availableExpr.size() == that.availableExpr.size() &&
                availableExpr.stream().allMatch(that.availableExpr::contains);

    }

    public static<T> Set<T> filter(Set<T> set, Predicate<T> test) {
        return set.stream().filter(test).collect(Collectors.toSet());
    }
}
