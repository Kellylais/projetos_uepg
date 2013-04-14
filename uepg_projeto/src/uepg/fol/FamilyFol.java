/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uepg.fol;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aima.logic.fol.CNFConverter;
import aima.logic.fol.StandardizeApartIndexicalFactory;
import aima.logic.fol.Unifier;
//import aima.logic.fol.domain.DomainFactory;
import aima.logic.fol.domain.FOLDomain;
//import aima.logic.fol.inference.Demodulation;
import aima.logic.fol.inference.FOLBCAsk;
import aima.logic.fol.inference.FOLFCAsk;
import aima.logic.fol.inference.FOLModelElimination;
//import aima.logic.fol.inference.FOLOTTERLikeTheoremProver;
import aima.logic.fol.inference.FOLTFMResolution;
import aima.logic.fol.inference.InferenceProcedure;
import aima.logic.fol.inference.InferenceResult;
//import aima.logic.fol.inference.Paramodulation;
import aima.logic.fol.inference.proof.Proof;
import aima.logic.fol.inference.proof.ProofPrinter;
import aima.logic.fol.kb.FOLKnowledgeBase;
import aima.logic.fol.kb.FOLKnowledgeBaseFactory;
import aima.logic.fol.kb.data.CNF;
import aima.logic.fol.kb.data.Clause;
import aima.logic.fol.kb.data.Literal;
import aima.logic.fol.parsing.FOLParser;
import aima.logic.fol.parsing.ast.AtomicSentence;
import aima.logic.fol.parsing.ast.Constant;
import aima.logic.fol.parsing.ast.Predicate;
import aima.logic.fol.parsing.ast.Sentence;
import aima.logic.fol.parsing.ast.Term;
import aima.logic.fol.parsing.ast.TermEquality;
import aima.logic.fol.parsing.ast.Variable;


import aima.logic.fol.domain.DomainFactory;
import aima.logic.fol.domain.FOLDomain;
import aima.logic.fol.inference.InferenceProcedure;

/**
 *
 * @author jcarlos
 */
public class FamilyFol extends aima.logic.fol.demos.FolDemo {

    public void runTest() {
        FOLTFMResolution inference_procedure = new FOLTFMResolution();
        FOLKnowledgeBase kb = createKB(inference_procedure);

        String kbStr = kb.toString();

	List<Term> terms = new ArrayList<Term>();
	terms.add(new Variable("w"));
	terms.add(new Constant("regiane"));
	Predicate query = new Predicate("mae", terms);
	InferenceResult answer = kb.ask(query);

	System.out.println("Family:");
        System.out.println(kbStr);
        System.out.println("Query: " + query);
        for (Proof p : answer.getProofs()) {
                System.out.print(ProofPrinter.printProof(p));
                System.out.println("");
        }
        System.out.println("end.");

    }


    private FOLKnowledgeBase createKB(FOLTFMResolution ip) {
        FOLDomain dm = this.createDomain();
        FOLKnowledgeBase kb;
        kb = new FOLKnowledgeBase(dm,ip);
        kb.tell("h(joao)");
        kb.tell("h(pedro)");
        kb.tell("h(marcos)");
        kb.tell("h(luiz)");
        kb.tell("h(lucas)");
        kb.tell("m(ana)");
        kb.tell("m(marina)");
        kb.tell("m(regiane)");
        kb.tell("m(luzia)");
        kb.tell("m(luciana)");
        kb.tell("genitor(joao,marina)");
        kb.tell("genitor(ana,marina)");
        kb.tell("genitor(joao,marcos)");
        kb.tell("genitor(ana,marcos)");
        kb.tell("genitor(marina,regiane)");
        kb.tell("genitor(pedro,regiane)");
        kb.tell("FORALL x , y (mae(x,y) <=> (m(x) AND genitor(x,y)))");
        kb.tell("FORALL x , y (pai(x,y) <=> (h(x) AND genitor(x,y)))");
        kb.tell("FORALL x , y (irmaos(x,y) <=> (EXISTS z (mae(z,x) AND mae(z,y))))");
        kb.tell("FORALL x , y (irmaos(x,y) <=> (EXISTS z (pai(z,x) AND mae(z,y))))");
        kb.tell("FORALL x , y (irma(x,y) <=> (irmaos(x,y) AND m(x) ))");
        kb.tell("FORALL x , y (irma(x,y) <=> (irmaos(y,x) AND m(x) ))");
        //
        return kb;
    }

    private FOLDomain createDomain() {
        FOLDomain domain = new FOLDomain();
	domain.addConstant("joao");
	domain.addConstant("pedro");
	domain.addConstant("marcos");
	domain.addConstant("luiz");
	domain.addConstant("lucas");
        domain.addConstant("ana");
	domain.addConstant("marina");
	domain.addConstant("regiane");
	domain.addConstant("luzia");
	domain.addConstant("luciana");
	domain.addPredicate("h");
        domain.addPredicate("m");
        domain.addPredicate("pai");
        domain.addPredicate("mae");
        domain.addPredicate("genitor");
        domain.addPredicate("irmaos");
        domain.addPredicate("irma");
        return domain;
    }


}
