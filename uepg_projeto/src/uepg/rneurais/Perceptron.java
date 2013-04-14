/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uepg.rneurais;

/**
 *
 * @author jcarlos
 * 
 * NOTA - usar o pacote javacsv.jar para ler arquivo jar
 */
public class Perceptron {
    
    String[][] dados;
    double[] w;
    public Perceptron(String f) {
        
         dados = loadData(f);
        
    }
    
    public void learn(int nepoch) {
        w = new double[dados.length-1];
        for (int i=0; i<(w.length);i++) w[i]=0;
        

        for (int j=0; j<nepoch; j++) {
            int erroOnline = 0;
            for (int k=0; k<dados.length; k++) {
                double out = calcOut(k);
                if (err1(out,k) || err2(out,k)) {
                   erroOnline++;
                   if (err1(out,k)) 
                     update(k,true);                   
                   else if (err2(out,k))
                     update(k,false);
                }
            }
            
        }
        
    }
    
    public void update(int k, boolean option) {
        for (int i=0; i<w.length; i++)
            w[i] = w[i] + java.lang.Integer.valueOf(dados[k][i]) *
                   (option ? 1 : -1);

    }
    
    public double calcOut(int k) {
        double out = 0;
        for (int i =0; i<w.length; i++)
            out = out + w[i]*java.lang.Integer.valueOf(dados[k][i]);
        return out;
    }
    
    public boolean err1(double out, int k) {
        int y = java.lang.Integer.valueOf(dados[k][dados.length-1]);
        if ((y>=0) && (out < 0))
            return true;
        else return false;
    }
    
    public boolean err2(double out, int k) {
        int y = java.lang.Integer.valueOf(dados[k][dados.length-1]);
        if ((y<0) && (out >= 0))
            return true;
        else return false;
    }
    
    
    
    private String[][] loadData(String f) {
        return null;
    }
    
}
