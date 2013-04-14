/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.rneurais;

/**
 *
 * @author jcarlos
 */
import javax.media.jai.*;
import com.sun.media.jai.widget.DisplayJAI;
import java.awt.event.*;


public class DisplayJAIE extends DisplayJAI implements MouseListener {
    
    int xa = -1;
    int ya = -1;
    
    int xb = -1;
    int yb = -1;
    
    public DisplayJAIE(PlanarImage image) {
        super(image);
        // Registers the mouse motion listener.
        addMouseMotionListener(this);

    }

    
    
     public Object getRegion(int xa, int ya, int xb, int yb) {
        Object aux;
        
        if ((xb<xa) && (yb<ya)) {
            int za = xa;
            int wa = ya;
            xa = xb;
            ya = yb;
            
            xb = za;
            yb = wa;
            
        } else if(xb<xa) {
            int za = xa;
            xa = xb;
            xb = za;            
        } else if(yb<ya) {
            int wa = ya;
            ya = yb;
            yb = wa;            
        }
        
        int sizeh = (yb-ya);
        int sizew = (xb-xa);
                
       
        java.awt.Rectangle rect = new
                java.awt.Rectangle(xa, ya, sizew, sizeh);
        aux = this.source.getData(rect);
        return aux;
    }
    
    
    
    
    

}
