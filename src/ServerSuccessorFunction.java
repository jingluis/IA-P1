import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class ServerSuccessorFunction implements SuccessorFunction {
    public List getSuccessors(Object astate) {
        ArrayList<Successor> successors = new ArrayList<>();
        ServerState state = (ServerState) astate;
        for (int i = 0; i < ServerState.requestsList.size(); ++i) {
            for (int j = i+1; j < ServerState.requestsList.size(); ++j) {
                ServerState newState = new ServerState(state);
                if (newState.can_swap_server(i, j)) {
                    newState.swap_requests(i, j);
                    successors.add(new Successor("swap servers beetwen "+i+" and "+j+"\n", newState));
                }
            }

            for (int s = 0; s < ServerState.serversList.size(); ++s) {
                ServerState newState = new ServerState(state);
                if (newState.can_modify(i, s)) {
                    newState.modify_server(i,s);
                    successors.add(new Successor("change the server of the request "+i+" to "+s+"\n", newState));
                }
            }
        }
        return successors;
    }
}
