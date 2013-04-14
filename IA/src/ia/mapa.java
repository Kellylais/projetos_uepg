/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ia;

import java.util.ArrayList;

/**
 *
 * @author Kelly
 */
public class mapa {

    int [][] matriz = { {0, 1, 0, 0, 0, 0, 0, 0},
                  {1, 0, 1, 0, 1, 0, 0, 0},
                  {0, 1, 0, 1, 0, 0, 0, 0},
                  {0, 0, 1, 0, 1, 1, 0, 0},
                  {0, 1, 0, 1, 0, 0, 1, 0},
                  {0, 0, 0, 1, 0, 0, 1, 0},
                  {0, 0, 0, 0, 1, 1, 0, 1},
                  {0, 0, 0, 0, 0, 0, 1, 0}
              };
    
    ArrayList vizinhos = new ArrayList();

    public void matriz_adjacencia(){
    for(int i=0; i< matriz.length; i++)
        {
            for(int j=0; j< matriz.length; j++)
            {
               if(matriz[i][j] == 1){
                   vizinhos.add(j+1);
                   System.out.println("Linha "+ (i+1)+ " Coluna "+ (j+1) + " e vizinho "+vizinhos);
                  
               }


            }

        }
    }




}
