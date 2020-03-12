import java.util.ArrayList;

public class ServerState {
    private ArrayList<Integer> serv;
    private ArrayList< ArrayList<Integer>> requests;
    private int total_time;
    private int working_servers;

    public ServerState(ServerState state)
    {
        this.serv = state.serv;
        this.requests = state.requests;
        this.total_time = state.total_time;
        this.working_servers = state.working_servers;
    }

    // Useful functions
    public int get_total_time()
    {
        return total_time;
    }

    public int get_working_servers()
    {
        return working_servers;
    }
    public double get_variance()
    {
        // Compute mean (average
        // of elements)
        double mean = total_time / working_servers;

        // Compute sum squared
        // differences with mean.
        double sqDiff = 0;
        for (auto w : serv)
        {
            if(w != 0)
            {
                sqDiff += (w - mean) * (w - mean);
            }
        }
        return (double)sqDiff / working_servers;
    }

    public int getMaxServerTime()
    {
        int max_value = Integer.MIN_VALUE;
        for(auto w : serv)
        {
            max_value = max(max_value, w);
        }
        return max_value;
    }

    public boolean isGoalState()
    {
        return false;
    }

    //state operators


    public move_request(int id, int serv)
    {

    }

    public boolean can_swap_requests(int id1, int id2)
    {
        
    }
    public swap_requests(int id_1, int id_2)
    {

    }



    //Generates the two initial solution

    //assign to the first server that operates this request
    public void initial_sol_1()
    {

    }
    //assign to the server which has less transmission time of that request
    public void initial_sol_2()
    {

    }

}
