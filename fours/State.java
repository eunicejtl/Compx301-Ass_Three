import java.util.*;
import java.io.*;

class State {

	private String _state;
	private int _move;
	private String[] _child = new String[] { "+4", "-4", "*4", "/4", "**4", "4", ".4", "()" };
	private ArrayList<State> _children = new ArrayList<State>();

	/* CONSTRUCTOR */

	//GRAPH STATE CONSTRUCTOR
	public State(String state) {

		_state = state;
	}
	public State(String state, int move) {

		_state = state;
		_move = move;
	}

	/* GETTERS AND SETTERS */

	public String getState() {

		return _state;
	}

	public int getMove() {

		return _move;
	}

	public void setChildren(String state) {

		int temp = 0;

		foreach(String s: child) {

			State childState = new State(state+s, temp);
			children.add(childState);
			temp++;
		}
	}

	public ArrayList<State> getChildren() {

		return _children;
	}

	public State getChild(State parent, int move) {

		State child = parent.getChildren().get(move);
		return child;
	}
}