import java.util.*;
import java.io.*;

class State {

	private State next;
	private String state;
	private String[] child = new String[] { "+4", "-4", "*4", "/4", "**4", "4", ".4", "()" };
	private ArrayList<State> children = new ArrayList<State>();

	public State(String state) {

		this.state = state;
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

		foreach(String s: child) {

			State childState = new State(state+s);
			children.add(childState);
		}


	}
}