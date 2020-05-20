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

	public void Dequeue() {

		//Check if end of queue
		if(root.getNext() != null) {

			root = root.getNext();
		}
	}

	public State getRoot() {

		return root;
	}
}