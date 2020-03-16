import aima.search.framework.HeuristicFunction;


// Aquest valora els costos.
public class ServerHeuristicFunction1 implements HeuristicFunction {
    public double getHeuristicValue(Object state) {
        ServerState aux_state = (ServerState) state;
        return aux_state.getMaxServerTime();
    }
}
