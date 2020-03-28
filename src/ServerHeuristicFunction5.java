import aima.search.framework.HeuristicFunction;
import java.lang.Math;


// Aquest valora els costos.
public class ServerHeuristicFunction5 implements HeuristicFunction {
    public double getHeuristicValue(Object state) {
        ServerState aux_state = (ServerState) state;
        return Math.sqrt(aux_state.get_variance()); //???
    }
}
