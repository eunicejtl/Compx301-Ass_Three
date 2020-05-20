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
	public void Enqueue(State newState) {

		State curr = root;

		while(curr != null) {

			curr = curr.getNext();
		}
		curr = newState;
	}

	//REMOVE FROM THE TOP THE LIST
	public void Dequeue() {

		root = root.getNext();
	}

	public State getRoot() {

		return root;
	}
}