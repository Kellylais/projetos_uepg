/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

/**
 *
 * @author jcarlos
 */

import aima.search.framework.GoalTest;

public class PotesTeste implements GoalTest {
    
    int sf5L, sf3L = 0; //volume objetivo
    
    public PotesTeste(int a, int b) {
        sf5L = a;
        sf3L = b;
    }
    
    public boolean isGoalState(Object state) {
        PotesEstado potes = (PotesEstado) state;
        if (potes.getVolumeIn5L()==sf5L)
            if (potes.getVolumeIn3L()==sf3L)
                return true;
        return false;
    }


    public int[] getObjetive() {
        return new int[] {sf5L,sf3L};
    }

}
