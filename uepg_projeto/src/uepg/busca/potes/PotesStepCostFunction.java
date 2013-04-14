/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

import aima.search.framework.StepCostFunction;

/**
 *
 * @author jcarlos
 */
public class PotesStepCostFunction implements StepCostFunction {

    public Double calculateStepCost(Object fromState, Object toState, String action) {
        double cost = 0;
        PotesEstado from = (PotesEstado) fromState;
        PotesEstado to = (PotesEstado) toState;

        if (action.compareToIgnoreCase("Encher pote 5L")==0)
            cost = 5 - from.getVolumeIn5L();
        if (action.compareToIgnoreCase("Encher pote 3L")==0)
            cost = 3 - from.getVolumeIn3L();
        if (action.compareToIgnoreCase("Esvaziar pote 3L")==0)
            cost = from.getVolumeIn3L();
        if (action.compareToIgnoreCase("Esvaziar pote 5L")==0)
            cost = from.getVolumeIn5L();
        if (action.compareToIgnoreCase("Esvaziar 5L em 3L")==0)
            cost = from.getVolumeIn5L();
        if (action.compareToIgnoreCase("Esvaziar 3L em 5L")==0)
            cost = from.getVolumeIn3L();
        if (action.compareToIgnoreCase("Completar potes de 5L com parte do conteudo do pote de 3L")==0)
            cost = from.getVolumeIn3L();
        if (action.compareToIgnoreCase("Completar potes de 3L com parte do conteudo do pote de 5L")==0)
            cost = from.getVolumeIn5L();
        System.out.println(from.getVolumeIn5L()+" "+from.getVolumeIn3L());
        System.out.println(action + " " + cost);
        System.out.println();



        return cost;
    }


}
