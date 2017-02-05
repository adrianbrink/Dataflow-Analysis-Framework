package eu.adrianbrink.dataflowanalysis.Lattice;

import eu.adrianbrink.parser.Expression;
import eu.adrianbrink.parser.Variable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by antreas on 2/5/17.
 */
public class LivenessLattice implements ILattice<LivenessLattice> {
    public Set<String> elements =  Collections.emptySet();

    public LivenessLattice(Set<String> elem) {
        elements = elem;
    }
    @Override
    public LivenessLattice join(LivenessLattice that) {
        Set<String> res = new HashSet<>();
        res.addAll(elements);
        res.addAll(that.elements);
        return new LivenessLattice(res);
    }

    @Override
    public boolean isEquals(LivenessLattice that) {
        return elements.equals(that.elements);
    }
}
