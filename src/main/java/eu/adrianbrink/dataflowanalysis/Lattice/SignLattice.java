package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;

/**
 * Created by Adrian Brink on 30/01/2017.
 */
// user implemented
public class SignLattice implements ILattice<SignLattice> {
    public BitSet element;

    @Override
    public SignLattice join(SignLattice that) {
        BitSet oldBitSet = this.element;
        BitSet newBitSet = (BitSet) oldBitSet.clone();
        newBitSet.or(that.element);
        SignLattice signLattice = new SignLattice();
        signLattice.element = newBitSet;
        return signLattice;
    }
}
