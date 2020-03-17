import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            while(!newState.can_modify(i, s))
            {
                s = myRandom.nextInt(ServerState.serversList.size());
            }
            newState.modify_server(i,s);
            successors.add(new Successor("change the server of the request "+i+" to "+s+"\n", newState));
        }
        return successors;
    }
}
