package plplan.javaapi;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a test class. It use the PLPlan class to create new facts, goal facts, 
 * and operators, and then to find a plan for it.
 * 
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
public class Example1 {

    public static void main(String[] args) throws PlPlanException {

        System.out.println("-- TEST1 --");
        // Create a planning problem, and then find a plan
        // This problem is based on Blockworld
        PLPlan planner = new PLPlan();
        planner.setAlgorithm(EnumAlgorithm.GRAPHPLAN);
        // Facts //td, ocd, obc, oab, na
        planner.addFact("td"); 
        planner.addFact("ocd"); 
        planner.addFact("obc"); 
        planner.addFact("oab"); 
        planner.addFact("na"); 
        // Goal //( oca, odb, ta, tb, nc, nd)
        planner.addGoalFact("oca");
        planner.addGoalFact("odb");
        planner.addGoalFact("ta");
        planner.addGoalFact("tb");
        planner.addGoalFact("nc");
        planner.addGoalFact("nd");
        // Operators [uAB], [uBC], [uCD], [sCA,
//        (op uAB
//                (na, oab)
//                (ta, nb)
//                (oab))
        List<String> precond = new ArrayList<String>();
        precond.add("na");
        precond.add("oab");
        List<String> neg = new ArrayList<String>();
        neg.add("oab");
        List<String> pos = new ArrayList<String>();
        pos.add("ta");
        pos.add("nb");
        planner.addOperator("uAB",precond, neg, pos);
        
//      (op uBC     //   operator name : "uBC"  unstack block b from block c
//      (nb, obc)   //   predonditions : the b Block is on block c (obc) and 
//                                       there is no block on block b (nb)                                                   
//      (tb, nc)    //   add facts :     the block b is on table (tb)
//                                       there is no block on block c (nc)
//      (obc))      //   remove facts : we remove the fact that
//                                      the b Block is on block c (ocd)
        precond = new ArrayList<String>();
        precond.add("nb");
        precond.add("obc");
        neg = new ArrayList<String>();
        neg.add("obc");
        pos = new ArrayList<String>();
        pos.add("tb");
        pos.add("nc");
        planner.addOperator("uBC",precond, neg, pos);
        
//        (op uCD             //   operator name : "uCD"  unstack block c from block d
//                (nc, ocd)   //   predonditions : the c Block is on block D (ocd) and 
//                                                 there is no block on block c (nc)                                                   
//                (tc, nd)    //   add facts :     the block c is on table (tc)
//                                                 there is no block on block d (nd)
//                (ocd))      //   remove facts : we remove the fact that
//                                                the c Block is on block d (ocd)
        precond = new ArrayList<String>();
        precond.add("nc");
        precond.add("ocd");
        neg = new ArrayList<String>();
        neg.add("ocd");
        pos = new ArrayList<String>();
        pos.add("tc");
        pos.add("nd");
        planner.addOperator("uCD",precond, neg, pos);
//      (op sCA       // operator name : "sCA" stack block c on block a
//      (na, tc, nc)  // predonditions : there is no block on block a (na)
//                                       the block c is on table  (tc)
//                                       there is no block on block c (nc)                                                   
//      (oca)         // add facts :     the block c is on block a  (oca)
//      (na, tc))     // remove facts : we remove the facts that
//                                       - there is no block on block a (na),
//                                       - block c is on table (tc).
        precond = new ArrayList<String>();
        precond.add("na");
        precond.add("tc");
        precond.add("nc");
        neg = new ArrayList<String>();
        neg.add("na");
        neg.add("tc");
        pos = new ArrayList<String>();
        pos.add("oca");
        planner.addOperator("sCA", precond, neg, pos);
        
//      (op sDB       // operator name : "sDB" stack block d on block b
//      (nd, tb, nb)  // predonditions : there is no block on block d (nd)
//                                       the block b is on table (tb) 
//                                       there is no block on block b (nb)                                                   
//      (odb)         // add facts :     the block d is on block b  (odb)
//      (nb, td))     // remove facts : we remove the facts that
//                                       - there is no block on block b (nb),
//                                       - block d is on table (td).
        precond = new ArrayList<String>();
        precond.add("nb");
        precond.add("td");
        precond.add("nd");
        neg = new ArrayList<String>();
        neg.add("nb");
        neg.add("td");
        pos = new ArrayList<String>();
        pos.add("odb");
        planner.addOperator("sDB", precond, neg, pos);
        
        //planner.readOperatorsFromFile("C:\\philippe\\eclipse\\workspace\\plplan\\plplan\\BlockWorld4.txt");
        List resultats = planner.findPlan();
        System.out.println(planner.getPlanningOutput());
        System.out.println(resultats);
      
        
    }
}
