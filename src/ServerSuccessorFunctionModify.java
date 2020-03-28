
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServerSuccessorFunctionModify implements SuccessorFunction {
    public List getSuccessors(Object astate) {
        ArrayList<Successor> successors = new ArrayList<>();
        ServerState state = (ServerState) astate;
        for (int i = 0; i < ServerState.requestsList.size(); ++i) {
            int fileId = ServerState.requestsList.getRequest(i)[1];
            Set<Integer> available_servers = ServerState.serversList.fileLocations(fileId);

            /*for (int j = i+1; j < ServerState.requestsList.size(); ++j) {
                if (state.can_swap_server(i, j)) {
                    ServerState newState = new ServerState(state);
                    newState.swap_requests(i, j);
                    successors.add(new Successor("swap servers beetwen "+i+" and "+j+"\n", newState));
                }
            }*/

            for (int serv : available_servers) {
                if (serv != state.get_server(i)) {
                    ServerState newState = new ServerState(state);
                    newState.modify_server(i, serv);
                    successors.add(new Successor("change the server of the request " + i + " to " + serv + "\n", newState));
                }
            }

        }
        return successors;
    }
}
