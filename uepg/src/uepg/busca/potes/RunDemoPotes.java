/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

/**
 *
 * @author jcarlos
 */


import java.util.List;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;
//import aima.search.framework.TreeSearch;

//import aima.search.uninformed.BreadthFirstSearch;
import aima.search.uninformed.DepthFirstSearch;


public class RunDemoPotes {
    
    public void run() {
//        astarSearch();
//        costUniformSearch();
        //depthFirstSearch();
        this.depthFirstLimitedSearch();
//        breadthFirstSearch();
    }


    private void breadthFirstSearch() {
/*        try {
            System.out.println("\nNQueensDemo BFS -->");
			Problem problem =  new Problem(new NQueensBoard(8),new NQueensSuccessorFunction(), new NQueensGoalTest());
			Search search = new BreadthFirstSearch(new TreeSearch());
			SearchAgent agent2 = new SearchAgent(problem, search);
			printActions(agent2.getActions());
			printInstrumentation(agent2.getInstrumentation());
		} catch (Exception e1) {

			e1.printStackTrace();
		}*/
	}

    private void depthFirstSearch() {
        System.out.println("\nPotes DFS -->");
        try {
            Problem problem =  new Problem(new PotesEstado(),new PotesSuccessorFunction(), new PotesTeste(3,2));
            Search search = new DepthFirstSearch(new GraphSearch());
            SearchAgent agent = new SearchAgent(problem, search);
            printActions(agent.getActions());
        } catch (Exception e) {
			e.printStackTrace();
	}
    }


    private void depthFirstLimitedSearch() {
        System.out.println("\nPotes DFS L-->");
        try {
          Problem problem =  new Problem(new PotesEstado(),new PotesSuccessorFunction(), new PotesTeste(2,3));
          Search search = new DepthLimitedSearch(10);
	  SearchAgent agent = new SearchAgent(problem, search);
          printActions(agent.getActions());
	} catch (Exception e) {
		e.printStackTrace();
	}
    }


    private void costUniformSearch() {
        System.out.println("\nPotes COU -->");
        try {
            Problem problem =  new Problem(new PotesEstado(),new PotesSuccessorFunction(), new PotesTeste(3,2),new PotesStepCostFunction());
            Search search = new aima.search.uninformed.UniformCostSearch(new GraphSearch());
            SearchAgent agent = new SearchAgent(problem, search);
            printActions(agent.getActions());
        } catch (Exception e) {
			e.printStackTrace();
	}
    }


    private void astarSearch() {
        System.out.println("\nPotes Astar -->");
        try {
            PotesTeste pteste = new PotesTeste(1,3);

            Problem problem =
                    new Problem(new PotesEstado(),new PotesSuccessorFunction(),pteste ,new PotesStepCostFunction(), new PotesHeuristicFunction(pteste));
            Search search = new aima.search.informed.AStarSearch(new GraphSearch());
            SearchAgent agent = new SearchAgent(problem, search);
            printActions(agent.getActions());
        } catch (Exception e) {
			e.printStackTrace();
	}
    }

    private static void printActions(List actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = (String) actions.get(i);
			System.out.println(action);
		}
	}


}
