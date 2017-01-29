package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;

/**
 * Created by sly on 28/01/2017.
 */
// TODO: This is a HashMap that links Variable names or Assignment strings to BitSets that represent all possible values that that variable could be in.
public class LatticeElement {
    private BitSet bits;

    public LatticeElement() {
        this.bits = new BitSet();
    }

    public LatticeElement set(int bitIndex, boolean value) {
        this.bits.set(bitIndex, value);
        return this;
    }
}
