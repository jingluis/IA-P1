import IA.DistFS.Servers;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class main {
    public static void main(String[] args) throws Exception {
        ServerState state = new ServerState(200, 5, 1234, 50, 5, 1234);

        state.initial_sol_1();
        Problem problem = new Problem(state, new ServerSuccessorFunction(), new ServerGoalTest(), new ServerHeuristicFunction1());
        Search search = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem, search);
        System.out.println(state.get_total_time());
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        System.out.println(((ServerState)search.getGoalState()).get_total_time());

        state.initial_sol_2();
        Problem problem2 = new Problem(state, new ServerSuccessorFunction2(), new ServerGoalTest(), new ServerHeuristicFunction2());
        Search search2 = new SimulatedAnnealingSearch();
        SearchAgent agent2 = new SearchAgent(problem2, search2);
        System.out.println(((ServerState)search2.getGoalState()).get_total_time());
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
