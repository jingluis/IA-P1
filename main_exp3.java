import IA.DistFS.Servers;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class main {
    public static void main(String[] args) throws Exception {

        //printActions(agent.getActions());
    	//experiment9();
    	/*int k = 20;
    		for (double lamb = 0.001; lamb < 0.005; lamb += 0.001) {
    			System.out.println("k: " + k);
    			System.out.println("lamb: " + lamb);
    			simulatedannealingtest(150000, 100, k, lamb, 10);
    			System.out.println("-----------------------------------");
    		}
    		for (double lamb = 0.005; lamb <= 0.05; lamb += 0.005) {
    			System.out.println("k: " + k);
    			System.out.println("lamb: " + lamb);
    			simulatedannealingtest(150000, 100, k, lamb, 10);
    			System.out.println("-----------------------------------");
    		}
    	}*/
    	double lamb = 0.005;
    	for (int k = 10; k <= 50; k+=2) {
    		System.out.println("k: " + k);
			System.out.println("lamb: " + lamb);
			simulatedannealingtest(100000, 100, k, lamb, 10);
			System.out.println("-----------------------------------");
    	}

        
        /*state.initial_sol_2();
        Problem problem2 = new Problem(state, new ServerSuccessorFunction2(), new ServerGoalTest(), new ServerHeuristicFunction2());
        Search search2 = new SimulatedAnnealingSearch();
        SearchAgent agent2 = new SearchAgent(problem2, search2);
        System.out.println(((ServerState)search2.getGoalState()).get_total_time());
        long endTime=System.currentTimeMillis();
        System.out.println((endTime - startTime) * 0.001);*/
    }
    
    private static void experiment9() throws Exception {
    	long startTime=System.currentTimeMillis();
    	ServerState state = new ServerState(200, 5, 1234, 50, 5, 1234);
        state.initial_sol_2();
        System.out.println(state.get_total_time());
        System.out.println(state.getMaxServerTime());
        Problem problem = new Problem(state, new ServerSuccessorFunction(), new ServerGoalTest(), new ServerHeuristicFunction1());
        Search search = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem, search);
        printInstrumentation(agent.getInstrumentation());
        System.out.println(((ServerState)search.getGoalState()).get_total_time());
        System.out.println(((ServerState)search.getGoalState()).getMaxServerTime());
        long endTime=System.currentTimeMillis();
        System.out.println((endTime - startTime) * 0.001);
    }
    
    //default: 10000, 100, 20, 0.005
    private static void simulatedannealingtest(int steps, int stiter, int k, double lamb, int iters) throws Exception {
    	double exectime = 0;
    	int maxtime = 0;
    	int inittime = 0;
    	Random rand = new Random();
    	for (int i = 0; i < iters; ++i) {
    		long startTime=System.currentTimeMillis();
            int seed1 = rand.nextInt();
            int seed2 = rand.nextInt();
            ServerState state = new ServerState(200, 5, seed1, 50, 5, seed2);
            state.initial_sol_2();
            inittime += state.getMaxServerTime();
            Problem prob = new Problem(state, new ServerSuccessorFunction2(), new ServerGoalTest(), new ServerHeuristicFunction1());
            Search search = new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            SearchAgent agent = new SearchAgent(prob, search);
            long endTime=System.currentTimeMillis();
            exectime += (endTime-startTime)*0.001;
            maxtime += ((ServerState)search.getGoalState()).getMaxServerTime();
    	}
    	System.out.println("avg exec time: " + exectime/((double)iters));
    	System.out.println("avg max time: " + ((double)maxtime)/((double)iters));
    	System.out.println("avg init time: " + ((double)inittime)/((double)iters));
    	System.out.println("avg diff time: " + (((double)inittime)/((double)iters)-((double)maxtime)/((double)iters)));
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
