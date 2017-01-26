# Framework for Dataflow Analyses

## TODO
- watch youtube videos or other resources to understand in-depth how dataflow analyses work with CFGs
- create the analysis only for Live-Variable analysis initially
- write down the process for an analysis in words: Create CFG -> that creates the Lattices for every node and the powerlattice for the entire program
- figure out which classes are needed:
    - Lattice
    - CFG
        - something for the different types of nodes including entry and exit
    - TransferFunctions
        - they need to model the actual transfer functions
    - abstract Analsysis
        - will be extended by the actual implementations for each analysis
        - you instantiate a specific analysis but the running store in in the abstract datatype
- once all possible analyses work, figure out a way to optimise the input programs
    - should do M2T to output while source code
- document the entire framework in the report
    - ask Jesper how we should structure the report. Should it be like documentation or explanation?
    

## Goals
1. Implement a simple Available Expression Analysis for a sample program
2. Extend this analysis to work for any analysis as a framework
3. Enable the usage of different algorithms for solving fixed points computation
    - from simple to worklist
4. Enable optimization to the source code
    - M2T transformations for the input code