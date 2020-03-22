import IA.DistFS.Servers;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import javafx.util.Pair;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;

public class main {
    public static void main(String[] args) throws Exception {
        int[] parameter = {689, 1137, 322, 668, 316, 582, 1203, 1039, 1014, 239, 810,477, 196, 981, 894,300, 1173, 245, 404, 1093};
        for (int i = 0; i < 19; i+=2) {
            long startTime=System.currentTimeMillis();
            ServerState state = new ServerState(200, 5, parameter[i], 50, 5, parameter[i+1]);
            state.initial_sol_3();
            Problem problem = new Problem(state, new ServerSuccessorFunction(), new ServerGoalTest(), new ServerHeuristicFunction1());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            long endTime=System.currentTimeMillis();
            System.out.println((endTime - startTime) * 0.001 + " execution time");
            //printActions(agent.getActions());
            System.out.println(state.getMaxServerTime() + " initial maxServer time");
            System.out.println(state.get_total_time() + " initial total time");
            printInstrumentation(agent.getInstrumentation());
            System.out.println(((ServerState)search.getGoalState()).getMaxServerTime() + " result maxServer time");
            System.out.println(((ServerState)search.getGoalState()).get_total_time() + " result total time");
            System.out.println("\n");
            /*state.initial_sol_2 ();
            Problem problem2 = new Problem(state, new ServerSuccessorFunction2(), new ServerGoalTest(), new ServerHeuristicFunction2());
            Search search2 = new SimulatedAnnealingSearch();
            SearchAgent agent2 = new SearchAgent(problem2, search2);
            System.out.println(((ServerState)search2.getGoalState()).get_total_time());*/
        }
    }
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
