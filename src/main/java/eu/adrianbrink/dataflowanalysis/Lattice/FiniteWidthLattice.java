package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sly on 28/01/2017.
 */
// TODO: Ideally I would create an interface for the type of operations that LatticeElement needs to support and then the client code can decide what representation it wants to use.
// TODO: Make the lattice generic over the latticeValues and programParameters. In the case of availableExpressions they might be the same. Generic over A and B
// NOTE: This will not work for constant propagation analysis since the bitsets would be massive. For constant analysis it makes much more sence to keep track of the actual values.
public abstract class FiniteWidthLattice implements ILattice {

    // this is the lattice for one program point, where A represents the elements of the domain, e.g.: all elements between top and bottom
    // this is my tuple of lattices per program point
    private Map<String, BitSet> lattice;

    public FiniteWidthLattice(Set<String> programParameters, int width) {
        Map<String, BitSet> newLattice = new HashMap<String, BitSet>(programParameters.size());
        for (String parameter : programParameters) {
            BitSet newBitSet = new BitSet(width);
            newLattice.put(parameter, newBitSet);
        }
        this.lattice = newLattice;
    }

    public FiniteWidthLattice(Map<String, BitSet> lattice) {
        this.lattice = lattice;
    }

    public Map<String, BitSet> getLattice() {
        return this.lattice;
    }

    @Override
    public Object getValueForParameter(Object parameter) {
        if (parameter instanceof String) {
            String variableName = (String) parameter;
            BitSet bitSet = this.lattice.get(variableName);
            return bitSet;
        }
        // this should never happen
        return null;
    }

    @Override
    public void setValueForParameter(Object parameter, Object value) {
        String variableName = (String) parameter;
        BitSet bitSet = (BitSet) value;
        this.lattice.put(variableName, bitSet);
    }
}