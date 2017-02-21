package nl.cwi.reo.interpret.instances;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.components.ComponentExpression;
import nl.cwi.reo.interpret.connectors.Semantics;
import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.interpret.terms.TermList;
import nl.cwi.reo.interpret.variables.Identifier;
import nl.cwi.reo.interpret.variables.VariableExpression;
import nl.cwi.reo.interpret.variables.VariableListExpression;
import nl.cwi.reo.util.Monitor;

/**
 * Interpretation of an atomic component instance.
 * @param <T> Reo semantics type
 */
public final class InstanceAtomic<T extends Semantics<T>> implements InstancesExpression<T> {

	/**
	 * Component definition.
	 */
//	private final VariableExpression<Identifier> var;
	private final ComponentExpression<T> component;
	
	/**
	 * List of parameter values.
	 */
	private final TermList values;
	
	/**
	 * List of ports.
	 */
	private final VariableListExpression ports;
	
	/**
	 * Constructs an atomic component instance.
	 * @param component	component definition
	 * @param values	parameter values
	 * @param ports		interface ports
	 */
	public InstanceAtomic(ComponentExpression<T> component, TermList values, VariableListExpression ports) {
		this.component = component;
		this.values = values;
		this.ports = ports;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Instances<T> evaluate(Scope s, Monitor m) {
		// TODO Auto-generated method stub
		return null;
	}

}
