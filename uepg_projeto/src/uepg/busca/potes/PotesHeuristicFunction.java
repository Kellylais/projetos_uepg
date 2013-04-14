/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

/**
 *
 * @author jcarlos
 */

import aima.search.framework.HeuristicFunction;

public class PotesHeuristicFunction implements HeuristicFunction {

    private PotesTeste teste;

    public PotesHeuristicFunction(PotesTeste pteste) {
        teste = pteste;
    }

    public double getHeuristicValue(Object state) {
        double heuristic = 0;
        PotesEstado potes = (PotesEstado) state;
        int[] objs = teste.getObjetive();
        int dif1 = Math.abs(potes.getVolumeIn5L()-objs[0]);
        int dif2 = Math.abs(potes.getVolumeIn3L()-objs[1]);


        heuristic = dif1+dif2;
        //heuristica*soma absolua das diferencas em cada pote
        // meta 3,3  - atual (3,1)  --> heuristic = 1 * 2 (L) = 2
        return heuristic;
    }


}
