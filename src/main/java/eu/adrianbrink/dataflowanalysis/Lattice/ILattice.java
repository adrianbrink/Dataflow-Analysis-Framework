package eu.adrianbrink.dataflowanalysis.Lattice;
/*
This interface defines what operations a lattice needs to support.
// TODO: Implement 2 different lattices: one for infinite lattices (constant propagation) and one for finite lattices (sign analysis).
// QUESTION: Does an environment lattice have the same operations as a lattice over values
 */

// TODO: This interface needs to be generic over the lattice elements. In most cases I will pass it a BitSet but in other cases the elements will be integers (constant propagation)

/**
 * Created by sly on 29/01/2017.
 */
public interface ILattice<A> {
    // meet and join combine 2 lattices at a confluence point, when 2 branches meet.
    // they also take the lattices produced by gen() and kill() and combine them
    // greatest lower bound
    public ILattice meet(ILattice that);

    // least upper bound
    public ILattice join(ILattice that);

    //
    public ILattice initialise(A element);
}
