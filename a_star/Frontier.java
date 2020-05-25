import java.util.*;
import java.io.*;

class Frontier {

	private State root;
	private State tail;

	/* CONSTRUCTOR */

	public Frontier(State initialState) {

		root = initialState;
		tail = root;
	}

	/* METHODS */

	//ADDING NEW STATE AT THE END OF THE LIST
	public void add(State state) {

		tail.setNext(state);
		tail = state;
	}

	//REMOVE FROM THE LIST AT A GIVEN PATH 
    public void remove(State state) {

        //IF THE STATE TO REMOVE IS AT THE HEAD OF THE LIST
        if(compareState(root, state)) {

            root = root.getNext();
            return;
        }

        State curr = root;

        while(!compareState(curr.getNext(), state)) {

            curr = curr.getNext();
        }

        if (compareState(tail, curr.getNext())) {
        	
        	tail = curr;
        }
        
        curr.setNext(curr.getNext().getNext());
    }

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

	public boolean findLowestF(State state) {

		State curr = root;
		State min_fValue = curr;
		boolean returnValue = true;

		//WHILE NOT END OF THE LIST
		while(curr != null) {

			if (compareState(curr, state)) {

				if(curr.getFValue() <= state.getFValue()) {

					min_fValue = curr;
					returnValue = false;
				}
				else {
					
					min_fValue = curr;

                    //Remove the existing state
                    remove(curr);

                    returnValue = true;
				}
			}

			curr = curr.getNext();
		}

		return returnValue;
	}

    //FIND THE LOWEST COST STATE IF SAME STATE
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

	public State getRoot() {

		return root;
	}

	public void displayFrontier() {

		System.out.println("	DISPLAYING FRONTIER...");
		System.out.println("");

		State curr = root;

		//WHILE NOT END OF THE LIST
		while (curr != null) {
		
			System.out.println("	( " + curr.getXCoord() + ", " + curr.getYCoord() + " )");
			curr = curr.getNext();
		}

		return;
	}

	public int getSize() {

		int size = 0;
		State curr = root;

		//WHILE NOT END OF THE LIST
		while (curr != null) {
		
			size++;
			curr = curr.getNext();
		}

		return size;
	}
}