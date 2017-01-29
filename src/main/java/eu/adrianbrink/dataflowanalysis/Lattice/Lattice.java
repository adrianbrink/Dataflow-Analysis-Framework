package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.*;

/**
 * Created by sly on 28/01/2017.
 */
// TODO: Ideally I would create an interface for the type of operations that LatticeElement needs to support and then the client code can decide what representation it wants to use.
// TODO: Make the lattice generic over the latticeValues and programParameters. In the case of availableExpressions they might be the same. Generic over A and B
// NOTE: This will not work for constant propagation analysis since the bitsets would be massive. For constant analysis it makes much more sence to keep track of the actual values.
public class Lattice {
    private List<Map<String, BitSet>> values;
    // Bottom, -, +, Top
    // this maps directly to the BitSet, if the bit at 0 is set then the variable is Bottom
    private List<String> latticeValues;
    // x and y
    private List<String> programParameters;

    // lattice values are the hasse diagram and programParameters are the actual assignments from the program
    public Lattice(int numberOfCFGNodes, List<String> latticeValues, List<String> programParameters) {
        this.latticeValues = latticeValues;
        this.programParameters = programParameters;
        this.values = new ArrayList<Map<String, BitSet>>(numberOfCFGNodes);
        for (int i = 0; i < numberOfCFGNodes; i++) {
            Map<String, BitSet> newLatticeRow = new HashMap<>();
            for (String variable : programParameters) {
                newLatticeRow.put(variable, new BitSet(latticeValues.size()));
            }
            this.values.add(i, newLatticeRow);
        }
    }

    private Lattice(List<Map<String, BitSet>> values, List<String> latticeValues, List<String> programParameters) {
        this.values = values;
        this.latticeValues = latticeValues;
        this.programParameters = programParameters;
    }

    public Map<String, BitSet> getLatticeElement(int index) {
        return this.values.get(index);
    }

    public void updateLatticeElement(Map<String, BitSet> element, int index) {
        this.values.set(index, element);
    }

    public void setAll(String value) {
        int index = this.latticeValues.indexOf(value);
        for (Map<String, BitSet> arrayListEntry : values) {
            for (Map.Entry<String, BitSet> entry : arrayListEntry.entrySet()) {
                entry.getValue().set(index, true);
            }
        }
    }

    public int getLatticeValue(String value) {
        int index = this.latticeValues.indexOf(value);
        return index;
    }

    public Lattice deepCopy() {
        List<String> latticeValues = new ArrayList<>();
        for (String value : this.latticeValues) {
            latticeValues.add(value);
        }

        List<String> programParameters = new ArrayList<>();
        for (String parameter : this.programParameters) {
            programParameters.add(parameter);
        }

        List<Map<String, BitSet>> values = new ArrayList<>();
        for (Map<String, BitSet> value : this.values) {
            Map<String, BitSet> clonedLatticeRow = new HashMap<>();
            for (Map.Entry<String, BitSet> entry : value.entrySet()) {
                String clonedKey = entry.getKey();
                // clone() returns a new instance of a BitSet with the exact same bits set
                BitSet clonedBitSet = (BitSet) entry.getValue().clone();
                clonedLatticeRow.put(clonedKey, clonedBitSet);
            }
            values.add(clonedLatticeRow);
        }

        Lattice deepCopy = new Lattice(values, latticeValues, programParameters);

        return deepCopy;
    }

    // returns the size of the underlying List of values which is equal to the number of CFGNodes that was used when constructing the lattice.
    public int getSize() {
        int size = this.values.size();
        return size;
    }
}
