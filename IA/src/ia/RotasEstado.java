/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ia;

/**
 *
 * @author Kelly
 */
public class RotasEstado {

    int index;
    int posX, posY;

    public void NewEstado(int a, int b){
        this.posX = a;
        this.posY = b;

    }

     public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

}
