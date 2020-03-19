import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ServerSuccessorFunction2 implements SuccessorFunction {
    public List getSuccessors(Object astate) {
        ArrayList<Successor> successors = new ArrayList<>();
        ServerState state = (ServerState) astate;
        Random myRandom = new Random();
        //determine which operator will be chosen
        int operator = myRandom.nextInt(2);
        int i = myRandom.nextInt(ServerState.requestsList.size());

        ServerState newState = new ServerState(state);
        if(operator == 0)
        {
            int j = myRandom.nextInt(ServerState.requestsList.size());
            int cond = 0;
            while(!newState.can_swap_server(i, j) && cond < ServerState.requestsList.size())
            {
                j = myRandom.nextInt(ServerState.requestsList.size());
                ++cond;
            }
            if (newState.can_swap_server(i, j)) {
                newState.swap_requests(i, j);
                successors.add(new Successor("swap servers beetwen "+i+" and "+j+"\n", newState));
            }
        }
        else
        {
            int s = myRandom.nextInt(ServerState.serversList.size());
            int fileId = ServerState.requestsList.getRequest(i)[1];
            Set<Integer> available_servers = ServerState.serversList.fileLocations(fileId);
            //get the index of the random choosed server
            int chosed = myRandom.nextInt(available_servers.size());
            int ite = 0;
            for (int serv : available_servers) {
                if (ite == chosed) {
                    newState.modify_server(i, serv);
                    break;
                }
                ++ite;
            }
            successors.add(new Successor("change the server of the request "+i+" to "+s+"\n", newState));
        }
        return successors;
    }
}
