/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

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

        //WHILE NOT THE STATE TO REMOVE
        while(!compareState(curr.getNext(), state)) {

        	//GO NEXT
            curr = curr.getNext();
        }

        //IF THE STATE TO REMOVE IS AT THE TAIL
        if (compareState(tail, curr.getNext())) {
        	
        	//MAKE TAIL CURR
        	tail = curr;
        }
        
        //SET CURR'S NEXT TO CURR'S NEXT'S NEXT
        curr.setNext(curr.getNext().getNext());
    }

    //FIND THE LOWEST F_VALUED STATE
	public State getLowestF() {

		State curr = root;
		State min_fValue = curr;

		//WHILE NOT END OF THE LIST
		while(curr != null) {

			//IF CURR IS NOT END OF THE LIST
			if (curr.getNext() != null) {

				//IF CURR'S F VALUE IS LOWER OR EQUAL TO NEXT'S F VALUE
				if(curr.getFValue() <= curr.getNext().getFValue()) {

					//IF CURR F VALUE IS LOWER THAN THE SAVED STATE (LOWEST F VALUE)
					if (curr.getFValue() <= min_fValue.getFValue()) {

						//MAKE IT THE LOWEST F VALUE
						min_fValue = curr;
					}
				}
			}
			//IF CURR IS THE TAIL
			else {
				
				//IF CURR F VALUE IS LOWER THAN THE SAVED STATE (LOWEST F VALUE)
				if (curr.getFValue() <= min_fValue.getFValue()) {

					//MAKE IT THE LOWEST F VALUE
					min_fValue = curr;
				}
			}

			//GO NEXT
			curr = curr.getNext();
		}

		return min_fValue;
	}

    //FIND THE LOWEST COST STATE IF SAME STATE
    public boolean getLowestCost(State state) {

        State curr = root;
        State min_cost = curr;
        boolean returnValue = true;

        //WHILE NOT END OF THE LIST
        while(curr != null) {

            //IF STATES ARE THE SAME
            if(compareState(curr, state)) {

                //IF EXISTING STATE'S COST IS LOWER OR EQUAL TO NEW STATE'S COST
                if(curr.getCost() <= state.getCost()) {

                    //MAKE CURR AS THE MIN COST MAKE RETURN FALSE
                    min_cost = curr;
                    returnValue = false;
                }
                //IF NEW STATE HAS LOWER COST
                else {

                    //MAKE NEW STATE LOWEST COST
                    min_cost = state;

                    //REMOVE EXISTING STATE RETURN TRUE
                    remove(curr);
                    returnValue = true;
                }
            }

            //GO NEXT
            curr = curr.getNext();
        }

        return returnValue;
    }

    //COMPARE IF STATEONE HAS THE SAME COORDINATES AT STATETWO
	public boolean compareState(State stateOne, State stateTwo) {

		if ((stateOne.getXCoord() == stateTwo.getXCoord()) && (stateOne.getYCoord() == stateTwo.getYCoord())) {
			
			return true;
		}

		return false;
	}

	//GET THE PATH USING STATE'S PREVIOUS PATH FROM SPECIFIED GOAL TO SPECIFIED START STATE
	public ArrayList<State> getPath(State start, State goal) {

		ArrayList<State> path = new ArrayList<State>();
		State _goal = goal;

		//WHILE _GOAL IS NOT THE START
		while (!compareState(_goal, start)) {
		 	
			//ADD TO THE PATH THEN SET GOAL TO GOAL'S PREVIOUS PATH
			path.add(_goal);
			_goal = _goal.getPP();
		}

		return path;
	}

	public void displayFrontier() {

		System.out.println("DISPLAYING FRONTIER...");
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

	public State getRoot() {

		return root;
	}
}