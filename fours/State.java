import java.util.*;
import java.io.*;

class State {

	private String _state;
	private int _move;
	private String[] _child = new String[] { "+4", "-4", "*4", "/4", "**4", "4", ".4", "()" };
	private ArrayList<State> _children = new ArrayList<State>();

	private State _next;

	/* CONSTRUCTOR */

	//GRAPH STATE CONSTRUCTOR
	public State(String state) {

		_state = state;
	}
	public State(String state, int move) {

		_state = state;
		_move = move;
	}


	public String getState() {

		return state;
	}

	public void setState(String state) {

		this.state = state;
	}

	public Node getNext() {

		return next;
	}

	public void setNext(Node next) {

		this.next = next;
	}

	public void setChildren(String state) {

		int temp = 0;

		foreach(String s: child) {

			State childState = new State(state+s, temp);
			children.add(childState);
			temp++;
		}
	}
}