/*
 * Created on 2004-07-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package plplan.algorithms;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Example1 class for MutexProp.
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
public class MutexPropTest extends TestCase {

	private MutexProp mutexA;
    
    byte b1 = 1;
    byte b2 = 2;
    byte b3 = 3;

	private Proposition a = new Proposition(b1);

	private Proposition b = new Proposition(b2);

	private Proposition c = new Proposition(b3);

	private List listab;

	private List lista;

	private List listc;
    
    public MutexPropTest(){
        
    }

	public static void main(String[] args) {
		junit.textui.TestRunner.run(MutexPropTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		mutexA = new MutexProp(a);
		a = new Proposition(b1);
		b = new Proposition(b2);
		c = new Proposition(b3);
		listab = new ArrayList();
		listab.add(a);
		listab.add(b);
		lista = new ArrayList();
		lista.add(a);
		listc = new ArrayList();
		listc.add(c);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testHashCode() {
		mutexA.add(b);
		assertTrue(a.hashCode() == mutexA.hashCode());
		assertTrue(b.hashCode() != mutexA.hashCode());
	}

	public final void testGetNode() {
		assertEquals(mutexA.getNode(), a);
	}

	public final void testAddAll() {
		assertFalse(mutexA.containsAll(listab.iterator()));
		mutexA.add(c);
		assertTrue(mutexA.containsAll(listc.iterator()));
		assertFalse(mutexA.containsAll(lista.iterator()));
		mutexA.addAll(lista.iterator());
		assertTrue(mutexA.containsAll(lista.iterator()));
		mutexA.addAll(lista.iterator());
		assertTrue(mutexA.containsAll(lista.iterator()));
	}
	
	public final void testContainNode(){
		assertFalse(mutexA.containsNode(a));
		assertFalse(mutexA.containsNode(b));
		mutexA.add(a);
		assertTrue(mutexA.containsNode(a));
		mutexA.add(b);
		assertTrue(mutexA.containsNode(b));
		mutexA.addAll(listc.iterator());
		assertTrue(mutexA.containsNode(c));
	}
	
	public final void testContainAtLeastOneOf_Et_ContainAll(){
		assertFalse(mutexA.containsAll(listab.iterator()));
		assertFalse(mutexA.containsAtLeastOneOf(listab.iterator()));
		mutexA.add(a);
		assertFalse(mutexA.containsAll(listab.iterator()));
		assertTrue(mutexA.containsAtLeastOneOf(listab.iterator()));
		assertTrue(mutexA.containsAll(lista.iterator()));
		assertTrue(mutexA.containsAtLeastOneOf(lista.iterator()));
		mutexA.add(b);
		assertTrue(mutexA.containsAll(listab.iterator()));
		assertTrue(mutexA.containsAtLeastOneOf(listab.iterator()));
		assertTrue(mutexA.containsAll(lista.iterator()));
		assertTrue(mutexA.containsAtLeastOneOf(lista.iterator()));
	}
	
	public final void testGetNbMutexPair(){
		assertTrue(mutexA.getNbMutexPair() == 0);
		mutexA.add(a);
		mutexA.add(a);
		assertTrue(mutexA.getNbMutexPair() == 1);
		mutexA.add(b);
		assertTrue(mutexA.getNbMutexPair() == 2);
	}
}