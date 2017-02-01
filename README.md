# Framework for Dataflow Analyses

## Questions
### Specific
- What do you think about my implementation of the CFG?]
- Should I model an environment lattice?
    - currently I assign every CFGNode two lattices that are each a Map from the feature I am interested in to the lattice for that feature, e.g: x -> [lattice for signs]
- What interface does the lattice need to support?
    - initialise() - sets the initial values for every lattice
    - getValueForParameter() - returns the lattice for specific feature (variable x)
    - setValueForParameter() - updates the lattice for specific feature (variable x)
    - meet() - combines two lattices by doing a pair- and bit-wise and
    - join() - combiles two lattices by doing a pair- and bit-wise or
    - deepCopy() - copies a lattice and its underlying map in order to enable the Naive Engine
- What interface should the engine support?
    - run() - runs the engine until a least fixed-point is found
        - not guaranteed to termninate if the lattice class does not have a least upper bound and a greatest lower bound and if the transfer functions are not monotonic
    - anything else here
- Is there a better way to track the execution of the analysis than with using CFGState?
- Which class should implement the transfer functions?
    - I personally think it should be the IAnalysisFramework subclasses
- How can the transfer functions work?
    - I am taking the in lattice and extracting the transformation information from the node
    - then I am creating a copy of the in lattice and override the out lattice after having applied a bit-wise transformation to the BitSet for the variable covered by the CFGNode
- Overall my transfer functions over lattices feel very odd, since they are tightly coupled to the implementation of the specific lattice and involve a lot of casting.
    - this isn't a massive problem, since the user would implement both classes at the same time
- Could you walk me through a process for the engines and how they should interact with the framework and the lattice?

**Is there any other advice that you can give me to implement this project?**

### General
- What should the report contain?
    - should it act like documentation for the project
    - should it explain the theory behind dataflow analysis
- Is there already a date for the new exam?
- How is the project doing? What grade would you give it right now?
- Is there anything missing from the project and how is its scope?
- Should I write unit tests for the code?
- Should I implement error throwing?
- Do you have other literature or lecture notes that explain dataflow analysis?



## TODO
1. Implement a working Sign analysis using the Naive Engine
2. Implement a working Constant Propagation analysis using the Naive Engine
3. Implement the Chaotic Engine and test it on Sign & Constant Propagation analysis
4. Implement the Worklist Engine and test it on Sign & Constant Propagation analysis
5. Implement Available Expression analysis
6. Implement Live Variable analysis
- *Optional* Implement the beginning of M2T and code optimisation
- *Optional* Write unit tests for the framework
- *Optional* Implement throwing errors for invalid program states instead of returning null

## Further Improvements
### Parser
- it cannot parse statements that end with a semicolon