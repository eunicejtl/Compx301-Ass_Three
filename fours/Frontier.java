/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

class Frontier {

	private State root;

	public Frontier() {

		//Set the initial frontier
		root = new State("4");
	}

	//ADD STATES AT THE END OF THE QUEUE
	public void enqueue(State state) {

		State curr = root;

		while(curr.getNext() != null) {

			curr = curr.getNext();
		}
		curr.setNext(state);
	}

	//REMOVE FROM THE TOP THE LIST
	public void dequeue() {

		root = root.getNext();
	}

	public State getRoot() {

		return root;
	}
}