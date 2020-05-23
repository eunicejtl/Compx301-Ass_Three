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
	public void remove(State state) {

		//IF THE STATE TO REMOVE IS AT THE HEAD OF THE LIST
		if(compareState(root, state)) {

			root = root.getNext();
			return;
		}

		State curr = root;

		while(!compareState(curr, state)) {

			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext());
	}

	//FIND THE LOWEST F_VALUED STATE
	public boolean getLowestCost(State state) {

		State curr = root;
		State min_cost = curr;


		boolean returnValue = true;

		//WHILE NOT END OF THE LIST
		while(curr != null) {
			
			if(compareState(curr, state)) {

				if(curr.getCost() <= state.getCost()) {

					min_cost = curr;
					returnValue = false;
				}
				else {

					min_cost = state;

					//Remove the existing state
					remove(curr);

					returnValue = true;
					
				}
			}
			curr = curr.getNext();
		}

		return returnValue;
	}

	public boolean compareState(State stateOne, State stateTwo) {

        if ((stateOne.getXCoord() == stateTwo.getXCoord()) && (stateOne.getYCoord() == stateTwo.getYCoord())) {

            return true;
        }

        return false;
    }
}