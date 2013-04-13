/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uepg.busca.rotas;

/**
 *
 * @author jcarlos
 */
import java.util.List;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthFirstSearch;

public class RunDemoRotas {

    public void run() {
//        astarSearch();
//        costUniformSearch();
        //depthFirstSearch();
        this.depthFirstSearch();
//        breadthFirstSearch();
    }

    private void depthFirstSearch() {
        try {
            Problem problem = new Problem(new RotasEstado(), new RotasSuccessorFunction(), new RotasTeste(8));
            //cria problema: estado atual, funÃ§Ã£o sucessora, teste final (estado final)
            Search search = new DepthFirstSearch(new GraphSearch());
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