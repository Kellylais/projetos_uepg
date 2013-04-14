/*
 * Created on 2004-07-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package plplan.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Example1 class for Action.
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
public class ActionTest extends TestCase {
	
	private Set precond;
	private Set pos;
	private Set<Proposition>neg;
	private Action actionA;
	private Action actionB;
	private Action actionC;
	
	private Proposition a;
	private Proposition b;
	private Proposition c;
	private Proposition d;
    
    private byte b1 = 1;
    private byte b2 = 2;
    private byte b3 = 3;
    private byte b4 = 4;
    private byte b5 = 5;
    private byte b6 = 6;
    private byte b7 = 7;
	
	private Map mapB;
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(ActionTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
        
		actionA = new Action(b1);
		precond = actionA.getPrecondMap();
		pos = actionA.getPosEffectSet();
		neg = actionA.getNegEffectSet();
		actionB = new Action(b2);
		actionC = new Action(b3);
		
		a = new Proposition(b4);
		b = new Proposition(b5);
		c = new Proposition(b6);
		d = new Proposition(b7);
		
		mapB = new HashMap();
		mapB.put(b,b);
	}
	
	public void testConstructeur(){
		assertEquals(actionB.getName(), 2);
		assertEquals(actionA.getName(), 1);
		assertEquals(actionA.getPrecondMap(), precond);
		assertEquals(actionA.getPosEffectSet(), pos);
		assertEquals(actionA.getNegEffectSet(), neg);
	}

	public final void testIsApplicableForPropositions() {
		neg.add(b);
		pos.add(b);
		assertTrue(actionA.isApplicableForPropositions(new HashMap()));
		precond.add(b);
		assertFalse(actionA.isApplicableForPropositions(new HashMap()));
		assertTrue(actionA.isApplicableForPropositions(mapB));
	}

	public final void testIsIndependentOf(){
		// quelque tests d'indépendances simples
		assertTrue(actionA.isIndependentOf(actionA));
		assertTrue(actionA.isIndependentOf(actionB));
		assertFalse(actionA.isDependentOf(actionA));
		assertFalse(actionA.isDependentOf(actionB));
		
		// negA()={b} et negB()={b} - indépendance
		neg.add(b);
		actionB.getNegEffectSet().add(b);
		assertTrue(actionA.isIndependentOf(actionB));
		
		// posA()={b} et posB()={b} - dépendance
		actionB.getPosEffectSet().add(b);
		assertFalse(actionA.isIndependentOf(actionB));
		
		// posA()={b} et C vide  - indépendance
		assertTrue(actionA.isIndependentOf(actionC));
		
		// posA()={b} et precondC()={b} - dépendance
		actionC.getPrecondMap().add(b);
		assertFalse(actionA.isIndependentOf(actionC));
	}
	
	public final void testHasMutexPreconditions(){
		Map<Proposition,MutexProp> muProps = new HashMap<Proposition,MutexProp>();
        MutexProp mutexA = new MutexProp(a);
		muProps.put(a, mutexA);
		pos.add(a);
		neg.add(a);
		assertFalse(actionA.hasMutexPreconditions(muProps));
		precond.add(b);
		assertFalse(actionA.hasMutexPreconditions(muProps));
		precond.add(a);
		precond.add(c);
		assertFalse(actionA.hasMutexPreconditions(muProps));
		mutexA.add(b);
		assertTrue(actionA.hasMutexPreconditions(muProps));
	}
	
	public final void testIsNoop(){
        byte b12 = 12;
        byte b13 = 13;
		Action act1 = new Action(b12);
		assertFalse(act1.isNoop());
		Action act2 = new Action(b13);
		assertFalse(act2.isNoop());
	}
}
