/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uepg.busca.rotas;

/**
 *
 * @author Lays
 */
import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class RotasSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object state) {
        List<Successor> successors = new ArrayList<Successor>();
        RotasEstado rota = (RotasEstado) state;


        if (rota.getPos() < 8) {
            novoEstado(rota.getPos(), successors);
        }

        return successors;
    }

    private void novoEstado(int pos, List<Successor> successors) {
        RotasEstado child = new RotasEstado();
        child.setNewPos(pos);
        successors.add(new Successor(child));
    }
}
