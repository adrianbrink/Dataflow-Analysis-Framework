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
public interface ILattice<A, B> {
    // meet and join combine 2 lattices at a confluence point, when 2 branches meet.
    // they also take the lattices produced by gen() and kill() and combine them
    // greatest lower bound
    // bitwise and
    public ILattice meet(ILattice that);

    // least upper bound
    // bitwise or
    public ILattice join(ILattice that);

    // this doesn't need to take an element, because the user implementation defines this method and initializes the lattice to the correct initial value
    public void initialise();

    public A getValueForParameter(B parameter);

    public void setValueForParameter(B parameter, A value);

    public ILattice deepCopy();
}

/*
A lattice is a bit vector who's length is equal to the set of elements between top and bottom.
Sign Analysis
 */