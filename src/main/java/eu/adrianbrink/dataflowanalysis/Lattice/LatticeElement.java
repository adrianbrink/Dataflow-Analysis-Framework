package eu.adrianbrink.dataflowanalysis.Lattice;

/**
 * Created by sly on 01/02/2017.
 */
public class LatticeElement<A> {
    private A element;

    public LatticeElement(A element) {
        this.element = element;
    }

    public A getElement() {
        return this.element;
    }
}
