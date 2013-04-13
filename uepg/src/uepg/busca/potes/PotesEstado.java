/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.busca.potes;

/**
 *
 * @author jcarlos
 */
public class PotesEstado {

    int pt5L, pt3L = 0;  //volume armazenado
    
    public void setNewVolume(int a, int b) {
        this.pt5L=a;
        this.pt3L=b;
    }
    
    public int getVolumeIn5L() {
        return this.pt5L;
    }
    
    public int getVolumeIn3L() {
        return this.pt3L;
    }

}
