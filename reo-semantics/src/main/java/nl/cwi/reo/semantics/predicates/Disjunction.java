package nl.cwi.reo.semantics.predicates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.util.Monitor;

// TODO: Auto-generated Javadoc
/**
 * Disjunction of a list of formulas.
 */
public class Disjunction implements Formula {

	/**
	 * Flag for string template.
	 */
	public static final boolean disjunction = true;

	/**
	 * List of formulas in this disjunction.
	 */
	private final List<Formula> clauses;

	/**
	 * Constructs the disjunction of a list of formulas.
	 * 
	 * @param clauses
	 *            list of formulas
	 */
	public Disjunction(List<Formula> clauses) {
		this.clauses = clauses;
	}

	/**
	 * Returns the list of clauses in this conjunction.
	 * 
	 * @return list of clauses of this conjunction.
	 */
	public List<Formula> getClauses() {
		return clauses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula rename(Map<Port, Port> links) {
		List<Formula> h = new ArrayList<Formula>();
		for (Formula f : clauses)
			h.add(f.rename(links));
		return new Disjunction(h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port> getInterface() {
		Set<Port> P = new HashSet<Port>();
		for (Formula f : clauses)
			if (f instanceof Disjunction || f instanceof Conjunction || f instanceof Existential)
				P.addAll(f.getInterface());
		return P;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public @Nullable Formula evaluate(Scope s, Monitor m) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Disjunction DNF() {
		List<Formula> list = new ArrayList<Formula>();
		for (Formula f : clauses)
			list.add(f.DNF());
		return new Disjunction(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula NNF() {
		List<Formula> list = new ArrayList<Formula>();

		if (clauses.size() == 1)
			return clauses.get(0).NNF();

		for (Formula f : clauses)
			list.add(f.NNF());
		return new Disjunction(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula QE() {
		List<Formula> list = new ArrayList<Formula>();

		if (clauses.size() == 1)
			return clauses.get(0).QE();

		for (Formula f : clauses) {
			if (f instanceof Equality && ((Equality) f).getLHS().equals(((Equality) f).getRHS()))
				return new TruthValue(true);
			list.add(f.QE());
		}
		return new Disjunction(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula substitute(Term t, Variable x) {
		List<Formula> list = new ArrayList<Formula>();
		if (clauses.size() == 1)
			return clauses.get(0).substitute(t, x);
		for (Formula f : clauses) {
			Formula formula = f.substitute(t, x);
			if (formula instanceof TruthValue && ((TruthValue) formula).getBool() == true)
				return formula;
			list.add(formula);
		}
		return new Disjunction(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Variable> getFreeVariables() {
		Set<Variable> vars = new HashSet<Variable>();
		for (Formula f : clauses)
			vars.addAll(f.getFreeVariables());
		return vars;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Variable, Integer> getEvaluation() {
		// TODO Auto-generated method stub
		return new HashMap<Variable, Integer>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = "(" + clauses.get(0);
		for (int i = 1; i < clauses.size(); i++)
			s = s + "\u2228" + clauses.get(i);
		return s + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Disjunction))
			return false;
		Set<Formula> s = new HashSet<>(this.getClauses());
		Set<Formula> s2 = new HashSet<>(((Disjunction) other).getClauses());

		return Objects.equals(s, s2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		Set<Formula> s = new HashSet<>(clauses);
		return Objects.hash(s);
	}

}
