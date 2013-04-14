/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uepg.rneurais;

import javax.media.jai.*;
import com.sun.media.jai.widget.DisplayJAI;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 *
 * @author jcarlos
 */
public class Autoassociator {
    
    PlanarImage image1, image2;
    
    public Autoassociator(String img1, String img2) {
        this.image1 = JAI.create("fileload", img1);
        this.image2 = JAI.create("fileload", img2);
        ImageViewer viewer1 = new ImageViewer();
        viewer1.setImage(image1);
        ImageViewer viewer2 = new ImageViewer();
        viewer2.setImage(image2);
        viewer1.setVisible(true);
        viewer2.setVisible(true);
        
        double[][] E1 = normalize(image1);
        double[][] E2 = normalize(image2);
        

    }
    
    
    private double[][] normalize(PlanarImage pi) {
        return null;
    }
    
}    


