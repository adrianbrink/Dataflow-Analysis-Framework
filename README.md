# Framework for Dataflow Analyses

## TODO
- watch youtube videos or other resources to understand in-depth how dataflow analyses work with CFGs
- create the analysis only for Live-Variable analysis intially
- write down the process for an analysis in words: Create CFG -> that creates the Lattices for every node and the powerlattice for the entire program
- figure out which classes are needed:
    - Lattice
    - CFG
        - something for the differnet types of nodes
    - TransferFunctions
        - they need to model the actual transfer functions
    - abstract Analsysis
        - will be extended by the actual implementations for each analysis
        - you instantiate a specific analysis but the running store in in the abstract datatype
- once all possible analyses work, figure out a way to optimise the input programs
    - should do M2T to output while source code
- document the entire framework in the report
    - ask Jesper how we should structure the report. Should it be like documentation or explanation?