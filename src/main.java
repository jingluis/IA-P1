import IA.DistFS.Servers;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.SuccessorFunction;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class main {
    public static void main(String[] args) throws Exception {

        //printActions(agent.getActions());
    	
    	Scanner in = new Scanner(System.in);
    	System.out.println("experiment: ");
    	int exp = in.nextInt();
    	switch(exp) {
    	
    	case 9:
    	    experiment9();
    	    break;
    	
    	case 1:
    	//experiment 1
    		int[] seeds1 = seedgen(10);
    		//hillclimbingtest(200, 5, 50, 5, seeds1, true, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
    		hillclimbingtest(200, 5, 50, 5, seeds1, true, new ServerHeuristicFunction1(), new ServerSuccessorFunctionModify(), 2);
    		hillclimbingtest(200, 5, 50, 5, seeds1, true, new ServerHeuristicFunction1(), new ServerSuccessorFunctionSwap(), 2);
    		hillclimbingtest(200, 5, 50, 5, seeds1, true, new ServerHeuristicFunction1(), new ServerSuccessorFunctionModifySwap(), 2);
    		break;
    		
    	case 2:
    	//experiment 2
    		int[] seeds2 = seedgen(10);
    		hillclimbingtest(200, 5, 50, 5, seeds2, true, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 1);
    		hillclimbingtest(200, 5, 50, 5, seeds2, true, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
    		hillclimbingtest(200, 5, 50, 5, seeds2, true, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 3);
    		break;
    	
    	case 3:
    	//experiment 3
    		for (int k = 1; k <= 512; k *= 2) {
	    	/*for (double lamb = 0.002; lamb <= 0.005; lamb += 0.001) {
	    		System.out.println("k: " + k);
	    		System.out.println("lamb: " + lamb);
	    		int[] seeds = seedgen(10);
	    		simulatedannealingtest(100000, 100, k, lamb, seeds, false, new ServerHeuristicFunction1(), new ServerSuccessorFunction2());
	    		System.out.println("-----------------------------------");
	    	}*/
	    	for (double lamb = 0.000001; lamb <= 1; lamb *= 10) {
	    		System.out.println("k: " + k);
	    		System.out.println("lamb: " + lamb);
	    		int[] seeds = seedgen(10);
	    		simulatedannealingtest(100000, 100, k, lamb, seeds, false, new ServerHeuristicFunction1(), new ServerSuccessorFunction2());
	    		System.out.println("-----------------------------------");
	    	}
    		}
	    	/*for (int k = 5; k <= 50; k+=5) {
	    		System.out.println("k: " + k);
				System.out.println("lamb: " + 0.005);
				int[] seeds = seedgen(10);
				simulatedannealingtest(100000, 100, k, 0.005, seeds, false, new ServerHeuristicFunction1(), new ServerSuccessorFunction2());
				System.out.println("-----------------------------------");
	    	}*/
	    	break;
    	
	    case 4:
    	//experiment 4
	    	for (int k = 100; k <= 1000; k += 100) {
	    		System.out.println("users: " + k);
	    		int[] seeds = seedgen(10);
	    		hillclimbingtest(k,5,50,5,seeds,false, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
	    		System.out.println("-----------------------------------");
	    	}
	    	for (int k = 50; k <= 1000; k += 50) {
	    		System.out.println("servs: " + k);
	    		int[] seeds = seedgen(10);
	    		hillclimbingtest(200,5,k,5,seeds,false, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
	    		System.out.println("-----------------------------------");
	    	}
	    	break;
    	
	    case 5:
    	//experiment 5
	    	int[] seeds5 = seedgen(50);
	    	hillclimbingtest(200, 5, 50, 5, seeds5,false, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
	    	//hillclimbingtest(200, 5, 50, 5, seeds5,false, new ServerHeuristicFunction2(), new ServerSuccessorFunction(), 2);
	    	System.out.println("-----------------------------");
	    	hillclimbingtest(200, 5, 50, 5, seeds5,false, new ServerHeuristicFunction3(), new ServerSuccessorFunction(), 2);
	    	System.out.println("-----------------------------");
	    	//hillclimbingtest(200, 5, 50, 5, seeds5,false, new ServerHeuristicFunction4(), new ServerSuccessorFunction(), 2);
	    	System.out.println("-----------------------------");
	    	//hillclimbingtest(200, 5, 50, 5, seeds5,false, new ServerHeuristicFunction5(), new ServerSuccessorFunction(), 2);
	    	break;
	    
	    case 6:
	    //experiment 6
	    	int[] seeds6 = seedgen(50);
	    	simulatedannealingtest(100000, 100, 20, 0.005, seeds6, false, new ServerHeuristicFunction1(), new ServerSuccessorFunction2());
	    	System.out.println("-----------------------------");
	    	simulatedannealingtest(100000, 100, 20, 0.005, seeds6, false, new ServerHeuristicFunction3(), new ServerSuccessorFunction2());
	    	break;
	    
	    case 7:
	    //experiment 7
	    	for (int k = 5; k <= 25; k += 5) {
	    		System.out.println("replicaciones: " + k);
	    		int[] seeds = seedgen(10);
	    		hillclimbingtest(200,5,50,k,seeds,false, new ServerHeuristicFunction1(), new ServerSuccessorFunction(), 2);
	    		System.out.println("--------------------------");
	    		hillclimbingtest(200,5,50,k,seeds,false, new ServerHeuristicFunction3(), new ServerSuccessorFunction(), 2);
	    		System.out.println("                           ");
	    	}
	    	break;
	    	
	    default:
	    	break;

    	}
        /*state.initial_sol_2();
        Problem problem2 = new Problem(state, new ServerSuccessorFunction2(), new ServerGoalTest(), new ServerHeuristicFunction2());
        Search search2 = new SimulatedAnnealingSearch();
        SearchAgent agent2 = new SearchAgent(problem2, search2);
        System.out.println(((ServerState)search2.getGoalState()).get_total_time());
        long endTime=System.currentTimeMillis();
        System.out.println((endTime - startTime) * 0.001);*/
    }
    
    private static int[] seedgen(int nseeds) {
    	int[] seeds = new int[nseeds*2];
    	Random rand = new Random();
    	for (int i = 0; i < nseeds*2; ++i) {
    		seeds[i] = rand.nextInt();
    	}
    	return seeds;
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
    
    private static void hillclimbingtest(int users, int reqs, int servs, int nrep, int[] seeds, boolean info, HeuristicFunction heur, SuccessorFunction suc, int inisol) throws Exception {
    	double exectime = 0;
    	int maxtime = 0;
    	int inittime = 0;
    	int totaltime = 0;
    	int iters = seeds.length/2;
    	for (int i = 0; i < seeds.length; i += 2) {
    		long startTime=System.currentTimeMillis();
            int seed1 = seeds[i];
            int seed2 = seeds[i+1];
            ServerState state = new ServerState(users, reqs, seed1, servs, nrep, seed2);
            if (inisol == 1) state.initial_sol_1();
            else if (inisol == 3) state.initial_sol_3();
            else state.initial_sol_2();
            inittime += state.getMaxServerTime();
            if (info) {
            	System.out.println("max initime: " + state.getMaxServerTime());
            	System.out.println("total initime: " + state.get_total_time());
            }
            Problem prob = new Problem(state, suc, new ServerGoalTest(), heur);
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(prob, search);
            long endTime=System.currentTimeMillis();
            exectime += (endTime-startTime)*0.001;
            totaltime += ((ServerState)search.getGoalState()).get_total_time();
            maxtime += ((ServerState)search.getGoalState()).getMaxServerTime();
            if (info) {
            	System.out.println("seed1: " + seed1);
            	System.out.println("seed2: " + seed2);
            	System.out.println("totaltime: " + ((ServerState)search.getGoalState()).get_total_time());
            	System.out.println("maxtime: " + ((ServerState)search.getGoalState()).getMaxServerTime());
            	System.out.println("exectime: " + (endTime-startTime)*0.001);
            	printInstrumentation(agent.getInstrumentation());
            } else {
            	System.out.println((endTime-startTime) + " " + ((ServerState)search.getGoalState()).get_total_time());
            }
    	}
    	System.out.println("avg exec time: " + exectime/((double)iters));
    	System.out.println("avg max time: " + ((double)maxtime)/((double)iters));
    	System.out.println("avg init time: " + ((double)inittime)/((double)iters));
    	System.out.println("avg diff time: " + (((double)inittime)/((double)iters)-((double)maxtime)/((double)iters)));
    	System.out.println("avg total time: " + ((double)totaltime)/((double)iters));
    }
    
    //default: 10000, 100, 20, 0.005
    private static void simulatedannealingtest(int steps, int stiter, int k, double lamb, int[] seeds, boolean info, HeuristicFunction heur, SuccessorFunction suc) throws Exception {
    	double exectime = 0;
    	int maxtime = 0;
    	int inittime = 0;
    	int totaltime = 0;
    	int iters = seeds.length/2;
    	for (int i = 0; i < seeds.length; i += 2) {
    		long startTime=System.currentTimeMillis();
            int seed1 = seeds[i];
            int seed2 = seeds[i+1];
            ServerState state = new ServerState(200, 5, seed1, 50, 5, seed2);
            state.initial_sol_2();
            inittime += state.getMaxServerTime();
            Problem prob = new Problem(state, suc, new ServerGoalTest(), heur);
            Search search = new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            SearchAgent agent = new SearchAgent(prob, search);
            long endTime=System.currentTimeMillis();
            exectime += (endTime-startTime)*0.001;
            totaltime += ((ServerState)search.getGoalState()).get_total_time();
            maxtime += ((ServerState)search.getGoalState()).getMaxServerTime();
            if (info) {
            	System.out.println("seed1: " + seed1);
            	System.out.println("seed2: " + seed2);
            	System.out.println("totaltime: " + ((ServerState)search.getGoalState()).get_total_time());
            	System.out.println("maxtime: " + ((ServerState)search.getGoalState()).getMaxServerTime());
            	System.out.println("exectime: " + (endTime-startTime)*0.001);
            } else {
            	//System.out.println(((ServerState)search.getGoalState()).getMaxServerTime());
            	System.out.println((endTime-startTime) + " " + ((ServerState)search.getGoalState()).get_total_time() + " " + Math.sqrt(((ServerState)search.getGoalState()).get_variance()));
            }
    	}
    	System.out.println("avg exec time: " + exectime/((double)iters));
    	System.out.println("avg max time: " + ((double)maxtime)/((double)iters));
    	System.out.println("avg init time: " + ((double)inittime)/((double)iters));
    	System.out.println("avg diff time: " + (((double)inittime)/((double)iters)-((double)maxtime)/((double)iters)));
    	System.out.println("avg total time: " + ((double)totaltime)/((double)iters));
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
