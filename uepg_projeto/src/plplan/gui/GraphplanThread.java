
package plplan.gui;

import java.util.List;

import plplan.algorithms.AbstractPlanner;

/**
 * This class is used by the GUI to make a thread to stop the planner if it takes too much time
 * to find a plan.
 * <p>
 * PLPLAN
 * Authors : Philippe Fournier-Viger and Ludovic lebel
 * <p>
 * This work is licensed under the Creative Commons Attribution 2.5 License. To
 * view a copy of this license, visit
 * http://creativecommons.org/licenses/by/2.5/ or send a letter to Creative
 * Commons, 543 Howard Street, 5th Floor, San Francisco, California, 94105, USA.
 * <p>
 * If you use PLPLAN, we ask you to mention our names and our webpage URL in your work. 
 * The PLPLAN software is copyrighted by Philippe Fournier-Viger and Ludovic Lebel (2005). 
 * Please read carefully the license to know what you can do and cannot do with this software. 
 * You can contact Philippe Fournier-Viger for special permissions. 
 * <p>
 * This sofware is provided "as is", without warranty of any kind. 
 * The user takes the entire risk as to the quality and performance of the software. 
 * The authors accept no responsibility for any problem the user encounters using this software.
 * <p>
 * @author Philippe Fournier-Viger and Ludovic Lebel
 */
public class GraphplanThread implements Runnable{
	
	private AbstractPlanner graphplan;
	private List solution;
	

	 public GraphplanThread(AbstractPlanner graphplan){
	 	super();
	 	solution = null;
	 	this.graphplan = graphplan;
	 }

	  public void run() {
	  	solution = graphplan.run();
	  }

	/**
	 * @return Returns the solution.
	 */
	public List getSolution() {
		return solution;
	}
}
