package plplan.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plplan.javaapi.StringToByteConvertor;

import junit.framework.TestCase;

/**
 * Example1 class for PlanningGraph.
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
public class PlanningGraphTest extends TestCase {
	
	private Layer layer;
	private Map mapAi;
	private Map mapPi;
	private Map mapAiMutex;
	private Map mapPiMutex;
	
	private Map opMap;
	private Map factMap;
	private List goalList;
	
	private Proposition a;
	private Proposition b;
	private Proposition c;
	private Proposition d;
	
	private Action actionA;
	private Action actionB;
	
	private Action noopA;
	private Action noopB;
	private Action noopC;
	private Action noopD;
	
	private Set precond;
	private Set pos;
	private Set neg;
    
    
    StringToByteConvertor convertor = new StringToByteConvertor();
	
	private PlanningGraph planningGraph;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PlanningGraphTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		a = new Proposition(convertor.getIDForPropositionName("a"));
		b = new Proposition(convertor.getIDForPropositionName("b"));
		c = new Proposition(convertor.getIDForPropositionName("c"));
		d = new Proposition(convertor.getIDForPropositionName("d"));
		
		noopA = new Action(convertor.getIDForActionName("a"),true); // HMM!!!!
		noopB = new Action(convertor.getIDForActionName("b"),true);
		noopC = new Action(convertor.getIDForActionName("c"),true);
		noopD = new Action(convertor.getIDForActionName("d"),true);

		//actions
//		precond = new HashMap();  // Action A : precond :  a,b  effet : d
//		precond.put(a,a);         // Action B : precond :    effet : +a, -b
//		precond.put(b,b);
//		pos = new HashMap();
//		pos.put(d,d);
//		neg = new HashMap();
//		neg.put(b,b);
		actionA = new Action(convertor.getIDForActionName("acta"), precond, pos, neg);
		actionB = new Action(convertor.getIDForActionName("actb"));
		
		// planning graph
		factMap = new HashMap(); // initialement a,b,c
		factMap.put(a,a);
		factMap.put(b,b);
		factMap.put(c,c);
		goalList = new ArrayList();  // le but d
		goalList.add(d);
		opMap = new HashMap();
		opMap.put(actionA,actionA);
		opMap.put(actionB,actionB);
//		planningGraph = new PlanningGraph(opMap, factMap);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateNoopActions() {
		//On fait l'expansion la premi�re fois.
		planningGraph.expandGraph();
		assertTrue(planningGraph.getOps().size() == 5 ); 
//		assertTrue(planningGraph.getOps().containsKey(noopA));
//		assertTrue(planningGraph.getOps().containsKey(noopB));
//		assertTrue(planningGraph.getOps().containsKey(noopC));
//		assertTrue(planningGraph.getOps().containsKey(actionA));
//		assertTrue(planningGraph.getOps().containsKey(actionB));
//		assertFalse(planningGraph.getOps().containsKey(noopD));
		
		// On fait l'expansion d'une autre couche... noopD devrait  s'ajouter
		planningGraph.expandGraph();
//		assertTrue(planningGraph.getOps().containsKey(noopA));
//		assertTrue(planningGraph.getOps().containsKey(noopB));
//		assertTrue(planningGraph.getOps().containsKey(noopC));
//		assertTrue(planningGraph.getOps().containsKey(actionA));
//		assertTrue(planningGraph.getOps().containsKey(actionB));
//		assertTrue(planningGraph.getOps().containsKey(noopD));
	}

	public void testIsPiContainingAllGoalProposition() {
		
		//aucune prop de Pi est dans goal
		assertFalse(planningGraph.isPiContainingAllGoalProposition(goalList));
		
		//Pi contient b qui est dans goal mais pas d.
		goalList.add(b);
		assertFalse(planningGraph.isPiContainingAllGoalProposition(goalList));
		
		//  le but n'est pas atteint
	}

	public void testIsFixedPointLevel() {
		
		WorldReader wr;
		try {
			wr = new WorldReader("plplan/fixedPointTestWorld.txt", convertor);
			PlanningGraph pg = new PlanningGraph(wr.getOpSet(), wr.getFactSet());
			assertFalse(pg.isFixedPointLevel());
			pg.expandGraph();
			assertFalse(pg.isFixedPointLevel());
			pg.expandGraph();
			assertTrue(pg.isFixedPointLevel());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public void testCreatePosOutNegArcs() {
		
		planningGraph.expandGraph();
		//V�rifie apres les arcs de la couche 1
		Layer layer0 = (Layer)planningGraph.getLayers().get(0);
		PropositionPGraph propAlayer0 = layer0.getProps().get(a);
        PropositionPGraph propBlayer0 = layer0.getProps().get(b);
        PropositionPGraph propClayer0 = layer0.getProps().get(c);

		assertTrue(propAlayer0.getOutMap().containsKey(actionA));
		assertTrue(propAlayer0.getOutMap().containsKey(noopA));
		assertTrue(propAlayer0.getOutMap().size() == 2);
		assertTrue(propAlayer0.getNegInMap().size() == 0);
		assertTrue(propAlayer0.getPosInMap().size() == 0);

		assertTrue(propBlayer0.getOutMap().containsKey(actionA));
		assertTrue(propBlayer0.getOutMap().containsKey(noopB));
		assertTrue(propBlayer0.getOutMap().size() == 2);
		assertTrue(propBlayer0.getNegInMap().size() == 0); 
		assertTrue(propBlayer0.getPosInMap().size() == 0);
		
		assertTrue(propClayer0.getOutMap().containsKey(noopC));
		assertTrue(propClayer0.getOutMap().size() == 1);
		assertTrue(propClayer0.getNegInMap().size() == 0);
		assertTrue(propClayer0.getPosInMap().size() == 0);
		
		// V�rifie apres les arcs de la couche 2
		Layer layer1 = (Layer)planningGraph.getLayers().get(1);
        PropositionPGraph propAlayer1 = layer1.getProps().get(a);
        PropositionPGraph propBlayer1 = layer1.getProps().get(b); 
        PropositionPGraph propClayer1 = layer1.getProps().get(c);
        PropositionPGraph propDlayer1 = layer1.getProps().get(d);
		
		assertTrue(propAlayer1.getPosInMap().containsKey(noopA));
		assertTrue(propAlayer1.getPosInMap().size() == 1);
		assertTrue(propAlayer1.getNegInMap().size() == 0);
		assertTrue(propAlayer1.getOutMap().size() == 0);

		assertTrue(propBlayer1.getNegInMap().containsKey(actionA));
		assertTrue(propBlayer1.getNegInMap().size() == 1);
		assertTrue(propBlayer1.getPosInMap().containsKey(noopB));
		assertTrue(propBlayer1.getPosInMap().size() == 1);
		assertTrue(propBlayer1.getOutMap().size() == 0);
				
		assertTrue(propDlayer1.getPosInMap().containsKey(actionA));
		assertTrue(propDlayer1.getPosInMap().size() == 1);
		assertTrue(propDlayer1.getNegInMap().size() == 0);
		assertTrue(propDlayer1.getOutMap().size() == 0);
		
		assertTrue(propClayer1.getPosInMap().containsKey(noopC));
		assertTrue(propClayer1.getPosInMap().size() == 1);
		assertTrue(propClayer1.getNegInMap().size() == 0);
		assertTrue(propClayer1.getOutMap().size() == 0);
		
		//-------------------------------------------
		// On v�rifie l'�tat de MuPi et MuAi, dans cet exemple quand m�me assez trivial.
		
		// layer 0
		assertTrue(layer0.getMuProps().size() == 0);
		assertTrue(layer0.getMuActs().size() == 0);
		
		// layer 1
		assertTrue(layer1.getMuActs().size() == 2);
		assertTrue(layer1.getMuActs().containsKey(actionA));
		assertTrue(layer1.getMuActs().containsKey(noopB));
		
		assertTrue(layer1.getMuProps().size() == 2); 
		assertTrue(layer1.getMuProps().containsKey(b));
		assertTrue(layer1.getMuProps().containsKey(d));
	}

	public void testGetLayers() {
		assertNotNull(planningGraph.getLayers());
	}
	
	public void testGetFactMap() {
		assertEquals(factMap, planningGraph.getFacts());
	}

	public void testGetOpMap() {
		assertEquals(opMap, planningGraph.getOps());
	}
	
	public void testExpandGraph(){
		WorldReader wr;
		try {
		wr = new WorldReader("plplan//PGTestWorld1.txt", convertor);
		
		planningGraph = new PlanningGraph(wr.getOpSet(), wr.getFactSet());

//		Action Mr12 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Mr12")));
//		Action Mr21 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Mr21")));
//		Action Mq12 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Mq12")));
//		Action Mq21 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Mq21")));
//		Action Lar1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lar1")));
//		Action Lar2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lar2")));
//		Action Laq1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Laq1")));
//		Action Laq2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Laq2")));
//		Action Lbr1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lbr1")));
//		Action Lbr2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lbr2")));
//		Action Lbq1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lbq1")));
//		Action Lbq2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Lbq2")));
//		Action Uar1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Uar1")));
//		Action Uar2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Uar2")));
//		Action Uaq1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Uaq1")));
//		Action Uaq2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Uaq2")));
//		Action Ubr1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Ubr1")));
//		Action Ubr2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Ubr2")));
//		Action Ubq1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Ubq1")));
//		Action Ubq2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForActionName("Ubq2")));
		
		Action noopr1 = new Action(convertor.getIDForActionName("r1"), true);  
		Action noopr2 = new Action(convertor.getIDForActionName("r2"), true);
		Action noopq1 = new Action(convertor.getIDForActionName("q1"), true);
		Action noopq2 = new Action(convertor.getIDForActionName("q2"), true);		
		Action noopa1 = new Action(convertor.getIDForActionName("a1"), true);
		Action noopa2 = new Action(convertor.getIDForActionName("a2"), true);
		Action noopar = new Action(convertor.getIDForActionName("ar"), true);
		Action noopaq = new Action(convertor.getIDForActionName("aq"), true);
		Action noopb1 = new Action(convertor.getIDForActionName("b1"), true);
		Action noopb2 = new Action(convertor.getIDForActionName("b2"), true);
		Action noopbr = new Action(convertor.getIDForActionName("br"), true);
		Action noopbq = new Action(convertor.getIDForActionName("bq"), true);
		Action noopur = new Action(convertor.getIDForActionName("ur"), true);
		Action noopuq = new Action(convertor.getIDForActionName("uq"), true);
		Action noopadj12 = new Action(convertor.getIDForActionName("adj12"), true);
		
//		Action[] allActions = new Action[]{Mr12, Mr21, Mq12, Mq21,
//							 			   Lar1, Lar2, Laq1, Laq2,
//										   Lbr1, Lbr2, Lbq1, Lbq2,
//										   Uar1, Uar2, Uaq1, Uaq2,
//										   Ubr1, Ubr2, Ubq1, Ubq2,
//										   noopr1, noopr2, noopq1, noopq2,
//										   noopa1, noopa2, noopar, noopaq,
//										   noopb1, noopb2, noopbr, noopbq,
//										   noopur, noopuq, noopadj12 };

		Proposition r1 = new Proposition(convertor.getIDForPropositionName("r1"));
		Proposition r2 = new Proposition(convertor.getIDForPropositionName("r2"));
		Proposition q1 = new Proposition(convertor.getIDForPropositionName("q1"));
		Proposition q2 = new Proposition(convertor.getIDForPropositionName("q2"));		
		Proposition a1 = new Proposition(convertor.getIDForPropositionName("a1"));
		Proposition a2 = new Proposition(convertor.getIDForPropositionName("a2"));
		Proposition ar = new Proposition(convertor.getIDForPropositionName("ar"));
		Proposition aq = new Proposition(convertor.getIDForPropositionName("aq"));
		Proposition b1 = new Proposition(convertor.getIDForPropositionName("b1"));
		Proposition b2 = new Proposition(convertor.getIDForPropositionName("b2"));
		Proposition br = new Proposition(convertor.getIDForPropositionName("br"));
		Proposition bq = new Proposition(convertor.getIDForPropositionName("bq"));
		Proposition ur = new Proposition(convertor.getIDForPropositionName("ur"));
		Proposition uq = new Proposition(convertor.getIDForPropositionName("uq"));
		Proposition adj12 = new Proposition(convertor.getIDForPropositionName("adj12"));
		
		Proposition[] allProps = new Proposition[]{r1,r2,q1,q2,
												   a1,a2,ar,aq,
												   b1,b2,br,bq,
												   ur,uq,adj12};
			
		Action mutexNodes[];
		
		assertTrue(wr.getOpSet().size() == 20); // 20 actions initialement
		assertTrue(wr.getFactSet().size() == 7); // 6 faits  + adj12 initialement
				
		// COUCHE 0 Pi, Ai, muAi, muPi
		Layer layer = (Layer)planningGraph.getLayers().get(0);
		assertTrue(layer.getMuActs().size() == 0); // 0 �l�ments dans MuAi
		assertTrue(layer.getMuProps().size() == 0); // 0 �l�ments dans MuPi
		assertTrue(layer.getActs().size() == 0); // 0 actions
		
		assertTrue(layer.getProps().size() == 7); // 7 faits initialement
		assertTrue(layer.getProps().containsKey(r1));
		assertTrue(layer.getProps().containsKey(q2));
		assertTrue(layer.getProps().containsKey(a1));
		assertTrue(layer.getProps().containsKey(b2));
		assertTrue(layer.getProps().containsKey(ur));
		assertTrue(layer.getProps().containsKey(uq));
		assertTrue(layer.getProps().containsKey(adj12));
		
		// Couche 1 Pi, Ai, muAi, muPi
		planningGraph.expandGraph();
		layer = (Layer)planningGraph.getLayers().get(1);
	
		//A1
		assertTrue(layer.getActs().size() == 11); // 4 actions + 7 noop = 11
//		assertTrue(layer.getActs().containsKey(Mr12));
//		assertTrue(layer.getActs().containsKey(Mq21));
//		assertTrue(layer.getActs().containsKey(Lar1));
//		assertTrue(layer.getActs().containsKey(Lbq2));
//		assertTrue(layer.getActs().containsKey(noopr1));
//		assertTrue(layer.getActs().containsKey(noopq2));
//		assertTrue(layer.getActs().containsKey(noopa1));
//		assertTrue(layer.getActs().containsKey(noopb2));
//		assertTrue(layer.getActs().containsKey(noopur));
//		assertTrue(layer.getActs().containsKey(noopuq));
//		assertTrue(layer.getActs().containsKey(noopuq));
		
		//P1
		assertTrue(layer.getProps().size() == 11); // 11 faits 
		assertTrue(layer.getProps().containsKey(r1));
		assertTrue(layer.getProps().containsKey(r2));
		assertTrue(layer.getProps().containsKey(q1));
		assertTrue(layer.getProps().containsKey(q2));
		assertTrue(layer.getProps().containsKey(a1));
		assertTrue(layer.getProps().containsKey(ar));
		assertTrue(layer.getProps().containsKey(b2));
		assertTrue(layer.getProps().containsKey(bq));
		assertTrue(layer.getProps().containsKey(ur));
		assertTrue(layer.getProps().containsKey(uq));
		assertTrue(layer.getProps().containsKey(adj12));
		
		//MuA1
		assertTrue(layer.getMuActs().size() == 10); // // 4 action en conlit + 6 noop en conflit
		
//		mutexNodes = new Action[] {Lar1, noopr1};
//		testMutexAction(layer.getMuActs(), Mr12, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Mr12, noopur, noopa1};
//		testMutexAction(layer.getMuActs(), Lar1, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Mq21, noopuq, noopb2};
//		testMutexAction(layer.getMuActs(), Lbq2, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Lbq2, noopq2};
//		testMutexAction(layer.getMuActs(), Mq21, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Lar1};
//		testMutexAction(layer.getMuActs(), noopa1, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Lar1};
//		testMutexAction(layer.getMuActs(), noopur, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Lbq2};
//		testMutexAction(layer.getMuActs(), noopuq, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Mr12};
//		testMutexAction(layer.getMuActs(), noopr1, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Mq21};
//		testMutexAction(layer.getMuActs(), noopq2, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {Lbq2};
//		testMutexAction(layer.getMuActs(), noopb2, mutexNodes, allActions);
		
		//MuP1
		assertTrue(layer.getMuProps().size() == 10);

        Proposition mutexNodesProp[];
		
        mutexNodesProp = new Proposition[] {r1, ar};
        testMutexProposition(layer.getMuProps(), r2, mutexNodesProp, allProps);
		
        mutexNodesProp = new Proposition[] {r2};
        testMutexProposition(layer.getMuProps(), r1, mutexNodesProp, allProps);
		
        mutexNodesProp = new Proposition[] {a1, ur, r2};
        testMutexProposition(layer.getMuProps(), ar, mutexNodesProp, allProps);
		
        mutexNodesProp = new Proposition[] {q2, bq};
		testMutexProposition(layer.getMuProps(), q1, mutexNodesProp, allProps);

		assertTrue(layer.getMuProps().containsKey(q2));
		assertTrue(layer.getMuProps().containsKey(bq));
		assertTrue(layer.getMuProps().containsKey(a1));
		assertTrue(layer.getMuProps().containsKey(ur));
		assertTrue(layer.getMuProps().containsKey(b2));
		assertTrue(layer.getMuProps().containsKey(uq));
		
		// Couche 2
		planningGraph.expandGraph();
		Layer layer2 = (Layer)planningGraph.getLayers().get(2);
	
		assertTrue(layer2.getActs().size() == 21); // 10 actions + 11 noop = 21
//		assertTrue(layer2.getActs().containsKey(Mr12));
//		assertTrue(layer2.getActs().containsKey(Mr21));
//		assertTrue(layer2.getActs().containsKey(Mq12));
//		assertTrue(layer2.getActs().containsKey(Mq21));
//		assertTrue(layer2.getActs().containsKey(Lar1));
//		assertTrue(layer2.getActs().containsKey(Lbr2));
//		assertTrue(layer2.getActs().containsKey(Laq1));
//		assertTrue(layer2.getActs().containsKey(Lbq2));
//		assertTrue(layer2.getActs().containsKey(Uar1));
//		assertTrue(layer2.getActs().containsKey(Ubq2));
//		assertTrue(layer2.getActs().containsKey(noopr1));
//		assertTrue(layer2.getActs().containsKey(noopr2));
//		assertTrue(layer2.getActs().containsKey(noopq1));
//		assertTrue(layer2.getActs().containsKey(noopq2));
//		assertTrue(layer2.getActs().containsKey(noopa1));
//		assertTrue(layer2.getActs().containsKey(noopar));
//		assertTrue(layer2.getActs().containsKey(noopb2));
//		assertTrue(layer2.getActs().containsKey(noopbq));
//		assertTrue(layer2.getActs().containsKey(noopur));
//		assertTrue(layer2.getActs().containsKey(noopuq));
//		assertTrue(layer2.getActs().containsKey(noopadj12));
		
		assertTrue(layer2.getProps().size() == 13); // 13 faits  ( tel que dans la fig 6.4)
		assertTrue(layer2.getProps().containsKey(r1));
		assertTrue(layer2.getProps().containsKey(r2));
		assertTrue(layer2.getProps().containsKey(q1));
		assertTrue(layer2.getProps().containsKey(q2));
		assertTrue(layer2.getProps().containsKey(a1));
		assertTrue(layer2.getProps().containsKey(ar));
		assertTrue(layer2.getProps().containsKey(aq));
		assertTrue(layer2.getProps().containsKey(b2));
		assertTrue(layer2.getProps().containsKey(br));
		assertTrue(layer2.getProps().containsKey(bq));
		assertTrue(layer2.getProps().containsKey(ur));
		assertTrue(layer2.getProps().containsKey(uq));
		assertTrue(layer2.getProps().containsKey(adj12));
		
		// tel que dans la table 6.1
		assertTrue(layer2.getMuProps().size() == 12);
		assertTrue(layer2.getMuProps().containsKey(a1));
		assertTrue(layer2.getMuProps().containsKey(ar));
		assertTrue(layer2.getMuProps().containsKey(aq));
		assertTrue(layer2.getMuProps().containsKey(b2));
		assertTrue(layer2.getMuProps().containsKey(br));
		assertTrue(layer2.getMuProps().containsKey(bq));
		assertTrue(layer2.getMuProps().containsKey(q1));
		assertTrue(layer2.getMuProps().containsKey(q2));
		assertTrue(layer2.getMuProps().containsKey(r1));
		assertTrue(layer2.getMuProps().containsKey(r2));
		assertTrue(layer2.getMuProps().containsKey(ur));
		assertTrue(layer2.getMuProps().containsKey(uq));
		
		// Je n'ai pas compter ceci
//		assertTrue(layer2.getMuActs().containsKey(Mq12));
//		assertTrue(layer2.getMuActs().containsKey(Mq21));
//		assertTrue(layer2.getMuActs().containsKey(Mr12));
//		assertTrue(layer2.getMuActs().containsKey(Mr21));
//		assertTrue(layer2.getMuActs().containsKey(Laq1));
//		assertTrue(layer2.getMuActs().containsKey(Lar1));
//		assertTrue(layer2.getMuActs().containsKey(Lbr2));
//		assertTrue(layer2.getMuActs().containsKey(Lbq2));
//		assertTrue(layer2.getMuActs().containsKey(Uar1));
//		assertTrue(layer2.getMuActs().containsKey(Ubq2));
		assertTrue(layer2.getMuActs().containsKey(noopa1));
		assertTrue(layer2.getMuActs().containsKey(noopur));
		assertTrue(layer2.getMuActs().containsKey(noopuq));
		assertTrue(layer2.getMuActs().containsKey(noopr1));
		assertTrue(layer2.getMuActs().containsKey(noopq2));
		assertTrue(layer2.getMuActs().containsKey(noopb2));
		
//        MutexAction mutexMQ12 = layer2.getMuActs().get(Mq12);
//		assertTrue(mutexMQ12.containsNode(Mq21));
//		assertTrue(mutexMQ12.containsNode(Laq1));
//		assertTrue(mutexMQ12.containsNode(Lbq2));
//		assertTrue(mutexMQ12.containsNode(Ubq2));
//		assertTrue(mutexMQ12.containsNode(noopq1));
		
//		MutexAction mutexMQ21 = layer2.getMuActs().get(Mq21);
//		assertTrue(mutexMQ21.containsNode(Lbq2));
//		assertTrue(mutexMQ21.containsNode(Ubq2));
//		assertTrue(mutexMQ21.containsNode(Laq1));
//		assertTrue(mutexMQ21.containsNode(Mq12));
//		assertTrue(mutexMQ12.containsNode(noopq2));
		
		// Couche 3
		planningGraph.expandGraph();
		Layer layer3 = (Layer)planningGraph.getLayers().get(3);
	
		//assertTrue(layer3.getActs().size() == 26); 
//		assertTrue(layer3.getActs().containsKey(Mr12));
//		assertTrue(layer3.getActs().containsKey(Mr21));
//		assertTrue(layer3.getActs().containsKey(Mq12));
//		assertTrue(layer3.getActs().containsKey(Mq21));
//		assertTrue(layer3.getActs().containsKey(Lar1));
//		assertTrue(layer3.getActs().containsKey(Uar1));
//		assertTrue(layer3.getActs().containsKey(Uar2));
//		assertTrue(layer3.getActs().containsKey(Uaq1));
//		assertTrue(layer3.getActs().containsKey(Lbr2));
//		assertTrue(layer3.getActs().containsKey(Laq1));
//		assertTrue(layer3.getActs().containsKey(Lbq2));
//		assertTrue(layer3.getActs().containsKey(Ubq1));
//		assertTrue(layer3.getActs().containsKey(Ubq2));
//		assertTrue(layer3.getActs().containsKey(Ubr2));
//		assertTrue(layer3.getActs().containsKey(Mr21));
//		assertTrue(layer3.getActs().containsKey(Mr21));
//		assertTrue(layer3.getActs().containsKey(noopr1));
//		assertTrue(layer3.getActs().containsKey(noopr2));
//		assertTrue(layer3.getActs().containsKey(noopq1));
//		assertTrue(layer3.getActs().containsKey(noopq2));
//		assertTrue(layer3.getActs().containsKey(noopa1));
//		assertTrue(layer3.getActs().containsKey(noopar));
//		assertTrue(layer3.getActs().containsKey(noopb2));
//		assertTrue(layer3.getActs().containsKey(noopbq));
//		assertTrue(layer3.getActs().containsKey(noopur));
//		assertTrue(layer3.getActs().containsKey(noopuq));
//		assertTrue(layer3.getActs().containsKey(noopbr));
//		assertTrue(layer3.getActs().containsKey(noopaq));
//		assertTrue(layer3.getActs().containsKey(noopadj12));
		
		
		layer = (Layer)planningGraph.getLayers().get(1);
		// Et pour terminer Je v�rifie une coupe d'arcs de la couche P1
		r1 = (Proposition)layer.getProps().get(r1);
		/*assertTrue(r1.getNegInMap().size() == 1); // Mr12
		assertTrue(r1.getPosInMap().size() == 1); // le noop
		assertTrue(r1.getOutMap().size() == 4); 
		
		 r2 = (Proposition)layer.getProps().get(r1);
		assertTrue(r2.getNegInMap().size() == 1); // Mr12
		assertTrue(r2.getPosInMap().size() == 1); // le noop
		assertTrue(r2.getOutMap().size() == 4); // Mr21, LBR2, UAR2, et noopR2
		*/
		testExpandGraph2();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testExpandGraph2(){
		
		WorldReader wr;
		try {
			wr = new WorldReader("plplan/testWorld.txt", convertor);
		
		PlanningGraph pg = new PlanningGraph(wr.getOpSet(), wr.getFactSet());
		
//		Action op1 = (Action)wr.getOpSet().get(new Action(convertor.getIDForPropositionName("op1")));
//		Action op2 = (Action)wr.getOpSet().get(new Action(convertor.getIDForPropositionName("op2")));
//		Action op3 = (Action)wr.getOpSet().get(new Action(convertor.getIDForPropositionName("op3")));
		Action noopa = new Action(convertor.getIDForPropositionName("a"), true);
		Action noopb = new Action(convertor.getIDForPropositionName("b"), true);
		Action noopc = new Action(convertor.getIDForPropositionName("c"), true);
		
//		Action[] allActions = new Action[]{op1, op2, op3,noopa, noopb, noopc};
		
		Proposition[] allProps = new Proposition[]{a,b,c};
		
		Action mutexNodes[];
        Proposition mutexNodesProp[];
		
		//Layer 0
		assertTrue(((Layer)pg.getLayers().get(0)).getProps().containsKey(a));
		
		//Layer 1
		pg.expandGraph();
		Layer layer = (Layer)pg.getLayers().get(1);
		
		//Example1 A1
		assertTrue(layer.getActs().size() == 3);
//		assertTrue(layer.getActs().containsKey(noopa));
//		assertTrue(layer.getActs().containsKey(op1));
//		assertTrue(layer.getActs().containsKey(op2));
		
		//Example1 P1
		assertTrue(layer.getProps().size() == 3);
		assertTrue(layer.getProps().containsKey(a));
		assertTrue(layer.getProps().containsKey(b));
		assertTrue(layer.getProps().containsKey(c));
		
		//Example1 muA1
		assertTrue(layer.getMuActs().size() == 3);
		
//		mutexNodes = new Action[] {op2, noopa};
//		testMutexAction(layer.getMuActs(), op1, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op1};
//		testMutexAction(layer.getMuActs(), op2, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op1};
//		testMutexAction(layer.getMuActs(), noopa, mutexNodes, allActions);

		
		//Example1 muP1
		assertTrue(layer.getMuProps().size() == 2);
		
        mutexNodesProp = new Proposition[] {c};
		testMutexProposition(layer.getMuProps(), a, mutexNodesProp, allProps);
		
        mutexNodesProp = new Proposition[] {a};
        testMutexProposition(layer.getMuProps(), c, mutexNodesProp, allProps);

		
		//Layer 2
		pg.expandGraph();
		assertFalse(pg.isFixedPointLevel());
		layer = (Layer)pg.getLayers().get(2);
		
		//Example1 A2
		assertTrue(layer.getActs().size() == 6);
//		assertTrue(layer.getActs().containsKey(noopa));
//		assertTrue(layer.getActs().containsKey(noopb));
//		assertTrue(layer.getActs().containsKey(noopc));
//		assertTrue(layer.getActs().containsKey(op1));
//		assertTrue(layer.getActs().containsKey(op2));
//		assertTrue(layer.getActs().containsKey(op3));
		
		//Example1 P2
		assertTrue(layer.getProps().size() == 3);
		assertTrue(layer.getProps().containsKey(a));
		assertTrue(layer.getProps().containsKey(b));
		assertTrue(layer.getProps().containsKey(c));
		
		//Example1 muA2
		assertTrue(layer.getMuActs().size() == 6);
		
//		mutexNodes = new Action[] {op2, op3, noopa, noopc};
//		testMutexAction(layer.getMuActs(), op1, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op1, op3, noopc};
//		testMutexAction(layer.getMuActs(), op2, mutexNodes, allActions);
			
//		mutexNodes = new Action[] {op1, op2, noopb};
//		testMutexAction(layer.getMuActs(), op3, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op1, noopc};
//		testMutexAction(layer.getMuActs(), noopa, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op3};
//		testMutexAction(layer.getMuActs(), noopb, mutexNodes, allActions);
		
//		mutexNodes = new Action[] {op1, op2, noopa};
//		testMutexAction(layer.getMuActs(), noopc, mutexNodes, allActions);
		
		//Example1 muP2
		assertTrue(layer.getMuProps().size() == 0);
		
		//Layer 3
		pg.expandGraph();
		assertTrue(pg.isFixedPointLevel());
		layer = (Layer)pg.getLayers().get(3);
		
		//Example1 A3
		assertTrue(layer.getActs().size() == 6);
//		assertTrue(layer.getActs().containsKey(noopa));
//		assertTrue(layer.getActs().containsKey(noopb));
//		assertTrue(layer.getActs().containsKey(noopc));
//		assertTrue(layer.getActs().containsKey(op1));
//		assertTrue(layer.getActs().containsKey(op2));
//		assertTrue(layer.getActs().containsKey(op3));
		
		//Example1 P3
		assertTrue(layer.getProps().size() == 3);
		assertTrue(layer.getProps().containsKey(a));
		assertTrue(layer.getProps().containsKey(b));
		assertTrue(layer.getProps().containsKey(c));
		
		//Example1 muA3
		assertTrue(layer.getMuActs().size() == 5);
		
//		mutexNodes = new Action[] {op2, op3, noopa};
//		testMutexAction(layer.getMuActs(), op1, mutexNodes, allActions);

//		mutexNodes = new Action[] {op1, op3};
//		testMutexAction(layer.getMuActs(), op2, mutexNodes, allActions);

//		mutexNodes = new Action[] {op1, op2, noopb};
//		testMutexAction(layer.getMuActs(), op3, mutexNodes, allActions);

//		mutexNodes = new Action[] {op1};
//		testMutexAction(layer.getMuActs(), noopa, mutexNodes, allActions);
		
	//	mutexNodes = new Action[] {op3};
//		testMutexAction(layer.getMuActs(), noopb, mutexNodes, allActions);
		
		mutexNodes = new Action[] {};
//		testMutexAction(layer.getMuActs(), noopc, mutexNodes, allActions);
		
		//muP3
		assertTrue(layer.getMuProps().size() == 0);
		
		//Layer 4
		pg.expandGraph();
		assertTrue(pg.isFixedPointLevel());
		layer = (Layer)pg.getLayers().get(4);
		pg.isFixedPointLevel();
		
		//Example1 A4
		assertTrue(layer.getActs().size() == 6);
	//	assertTrue(layer.getActs().containsKey(noopa));
//		assertTrue(layer.getActs().containsKey(noopb));
//		assertTrue(layer.getActs().containsKey(noopc));
//		assertTrue(layer.getActs().containsKey(op1));
//		assertTrue(layer.getActs().containsKey(op2));
//		assertTrue(layer.getActs().containsKey(op3));
		
		//Example1 P4
		assertTrue(layer.getProps().size() == 3);
		assertTrue(layer.getProps().containsKey(a));
		assertTrue(layer.getProps().containsKey(b));
		assertTrue(layer.getProps().containsKey(c));
		
		//Example1 muA4
		assertTrue(layer.getMuActs().size() == 5);
		
//		assertTrue(layer.getMuActs().containsKey(op1));
//		mutexNodes = new Action[] {op2, op3, noopa};
//		testMutexAction(layer.getMuActs(), op1, mutexNodes, allActions);

//		assertTrue(layer.getMuActs().containsKey(op2));
//		mutexNodes = new Action[] {op1, op3};
//		testMutexAction(layer.getMuActs(), op2, mutexNodes, allActions);

//		assertTrue(layer.getMuActs().containsKey(op3));
//		mutexNodes = new Action[] {op1, op2, noopb};
//		testMutexAction(layer.getMuActs(), op3, mutexNodes, allActions);

		assertTrue(layer.getMuActs().containsKey(noopa));
//		mutexNodes = new Action[] {op1};
//		testMutexAction(layer.getMuActs(), noopa, mutexNodes, allActions);
		
		assertTrue(layer.getMuActs().containsKey(noopb));
//		mutexNodes = new Action[] {op3};
//		testMutexAction(layer.getMuActs(), noopb, mutexNodes, allActions);
		
		assertFalse(layer.getMuActs().containsKey(noopc));
		mutexNodes = new Action[] {};
//		testMutexAction(layer.getMuActs(), noopc, mutexNodes, allActions);
		
		//muP4
		assertTrue(layer.getMuProps().size() == 0);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void testMutexAction(Map<Action,MutexAction> muMap, Action node, Action[] mutexNodes, Action[] allNodes)
	{
		if(mutexNodes.length > 0)
		{
			assertTrue(muMap.containsKey(node));
		}
		else 
		{
			assertFalse(muMap.containsKey(node));
			return;
		}
		
		HashMap<Action,Action> mutexNodeMap = new HashMap<Action,Action>(5);
		for(int i = 0; i < mutexNodes.length; i++)
		{
			mutexNodeMap.put(mutexNodes[i], mutexNodes[i]);
		}
		
		for(int i = 0; i < allNodes.length; i++)
		{
			if(mutexNodeMap.containsKey(allNodes[i])) 
				assertTrue((muMap.get(node)).containsNode(allNodes[i]));
			else
				assertFalse((muMap.get(node)).containsNode(allNodes[i]));
		}
	}
    
    
    private void testMutexProposition(Map<Proposition,MutexProp> muMap, Proposition node, Proposition[] mutexNodes, Proposition[] allNodes)
    {
        if(mutexNodes.length > 0)
        {
            assertTrue(muMap.containsKey(node));
        }
        else 
        {
            assertFalse(muMap.containsKey(node));
            return;
        }
        
        HashMap<Proposition,Proposition> mutexNodeMap = new HashMap<Proposition,Proposition>(5);
        for(int i = 0; i < mutexNodes.length; i++)
        {
            mutexNodeMap.put(mutexNodes[i], mutexNodes[i]);
        }
        
        for(int i = 0; i < allNodes.length; i++)
        {
            if(mutexNodeMap.containsKey(allNodes[i])) 
                assertTrue((muMap.get(node)).containsNode(allNodes[i]));
            else
                assertFalse((muMap.get(node)).containsNode(allNodes[i]));
        }
    }
		

	public final void testGetNbMutexPair(){
		Map<Proposition, MutexProp> mutexsMap = new HashMap<Proposition, MutexProp>();
		MutexProp mutexA = new MutexProp(a);
        MutexProp mutexB = new MutexProp(b);
		mutexsMap.put(a,mutexA);
		mutexsMap.put(b,mutexB);
		assertTrue(planningGraph.getNbMutexPair(mutexsMap) == 0);
		mutexA.add(b);
		mutexA.add(b);
		assertTrue(planningGraph.getNbMutexPair(mutexsMap) == 1);
		mutexB.add(a);
		assertTrue(planningGraph.getNbMutexPair(mutexsMap) == 2);
	}
}
