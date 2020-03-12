import aima.search.framework.HeuristicFunction;


// Aquest valora els costos.
public class ServerHeuristicFunction2 implements HeuristicFunction {
    public double getHeuristicValue(Object state) {
        ServerState aux_state = (AzamonEstado) state;
        return aux_state.get_total_time() * aux_state.get_variance(); //???
    }
}
