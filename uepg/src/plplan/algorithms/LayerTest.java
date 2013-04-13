package plplan.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plplan.javaapi.StringToByteConvertor;

import junit.framework.TestCase;

/**
 * Example1 class for LayerTest.
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
public class LayerTest extends TestCase {
	
	private Layer layer;
	private Map mapAi;
	private Map mapPi;
	private Map mapMuProps;
	private Map mapMuActs;
	
	private MutexProp mutexA;

	private Proposition a;

	private Proposition b;

	private Proposition c;

	private List lista;
	
	private List listb;
	
	private List listc;
	
	private Action actA;
    
    private StringToByteConvertor convertor;
	

	public static void main(String[] args) {
		junit.textui.TestRunner.run(LayerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		layer = new Layer(10);
        
        convertor =  new StringToByteConvertor();
		
		a = new Proposition(convertor.getIDForPropositionName("a"));

		b = new Proposition(convertor.getIDForPropositionName("b"));

		c = new Proposition(convertor.getIDForPropositionName("c"));
			
		actA = new Action(convertor.getIDForPropositionName("acta"));

		lista = new ArrayList();
		lista.add(a);
		
		listb = new ArrayList();
		listb.add(b);
		
		listc = new ArrayList();
		listc.add(c);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLayer() {
		assertNotNull(layer.getActs());
		assertNotNull(layer.getProps());
		assertNotNull(layer.getMuProps());
		assertNotNull(layer.getMuActs());
	}
	
	public void testAddNodeMutex(){
		assertTrue(layer.getMuActs().size() == 0);
		assertTrue(layer.getMuProps().size() == 0);
					
		MutexProp mutexAC = new MutexProp(a);
		layer.getMuProps().put(a,mutexAC);
		assertTrue(layer.getMuProps().size() == 1);
        MutexProp depA = layer.getMuProps().get(a);
		assertTrue(depA.getNode() == a);
		assertFalse(depA.getNode() == b);
		assertFalse(depA.getNode() == c);
		assertFalse(depA.containsAll(listc.iterator()));
		mutexAC.add(c);
		assertTrue(depA.containsAll(listc.iterator()));
		
		
		layer.addNodeMutex(layer.getMuProps(), a, b);
		assertTrue(layer.getMuProps().size() == 2);
		assertTrue(depA.getNode() == a);
		assertTrue(depA.containsAll(listb.iterator()));
		assertTrue(depA.containsAll(listc.iterator()));
		
        MutexProp depB = layer.getMuProps().get(b);
		assertTrue(depB.getNode() == b);
		assertFalse(depB.containsAll(listb.iterator()));
		assertFalse(depB.containsAll(listc.iterator()));
		assertTrue(depB.containsAll(lista.iterator()));
	}
	
	public void testSimpleAvecActionA(){
        MutexProp mutexActa = new MutexProp(a);
		layer.getMuProps().put(a,mutexActa);
        MutexProp test = layer.getMuProps().get(a);
		assertTrue(test.getNode() == a);
	}

	public void testGetActsWithoutNoop(){
		WorldReader wr;
		try {
			wr = new WorldReader("testWorld.txt", convertor);
			PlanningGraph pg = new PlanningGraph(wr.getOpSet(), wr.getFactSet());
			pg.expandGraph();
			pg.expandGraph();
			Layer layer0 = pg.getLayerAt(0);
			Set<Action> actsWithoutNoop = layer0.getActsWithoutNoop();
			Set<Action> acts = layer0.getActsWithoutNoop();
			
			Iterator<Action> iter = acts.iterator();
			while (iter.hasNext()) {
				Action action = iter.next();
				if(action.isNoop()){
					assertTrue(!actsWithoutNoop.contains(action));
				}else{
					assertFalse(!actsWithoutNoop.contains(action));
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
