package eu.adrianbrink.dataflowanalysis.Lattice;

/**
 * Created by sly on 01/02/2017.
 */
public class LatticeElement<A> {
    private A element;
    private String parameter;

    public LatticeElement(A element) {
        this.element = element;
    }

    public A getElement() {
        return this.element;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
