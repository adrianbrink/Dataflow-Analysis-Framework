package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.List;

/**
 * Created by sly on 28/01/2017.
 */
// TODO: Ideally I would create an interface for the type of operations that LatticeElement needs to support and then the client code can decide what representation it wants to use.
// NOTE: This will not work for constant propagation analysis since the bitsets would be massive. For constant analysis it makes much more sence to keep track of the actual values.
public class Lattice {
    private LatticeElement[] values;
    private List<String> elementRepresentation;

    public Lattice(int numberOfCFGNodes, List<String> elementRepresentation) {
        this.values = new LatticeElement[numberOfCFGNodes];
        this.elementRepresentation = elementRepresentation;
        for (int i = 0; i < this.values.length; i++) {
            values[i] = new LatticeElement();
        }
    }

    public LatticeElement getLatticeElement(int index) {
        return this.values[index];
    }

    public void updateLatticeElement(LatticeElement element, int index) {
        this.values[index] = element;
    }

    public void setAll(int bitIndex, boolean value) {
        for (LatticeElement element : values) {
            element.set(bitIndex, value);
        }
    }

    public int getIndex(String element) {
        int index = elementRepresentation.indexOf(element);
        return index;
    }
}
