package nl.cwi.reo.semantics.rbautomaton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.cwi.reo.semantics.predicates.Conjunction;
import nl.cwi.reo.semantics.predicates.Existential;
import nl.cwi.reo.semantics.predicates.Formula;
import nl.cwi.reo.semantics.predicates.Node;

public class RuleNode implements HypergraphNode{
	private Set<Hyperedge> hyperedges;
	private List<RuleNode> exclusiveRules = new ArrayList<>();
	private Rule rule;
	
	private boolean visited;

	public RuleNode(Rule r, Set<Hyperedge> hyperedge) {
		this.rule = r;
		this.hyperedges=hyperedge;
		visited = false;
	}
	
	public RuleNode(Rule r){
		this.rule = r;
		this.hyperedges = new HashSet<Hyperedge>();
		visited = false;		
	}
	
	public Rule getRule(){
		return rule;
	}
	
	
	public Set<Hyperedge> getHyperedges(){
		return hyperedges;
	}
	
	public Hyperedge getHyperedges(PortNode p){
		for(Hyperedge h : hyperedges)
			if(h.getRoot().getPort().equals(p.getPort()))
				return h;
		return null;
	}
	
	public void addHyperedge(Hyperedge h){
		hyperedges.add(h);
	}
	
	public void rmHyperedge(Hyperedge h){
		hyperedges.remove(h);
	}
	
	public boolean isVisited() {
		return visited;
	}

	public List<RuleNode> getExclRules(){
		return exclusiveRules;
	}
	
	public void addExclRules(RuleNode n){
		exclusiveRules.add(n);
	}
	
	@Override
	public void setFlag(boolean flag) {
		this.visited = flag;
	}
	
	public RuleNode compose(Formula f){
		Formula formula = new Conjunction(Arrays.asList(rule.getFormula(),f));
		rule=new Rule(rule.getSync(),formula);
		return this;
	}
	
	public RuleNode compose(RuleNode r){
//		Rule newRule=new Rule(rule.getSync(),new Conjunction(Arrays.asList(rule.getFormula(),r.getRule().getFormula())));
		rule = new Rule(rule.getSync(),new Conjunction(Arrays.asList(rule.getFormula(),r.getRule().getFormula())));
		hyperedges.addAll(r.getHyperedges());
//		return new RuleNode(newRule,this.getHyperedges());
		return this;
	}
	
	
	
	public RuleNode hide(PortNode p){
		rule = new Rule(rule.getSync(),(new Existential(new Node(p.getPort()),rule.getFormula())).QE());
		return this;
	}
	
	public String toString(){
		String s = "("+rule.toString()+")";
//		s=s+"excl rules : (";
//		int i =0;
//		for(RuleNode r : exclusiveRules){
//			s=s+(i!=0?",":"")+r.getRule().toString()+"\n";
//			i++;
//		}
		return s;
	}
}
