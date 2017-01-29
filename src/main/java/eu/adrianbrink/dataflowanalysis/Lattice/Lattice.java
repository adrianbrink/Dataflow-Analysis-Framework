package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.*;

/**
 * Created by sly on 28/01/2017.
 */
// TODO: Ideally I would create an interface for the type of operations that LatticeElement needs to support and then the client code can decide what representation it wants to use.
// NOTE: This will not work for constant propagation analysis since the bitsets would be massive. For constant analysis it makes much more sence to keep track of the actual values.
public class Lattice {
    private ArrayList<Map<String, BitSet>> values;
    // Bottom, -, +, Top
    // this maps directly to the BitSet, if the bit at 0 is set then the variable is Bottom
    private List<String> latticeValues;
    // x and y
    private List<String> prorgramParameters;

    // lattice values are the hasse diagram and prorgramParameters are the actual assignments from the program
    public Lattice(int numberOfCFGNodes, List<String> latticeValues, List<String> prorgramParameters) {
        this.latticeValues = latticeValues;
        this.prorgramParameters = prorgramParameters;
        this.values = new ArrayList<Map<String, BitSet>>(numberOfCFGNodes);
        for (int i = 0; i < numberOfCFGNodes; i++) {
            Map<String, BitSet> newLatticeRow = new HashMap<>();
            for (String variable : prorgramParameters) {
                newLatticeRow.put(variable, new BitSet(latticeValues.size()));
            }
            this.values.add(i, newLatticeRow);
        }
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
}
