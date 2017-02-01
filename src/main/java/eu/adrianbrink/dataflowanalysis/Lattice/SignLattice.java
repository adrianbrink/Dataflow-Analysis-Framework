package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sly on 30/01/2017.
 */
public class SignLattice implements ILattice {
    private Map<String, LatticeElement<BitSet>> lattice;

    public SignLattice(Set<String> programParameters, LatticeElement<BitSet> initialElement) {
        Map<String, LatticeElement<BitSet>> lattice = new HashMap<>();
        BitSet oldBitSet = initialElement.getElement();
        for (String parameter : programParameters) {
            BitSet newBitSet = (BitSet) oldBitSet.clone();
            LatticeElement<BitSet> latticeElement = new LatticeElement<>(newBitSet);
            lattice.put(parameter, latticeElement);
        }
        this.lattice = lattice;
    }

    // bitwise or
    @Override
    public LatticeElement join(LatticeElement one, LatticeElement two) {
        BitSet firstLatticeElement = (BitSet) one.getElement();
        BitSet secondLatticeElement = (BitSet) two.getElement();
        BitSet newBitSet = (BitSet) firstLatticeElement.clone();
        newBitSet.or(secondLatticeElement);
        LatticeElement<BitSet> newLatticeElement = new LatticeElement<>(newBitSet);
        return newLatticeElement;
    }

    // bitwise and
    @Override
    public LatticeElement meet(LatticeElement one, LatticeElement two) {
        BitSet firstLatticeElement = (BitSet) one.getElement();
        BitSet secondLatticeElement = (BitSet) two.getElement();
        BitSet newBitSet = (BitSet) firstLatticeElement.clone();
        newBitSet.and(secondLatticeElement);
        LatticeElement<BitSet> newLatticeElement = new LatticeElement<>(newBitSet);
        return newLatticeElement;
    }

    public Map<String, LatticeElement<BitSet>> getLattice() {
        return this.lattice;
    }
}
