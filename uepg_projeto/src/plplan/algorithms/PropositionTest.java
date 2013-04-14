package plplan.algorithms;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Example1 class for Proposition.
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
public class PropositionTest extends TestCase {

    PropositionPGraph a;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PropositionTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        byte x = 1;
		a = new PropositionPGraph(x);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSetNegIn() {
		Map negMap = new HashMap();
		a.setNegIn(negMap);
		assertTrue(a.getNegInMap() == negMap);
	}

	public final void testSetPosIn() {
		Map posMap = new HashMap();
		a.setPosIn(posMap);
		assertTrue(a.getPosInMap() == posMap);
	}

	public final void testSetOut() {
		Map outMap = new HashMap();
		a.setOut(outMap);
		assertTrue(a.getOutMap() == outMap);
	}

	public final void testDuplicate() {
		Map negMap = new HashMap();
		Map posMap = new HashMap();
		Map outMap = new HashMap();
		a.setNegIn(negMap);
		a.setPosIn(posMap);
		a.setOut(outMap);
        PropositionPGraph b = a.duplicatePropositionPGraph();
		assertTrue(b.getNegInMap() != negMap);
		assertTrue(b.getPosInMap() != posMap);
		assertTrue(b.getOutMap() != outMap);
	}
}