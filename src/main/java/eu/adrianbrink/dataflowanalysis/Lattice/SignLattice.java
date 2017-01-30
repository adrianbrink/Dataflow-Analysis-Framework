package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sly on 30/01/2017.
 */
public class SignLattice extends FiniteWidthLattice {
    private BitSet bottom;

    public SignLattice(Set<String> programParameters) {
        super(programParameters, 2);

        this.bottom = new BitSet(2);
        this.bottom.set(0, false);
        this.bottom.set(0, false);
    }

    public SignLattice(Map<String, BitSet> lattice) {
        super(lattice);
    }

    @Override
    public void initialise() {
        for (Object latticeForParameter : this.getLattice().entrySet()) {
            Map.Entry<String, BitSet> entry = (Map.Entry) latticeForParameter;
            entry.getValue().and(this.bottom);
        }
    }

    @Override
    public ILattice join(ILattice that) {
        return null;
    }

    @Override
    public ILattice meet(ILattice that) {
        return null;
    }

    // this cast might seem unsafe, however I should only be calling this function from Sign.java.
    @Override
    public ILattice deepCopy() {
        SignLattice oldSignLattice = (SignLattice) this.getLattice();
        Map<String, BitSet> oldLatticeValues = oldSignLattice.getLattice();
        Map<String, BitSet> newLatticeValues = new HashMap<>();
        for (Map.Entry entry : oldLatticeValues.entrySet()) {
            String oldKey = (String) entry.getKey();
            BitSet oldBitSet = (BitSet) entry.getValue();
            BitSet newBitSet = (BitSet) oldBitSet.clone();
            newLatticeValues.put(oldKey, newBitSet);
        }
        SignLattice newSignLattice = new SignLattice(newLatticeValues);
        return newSignLattice;
    }
}
