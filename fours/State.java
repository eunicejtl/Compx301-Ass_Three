/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

class State {

	//VARIABLES FOR BOTH
	private String _expression;
	private State _next;

	/* CONSTRUCTOR */

	//STATE CONSTRUCTOR
	public State(String expression) {

		_expression = expression;
	}

	/* GETTERS AND SETTERS */

	public void setExpression(String expression) {

		_expression = expression;
	}

	public String getExpression() {

		return _expression;
	}

	public void setNext(State next) {

        _next = next;
    }

    public State getNext() {

        return _next;
    }
}