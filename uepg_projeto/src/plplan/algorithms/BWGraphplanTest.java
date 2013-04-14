
package plplan.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import plplan.javaapi.StringToByteConvertor;

import junit.framework.TestCase;

/**
 * Example1 class for BWGraphplan.
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
public class BWGraphplanTest extends TestCase {

	private PlanningGraph planGraph = null;
	WorldReader wr = null;
    
    String sLar1="Lar1";
    String sLbq2="Lbq2";
    String sMr12="Mr12";
    String sMq21="Mq21";
    String sUar2="Uar2";
    String sUar1="Uar1";
    String sUbq1="Ubq1";
    
    StringToByteConvertor convertor = null;
    
    
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
        convertor = new StringToByteConvertor();
		wr = new WorldReader(".\\plplan\\PGTestWorld2.txt", convertor);
		planGraph = new PlanningGraph(wr.getOpSet(), wr.getFactSet());
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRun() {
		try {
            convertor = new StringToByteConvertor();
			wr = new WorldReader(".\\plplan\\PGTestWorld2.txt",convertor);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BWGraphplan bwgp = new BWGraphplan(wr.getOpSet(), wr.getFactSet(), wr.getGoalList());
		List plan = bwgp.run();
		System.out.println(bwgp.getPlanningOutput());
		
		Action Lar1 = new Action(convertor.getIDForPropositionName(sLar1));
		Action Lbq2 = new Action(convertor.getIDForPropositionName(sLbq2));
		Action Mr12 = new Action(convertor.getIDForPropositionName(sMr12));
		Action Mq21 = new Action(convertor.getIDForPropositionName(sMq21));
		Action Uar2 = new Action(convertor.getIDForPropositionName(sUar2));
		Action Ubq1 = new Action(convertor.getIDForPropositionName(sUbq1));
		
		Action[][] thePlan = new Action[][]{{Lar1, Lbq2}, 
											{Mr12, Mq21},
											{Uar2, Ubq1}};
		
		assertTrue(isGoodPlan(thePlan, plan));
		
		/*bwgp = new BWGraphplan(".\\plplan\\BlockWorld4.txt");
		plan = bwgp.run();
		System.out.println(bwgp.getPlanningOutput());*/
		
		/*try {
			wr = new WorldReader(".\\plplan\\BlockWorld8c.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bwgp = new BWGraphplan(wr.getOpMap(), wr.getFactMap(), wr.getGoalList());
		plan = bwgp.run();
		System.out.println(bwgp.getPlanningOutput());*/
		
		
		/*bwgp = new BWGraphplan(".\\plplan\\BlockWorld8.txt");
		plan = bwgp.run();
		System.out.println(bwgp.getPlanningOutput());
		
		bwgp = new BWGraphplan(".\\plplan\\BlockWorld8b.txt");
		plan = bwgp.run();
		System.out.println(bwgp.getPlanningOutput());*/
	}

	/**
	 * @param thePlan
	 * @param plan
	 */
	private boolean isGoodPlan(Action[][] thePlan, List plan) {

		if(thePlan.length != plan.size()) return false;
		for(int i = 0; i < thePlan[i].length; i++)
		{
			Action[] actList = thePlan[i];
			if(actList.length != ((List)plan.get(i)).size()) return false;
			for(int j = 0; j < actList.length; j++)
			{
				Action act = actList[i];
				if(!((List)plan.get(i)).contains(act)) return false;
			}
		}
		return true;
	}

	public void testExtract() {
	}

	public void testSearch() {
	}

	public void testGoalSubSet() {
	}

	public void testGetResolvers() {
		
		/*BWGraphplan bw = new BWGraphplan(".\\plplan\\world.txt");		
		ArrayList plan = new ArrayList();
		
		ArrayList resolver = bw.getResolvers(planGraph.getLayerAt(3).getActs(),
											 planGraph.getLayerAt(3).getMuActs(),
											 new Proposition("a2"),
											 plan); */
	}

	public void testGetPreconds() {
		ArrayList plan = new ArrayList();
//		plan.add(planGraph.getOps().get(new Action(convertor.getIDForPropositionName(sMr12))));
//		plan.add(planGraph.getOps().get(new Action(convertor.getIDForPropositionName(sLar1))));
//		plan.add(planGraph.getOps().get(new Action(convertor.getIDForPropositionName(sUar1))));
		
		BWGraphplan bw = new BWGraphplan(wr.getOpSet(), wr.getFactSet(), wr.getGoalList());
		List precond = bw.getPreconds(plan);
		
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("r1"))));
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("adj12"))));
		
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("r1"))));
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("a1"))));
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("ur"))));
		
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("r1"))));
		assertTrue(precond.contains(new Proposition(convertor.getIDForPropositionName("ar"))));
	}
}
