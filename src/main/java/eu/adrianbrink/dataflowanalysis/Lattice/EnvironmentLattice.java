package eu.adrianbrink.dataflowanalysis.Lattice;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sly on 04/02/2017.
 */
// user defined
public class EnvironmentLattice implements ILattice<EnvironmentLattice> {
    public Map<String, SignLattice> environment;

    public EnvironmentLattice(Set<String> programVariables) {
        Map<String, SignLattice> map = new HashMap();
        for (String parameter : programVariables) {
            BitSet bitSet = new BitSet();
            SignLattice lattice = new SignLattice();
            lattice.element = bitSet;
            map.put(parameter, lattice);
        }
        this.environment = map;
    }

    private EnvironmentLattice() {}

    @Override
    public EnvironmentLattice join(EnvironmentLattice that) {
        EnvironmentLattice newLattice = new EnvironmentLattice();
        Map<String, SignLattice> newEnvironment = new HashMap();
        newLattice.environment = newEnvironment;

        for (String key : this.environment.keySet()) {
            SignLattice thatValue = that.environment.get(key);
            SignLattice newA = environment.get(key).join(thatValue);
            newEnvironment.put(key,newA);
        }
        return newLattice;
    }

    @Override
    public boolean isEquals(EnvironmentLattice that) {
        for (String k : environment.keySet())
            if (!environment.get(k).isEquals(that.environment.get(k)))
                return false;
        return true;
    }
}
