/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

class Frontier {

	private State root;
	private State tail;

	public Frontier() {

		//SET THE INITIAL FRONTIER VALUE
		root = new State("4");
		tail = root;
	}

	//ADD STATES AT THE END OF THE QUEUE
	public void enqueue(State state) {

		tail.setNext(state);
		tail = state;
	}

	//REMOVE FROM THE TOP THE LIST
	public void dequeue() {

		root = root.getNext();
	}

	public State getRoot() {

		return root;
	}
}