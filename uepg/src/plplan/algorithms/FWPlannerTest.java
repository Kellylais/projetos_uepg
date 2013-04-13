package plplan.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plplan.javaapi.StringToByteConvertor;

import junit.framework.TestCase;

/**
 * Example1 class for FWPlannerTest.
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
public class FWPlannerTest extends TestCase {
    
    StringToByteConvertor convertor = null;
	
	private Proposition a = null;
	private Proposition b = null;
	private Proposition c = null;
	private Proposition d = null;
	
	private Proposition a2 = null;
	private Proposition b2 = null;
	private Proposition c2 = null;
	private Proposition d2 = null;
	
	private FWPlanner fg;
	
	private Map map1;
	private Map map2;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(FWPlannerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		map1 = new HashMap();
		map2 = new HashMap();
        convertor = new StringToByteConvertor();
		WorldReader wr = new WorldReader(".\\plplan\\PGTestWorld1.txt", convertor);
		fg = new FWPlanner(wr.getOpSet(), wr.getFactSet(), wr.getGoalList());
        
        a = new Proposition(convertor.getIDForPropositionName("a"));
        b = new Proposition(convertor.getIDForPropositionName("b"));
        c = new Proposition(convertor.getIDForPropositionName("c"));
        d = new Proposition(convertor.getIDForPropositionName("d"));
        
        a2 = new Proposition(convertor.getIDForPropositionName("a"));
        b2 = new Proposition(convertor.getIDForPropositionName("b"));
        c2 = new Proposition(convertor.getIDForPropositionName("c"));
        d2 = new Proposition(convertor.getIDForPropositionName("d"));
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testRun() {



		WorldReader wr;
		try {
		
			wr = new WorldReader(".\\plplan\\BlockWorld8c.txt", convertor);
	
			FWPlanner fwgp = new FWPlanner(wr.getOpSet(), wr.getFactSet(), wr.getGoalList());
			List plan = fwgp.run();
			System.out.println(fwgp.getPlanningOutput());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void testExtract() {
	}

	public final void testSucessor() 
	{

	//	Runtime.getRuntime().gc();
	//	long s = System.currentTimeMillis();
	//	for(int i =0; i <  50000; i++){
			
//			Action actMr12 = (Action)fg.getOps().get(new Action(convertor.getIDForPropositionName("Mr12")));
//			Action actMr21 = (Action)fg.getOps().get(new Action(convertor.getIDForPropositionName("Mr21")));
			Map etat = new HashMap();
			Proposition r1 = new Proposition(convertor.getIDForPropositionName("r1"));
			Proposition r2 = new Proposition(convertor.getIDForPropositionName("r2"));
			Proposition adj12 = new Proposition(convertor.getIDForPropositionName("adj12"));
			etat.put(r1,r1);
			etat.put(adj12,adj12);
//			Map etatSucesseur = fg.sucessor(etat, actMr12);
//			assertTrue(etatSucesseur.size() == 2);
//			assertTrue(etatSucesseur.containsKey(r2));
//			assertTrue(etatSucesseur.containsKey(adj12));
//			etatSucesseur = fg.sucessor(etatSucesseur, actMr21);
//			assertTrue(etatSucesseur.size() == 2);
//			assertTrue(etatSucesseur.containsKey(r1));
//			assertTrue(etatSucesseur.containsKey(adj12));
	//	}
	//	long end  = System.currentTimeMillis();
	//	System.out.println("#####" + (s - end)  + "#####");
	}

	public final void testIsSContainingAllGoalProposition() {
		Map etat = new HashMap();
		Proposition a2 = new Proposition(convertor.getIDForPropositionName("a2"));
		Proposition b1 = new Proposition(convertor.getIDForPropositionName("b1"));
		Proposition ur = new Proposition(convertor.getIDForPropositionName("ur"));
		Proposition uq = new Proposition(convertor.getIDForPropositionName("uq"));
//		assertFalse(fg.isSContainingAllGoalProposition(etat));
		etat.put(a2,a2);
//		assertFalse(fg.isSContainingAllGoalProposition(etat));
		etat.put(b1,b1);
//		assertFalse(fg.isSContainingAllGoalProposition(etat));
		etat.put(ur,ur);
//		assertFalse(fg.isSContainingAllGoalProposition(etat));
		etat.put(uq,uq);
//		assertTrue(fg.isSContainingAllGoalProposition(etat));
	}

	public final void testCalculateWasSeenKey() {
		map1.put(a,a);
		map1.put(b,b);
		map2.put(a2,a2);
		map2.put(b2,b2);
		/*System.out.println("-" + fg.calculateWasSeenKey(map1) + "-");
		System.out.println("-" + fg.calculateWasSeenKey(map2) + "-");
		System.out.println("-" + fg.calculateWasSeenKey(map1).hashCode() + "-");
		System.out.println("-" + fg.calculateWasSeenKey(map2).hashCode() + "-");*/
//		assertTrue(fg.calculateWasSeenKey(map1).equals(fg.calculateWasSeenKey(map2)));
	}

	
	public final void testWasSeen(){
		map1.put(a,a);
		map1.put(b,b);
//		assertFalse(fg.wasSeen(map1));
		map2.put(a,a);
		map2.put(b,b);
//		fg.addToWasSeen(map1);
//		assertTrue(fg.wasSeen(map1));
//		fg.addToWasSeen(map2);
//		assertTrue(fg.wasSeen(map1));
//		assertTrue(fg.wasSeen(map2));
	}

	
}
