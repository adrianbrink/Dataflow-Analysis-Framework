package eu.adrianbrink.dataflowanalysis.Lattice;

/*
When implementing your specific analysis you need to implement three functions:
- join()
    - this returns the least upper bound of two lattice elements
- meet()
    - this returns the greatest lower bound of two lattice elements
 */

/**
 * Created by sly on 29/01/2017.
 */
public interface ILattice<A> {

    // glb
    // A meet(A that);

    // lub
    A join(A that);
}
