import aima.search.framework.HeuristicFunction;
import java.lang.Math;


// Aquest valora els costos.
public class ServerHeuristicFunction4 implements HeuristicFunction {
    public double getHeuristicValue(Object state) {
        ServerState aux_state = (ServerState) state;
        return aux_state.get_total_time() + aux_state.get_variance(); //???
    }
}
