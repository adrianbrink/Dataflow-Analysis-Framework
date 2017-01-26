ProgramPoint
    - List<ProgramPoint> previous
    - List<ProgramPoint> next
    - Lattice in
    - Lattice out
    - TransferFunction transferFunction
    - join()
        - depends on may or must analysis and joins all the out values from previous nodes into one and updates in
        - for computing the least upper bound
    - meet()
        - for computing the greatest lower bound

CFG
    - reverse()
        - reverses all the edges for backward analysis

Lattice
    - Set<*something that is represents the different values depending on the analysis*>
    
TransferFunction
    - transfer(Lattice in) -> Lattice out
        - takes a lattice and outputs another lattice