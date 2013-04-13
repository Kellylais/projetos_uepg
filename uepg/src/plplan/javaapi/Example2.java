package plplan.javaapi;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a test class. It use the PLPlan class  to create new facts, goal facts, 
 * and operators, and then to find a plan for it.
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
public class Example2 {

    public static void main(String[] args) throws PlPlanException {
        // Read a planning problem from a file, and then find a plan.
        PLPlan planner = new PLPlan();
        planner = new PLPlan();
        planner.setAlgorithm(EnumAlgorithm.FW_SS_SM_0); //FW_SS_SM_0  ParallelBlockWorld4b BlockWorld8c
        planner.readFactsFromFile("C:\\philippe\\eclipse\\workspace\\plplan\\plplan\\BlockWorld8c.txt");
        planner.readGoalFromFile("C:\\philippe\\eclipse\\workspace\\plplan\\plplan\\BlockWorld8c.txt");
        planner.readOperatorsFromFile("C:\\philippe\\eclipse\\workspace\\plplan\\plplan\\BlockWorld8c.txt");
        List resultats = planner.findPlan();
        System.out.println(planner.getPlanningOutput());
        System.out.println(resultats);       
    }
}
