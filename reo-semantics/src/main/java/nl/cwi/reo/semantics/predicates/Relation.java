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
import nl.cwi.reo.interpret.values.Value;
import nl.cwi.reo.interpret.variables.Identifier;
import nl.cwi.reo.util.Monitor;

// TODO: Auto-generated Javadoc
/**
 * Relation of a list of terms.
 */
public class Relation implements Formula {

	/**
	 * Flag for string template.
	 */
	public static final boolean relation = true;

	/**
	 * Name of this relation.
	 */
	private final String name;

	/**
	 * Value of this relation or reference to its implementation.
	 */
	@Nullable
	private String value;

	/**
	 * List of arguments of this relation.
	 */
	@Nullable
	private final List<Term> args;

	/**
	 * Constructs a new relation with a given name and a given list of
	 * arguments.
	 * 
	 * @param name
	 *            name of the relation
	 * @param args
	 *            list of arguments
	 */
	public Relation(String name, @Nullable List<Term> args) {
		this.name = name;
		this.value = null;
		this.args = args;
	}

	/**
	 * Constructs a new relation with a given name, a given value, and a given
	 * list of arguments.
	 * 
	 * @param name
	 *            name of the relation
	 * @param value
	 *            value of this relation or reference to its implementation
	 * @param args
	 *            list of arguments
	 */
	public Relation(String name, @Nullable String value, @Nullable List<Term> args) {
		this.name = name;
		this.value = value;
		this.args = args;
	}

	/**
	 * Gets the name of this relation.
	 * 
	 * @return name of this relation.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the value of this relation.
	 * 
	 * @return value of this relation.
	 */
	@Nullable
	public String getValue() {
		return value;
	}

	/**
	 * Gets the list of arguments of this relation.
	 * 
	 * @return list of arguments of this relation.
	 */
	@Nullable
	public List<Term> getArgs() {
		return args;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Variable> getFreeVariables() {
		Set<Variable> vars = new HashSet<Variable>();
		if (args != null)
			for (Term t : args)
				vars.addAll(t.getFreeVariables());
		return vars;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public Formula evaluate(Scope s, Monitor m) {

		// Evaluate the symbol
		String _name = name;
		Value v = s.get(new Identifier(name));
		if (v != null)
			_name = v.toString();

		// Evaluate the arguments
		List<Term> _args = null;
		if (args != null) {
			_args = new ArrayList<>();
			for (Term t : args) {
				Term u = t.evaluate(s, m);
				if (u == null)
					return null;
				_args.add(u);
			}
		}
		
		return new Relation(_name, value, _args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port> getInterface() {
		return new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula rename(Map<Port, Port> links) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula NNF() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula DNF() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula QE() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formula substitute(Term t, Variable x) {
		if (args == null)
			return this;
		List<Term> listTerms = new ArrayList<Term>();
		for (Term term : args) {
			if (term.equals(t))
				listTerms.add(x);
			else
				listTerms.add(term);
		}
		return new Relation(this.name, this.value, listTerms);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Variable, Integer> getEvaluation() {
		return new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = name;
		if (args != null) {
			s += "(";
			for (Term t : args)
				s += ", " + t;
			s += ")";
		}
		return s;
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
		if (!(other instanceof Relation))
			return false;
		Relation p = (Relation) other;
		return Objects.equals(this.name, p.name) && Objects.equals(this.value, p.value)
				&& Objects.equals(this.args, p.args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.value, this.args);
	}

}
