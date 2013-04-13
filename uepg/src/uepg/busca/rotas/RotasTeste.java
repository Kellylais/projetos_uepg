/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uepg.busca.rotas;

/**
 *
 * @author jcarlos
 */
import aima.search.framework.GoalTest;

public class RotasTeste implements GoalTest {

    int pos = 1;

    public RotasTeste(int pos) {
        pos = pos;
    }

    public boolean isGoalState(Object state) {
        RotasEstado rota = (RotasEstado) state;
        if (rota.getPos() == 8) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getObjetive() {
        return new int[]{pos};
    }
}