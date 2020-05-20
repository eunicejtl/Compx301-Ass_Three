import java.util.*;
import java.io.*;

class State {

	//VARIABLES FOR BOTH
	private String _state;
	
	//VARIABLES FOR TRIE (MOVE)
	private int _move;
	private String[] _child = new String[] { "+4", "-4", "*4", "/4", "**4", "4", ".4", "()" };
	private ArrayList<State> _children = new ArrayList<State>();

	//VARIABLES FOR LINKEDLIST (FRONTIER)
	private int _stateNum;
	private State _next;

	/* CONSTRUCTOR */

	//STATE CONSTRUCTOR
	public State(String state) {

		_state = state;
	}
	public State(String state, int move) {

		_state = state;
		_move = move;
	}

	//CONCTRUCTOR HELPER FOR FRONTIER
    public void setStateNum(int stateNum) {

        _stateNum = stateNum;
    }

    public int getStateNum() {

        return _stateNum;
    }

	/* GETTERS AND SETTERS */

	public String getState() {

		return _state;
	}

	public int getMove() {

		return _move;
	}

	public void setNext(State next) {

        _next = next;
    }

    public State getNext() {

        return _next;
    }

	public void setChildren(String state) {

		int temp = 0;

		for(String s: _child) {

			State childState = new State(state+s, temp);
			_children.add(childState);
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