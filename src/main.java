import IA.DistFS.Servers;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class main {
    public static void main(String[] args) throws Exception {
        ServerState state = new ServerState(100, 5, 1234, 1000, 3, 1234);
        state.initial_sol_1();
        Problem problem = new Problem(state, new ServerSuccessorFunction(), new ServerGoalTest(), new ServerHeuristicFunction1());
        Search search = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem, search);
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
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
