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
public interface ILattice {

    public LatticeElement meet(LatticeElement one, LatticeElement two);

    public LatticeElement join(LatticeElement one, LatticeElement two);
}
