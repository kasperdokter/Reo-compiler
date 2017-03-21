package nl.cwi.reo.semantics.rulebasedautomata;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.interpret.ports.PortType;
import nl.cwi.reo.semantics.Semantics;
import nl.cwi.reo.semantics.SemanticsType;
import nl.cwi.reo.util.Monitor;

public class RuleBasedAutomaton implements Semantics<RuleBasedAutomaton> {

	/**
	 * Interface.
	 */
	private final Set<Port> ports;

	/**
	 * Set of transition rules.
	 */
	private final Set<TransitionRule> rules;

	/**
	 * Initial state.
	 */
	private final Object[] initial;

	/**
	 * Constructs a new rule-based automaton.
	 * 
	 * @param ports
	 *            interface
	 * @param rules
	 *            set of rules
	 * @param initial
	 */
	public RuleBasedAutomaton(Set<Port> ports, Set<TransitionRule> rules, Object[] initial) {
		if (ports == null)
			throw new NullPointerException("Undefined interface.");
		this.ports = ports;
		this.initial = initial;
		if (rules == null)
			throw new NullPointerException("Undefined set of rules.");
		for (TransitionRule r : rules) {
			if (r == null)
				throw new NullPointerException("Undefined rule.");
			if (r.getDimension() != initial.length)
				throw new IllegalArgumentException("Rule " + r + " must be " + initial.length + " dimensional.");
		}
		this.rules = rules;
	}

	public int getDimensions() {
		return initial.length;
	}

	public Set<TransitionRule> getTransitionRules() {
		return rules;
	}

	@Override
	public Set<Port> getInterface() {
		return ports;
	}

	@Override
	public @Nullable RuleBasedAutomaton evaluate(Scope s, Monitor m) {
		return this;
	}

	@Override
	public SemanticsType getType() {
		return SemanticsType.RbA;
	}

	@Override
	public RuleBasedAutomaton getNode(Set<Port> node) {
		Set<Port> ports = new HashSet<Port>(node);

		Set<Port> inps = new HashSet<Port>();
		Set<Port> outs = new HashSet<Port>();

		for (Port p : ports) {
			if (p.getType() == PortType.IN) {
				inps.add(p);
			} else {
				outs.add(p);
			}
		}

		Set<TransitionRule> rules = new HashSet<TransitionRule>();

		Constant[] q0 = new Constant[0];
		DataTerm[] q1 = new DataTerm[0];

		for (Port p : inps) {
			Set<Port> included = new HashSet<Port>();
			Set<Port> excluded = new HashSet<Port>();
			DataConstraint g = new BooleanValue(true);
			for (Port x : inps) {
				if (x.equals(p)) {
					included.add(x);
				} else {
					excluded.add(x);
				}
			}
			for (Port x : outs) {
				included.add(x);
				g = new Conjunction(g, new Equality(new PortVariable(p), new PortVariable(x)));
			}

			SyncConstraint N = new SyncConstraint(included, excluded);

			rules.add(new TransitionRule(q0, q1, N, g));
		}

		return new RuleBasedAutomaton(ports, rules, new Object[0]);
	}

	@Override
	public RuleBasedAutomaton rename(Map<Port, Port> links) {
		Set<Port> ports = new HashSet<Port>();
		Set<TransitionRule> rules = new HashSet<TransitionRule>(); 
		
		// Rename the ports in the interface
		for (Port a : this.ports) {
			Port b = links.get(a);
			if (b == null) b = a;
			ports.add(b);
		}
		
		// Add relabeled transitions to the set of transition
		for (TransitionRule r : this.rules) 
			rules.add(r.rename(links));
		
		return new RuleBasedAutomaton(ports, rules, initial);
	}

	@Override
	public RuleBasedAutomaton compose(List<RuleBasedAutomaton> components) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuleBasedAutomaton restrict(Collection<? extends Port> intface) {
		// TODO Auto-generated method stub
		return null;
	}
}
