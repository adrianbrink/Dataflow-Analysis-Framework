package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
public class SignLattice implements ILattice {
    private Map<String, LatticeElement<BitSet>> lattice;
    private Set<String> programParameters;
    private LatticeElement<BitSet> initialElement;

    public SignLattice(Set<String> programParameters, LatticeElement<BitSet> initialElement) {
        this.programParameters = programParameters;
        this.initialElement = initialElement;

        Map<String, LatticeElement<BitSet>> lattice = new HashMap<>();
        BitSet oldBitSet = initialElement.getElement();
        for (String parameter : programParameters) {
            BitSet newBitSet = (BitSet) oldBitSet.clone();
            LatticeElement<BitSet> latticeElement = new LatticeElement<>(newBitSet);
            lattice.put(parameter, latticeElement);
        }
        this.lattice = lattice;
    }

    @Override
    public ILattice deepCopy() {
        SignLattice newLattice = new SignLattice(this.programParameters, this.initialElement);
        Map<String, LatticeElement<BitSet>> newMap = new HashMap<>();
        for (Map.Entry<String, LatticeElement<BitSet>> entry : this.lattice.entrySet()) {
            String key = entry.getKey();
            LatticeElement<BitSet> value = entry.getValue();
            newMap.put(key, value);
        }
        newLattice.lattice = newMap;
        return newLattice;
    }

    @Override
    public ILattice newLattice() {
        ILattice lattice = new SignLattice(this.programParameters, this.initialElement);
        return lattice;
    }

    // bitwise or
    @Override
    public LatticeElement<BitSet> join(LatticeElement one, LatticeElement two) {
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

    @Override
    public LatticeElement<BitSet> getLatticeElement(Object key) {
        String keyString = (String) key;
        LatticeElement<BitSet> element = this.lattice.get(keyString);
        return element;
    }

    @Override
    public void setLatticeElement(Object key, LatticeElement element) {
        String keyString = (String) key;
        this.lattice.put(keyString, element);
    }
}
