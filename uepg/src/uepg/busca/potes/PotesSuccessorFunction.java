/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

/**
 *
 * @author jcarlos
 */

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;


public class PotesSuccessorFunction implements SuccessorFunction {

    	public List getSuccessors(Object state) {
		List<Successor> successors = new ArrayList<Successor>();
		PotesEstado potes = (PotesEstado) state;

                // encher pote de 5L
                if (potes.getVolumeIn5L()<5) 
                    novoEstado(5,potes.getVolumeIn3L(),"Encher pote 5L",successors);
                

                // encher pote de 3L
                if (potes.getVolumeIn3L()<3) 
                    novoEstado(potes.getVolumeIn5L(),3,"Encher pote 3L",successors);
                

                //esvaziar pote de 5L no pote 3L
                if ((potes.getVolumeIn5L()>0) && (potes.getVolumeIn5L()<=(3-potes.getVolumeIn3L())))
                    novoEstado(0,potes.getVolumeIn3L()+potes.getVolumeIn5L(),"Esvaziar 5L em 3L",successors);


                // esvaziar pote de 5L
                if (potes.getVolumeIn5L()>0)
                    novoEstado(0,potes.getVolumeIn3L(),"Esvaziar pote 5L",successors);


                // esvaziar pote de 3L
                if (potes.getVolumeIn3L()>0)
                    novoEstado(potes.getVolumeIn5L(),0,"Esvaziar pote 3L",successors);


                //esvaziar pote de 3L no pote 5L
                if ((potes.getVolumeIn3L()>0) && (potes.getVolumeIn5L()<=(5-potes.getVolumeIn3L())))
                    novoEstado(potes.getVolumeIn3L()+potes.getVolumeIn5L(),0,"Esvaziar 3L em 5L",successors);

                //completar o pote de 5L com parte do conteudo do pote de 3L
                if ((potes.getVolumeIn3L()>0) && (potes.getVolumeIn5L()<5))
                    if ((5-potes.getVolumeIn5L())<potes.getVolumeIn3L())
                        if (potes.getVolumeIn5L()+potes.getVolumeIn3L()>5) {
                            int dif = (potes.getVolumeIn3L() - (5 - potes.getVolumeIn5L()));
                            novoEstado(5,potes.getVolumeIn3L()-dif,"Completar potes de 5L com parte do conteudo do pote de 3L",successors);
                        }

                //completar o pote de 3L com parte do conteudo do pote de 5L
                if ((potes.getVolumeIn3L()<3) && (potes.getVolumeIn5L()>0))
                    if ((3-potes.getVolumeIn3L())<potes.getVolumeIn5L())
                        if (potes.getVolumeIn5L()+potes.getVolumeIn3L()>3) {
                            int dif = potes.getVolumeIn5L() - (3 - potes.getVolumeIn3L());
                            novoEstado(potes.getVolumeIn5L()-dif,3,"Completar potes de 3L com parte do conteudo do pote de 5L",successors);
                        }




		return successors;
        }


        private void novoEstado(int a, int b, String acao, List<Successor> successors) {
            PotesEstado child = new PotesEstado();
            child.setNewVolume(a, b);
            successors.add(new Successor(acao, child));
        }

}
