import java.util.*;
import java.io.*;

class Frontier {

	State root;

	/* CONSTRUCTOR */

	public Frontier(State initialState) {

		root = initialState;
	}

	/* METHODS */

	//FIND THE LOWEST F_VALUED STATE
	public State getLowestF() {

		State curr = root;
		State min_fValue = curr;

		//WHILE NOT END OF THE LIST
		while(curr != null) {

			if (curr.getNext() != null) {

				if(curr.getFValue() < curr.getNext().getFValue()) {

					if (curr.getFValue() <= min_fValue.getFValue()) {

						min_fValue = curr;
					}
				}
			}
			else {
				
				if (curr.getFValue() <= min_fValue.getFValue()) {

					min_fValue = curr;
				}
			}

			curr = curr.getNext();
		}

		return min_fValue;
	}

	//ADDING NEW STATE AT THE END OF THE LIST
	public void add(State state) {

		State curr = root;

		while(curr.getNext() != null) {

			curr = curr.getNext();
		}

		curr.setNext(state);
	}

	//REMOVE FROM THE LIST AT A GIVEN PATH 
	public void remove(int x, int y) {

		//IF THE STATE TO REMOVE IS AT THE HEAD OF THE LIST
		if((root.getXCoord() == x) && (root.getYCoord() == y)) {

			root = root.getNext();
		}

		State curr = root;

		while(!(curr.getNext().getXCoord() == x) && (curr.getNext().getYCoord() == y)) {

			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext());
	}
}