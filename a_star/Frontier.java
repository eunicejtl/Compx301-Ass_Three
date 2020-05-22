import java.util.*;
import java.io.*;

class Frontier {

	State root;

	/* CONSTRUCTOR */

	public Frontier(State initialState) {

		root = initialState;
	}

	/* METHODS */

	//CALCULATE THE HEURISTIC VALUE OF A STATE USING EUCLIDEAN DISTANCE
	//sqrt((current.x - goal.x)^2 + (current.y - goal.y)^2)
	//public double calcHeuristic(int currXCoord, int currYCoord, int goalXCoord, int goalYCoord) {
	public double calcHeuristic(State currState, State goalState) {
		
		//double distance = Math.sqrt(Math.pow(currXCoord - goalXCoord, 2) + Math.pow(currYCoord - goalYCoord, 2));
		double distance = Math.sqrt(Math.pow(currState.getXCoord() - goalState.getXCoord(), 2) + Math.pow(currState.getYCoord() - goalState.getYCoord(), 2));
			
		return distance;
	}

	//CALCULATE THE F_VALUE OF THE STATE
	public double calcF(double cost, double heuristic) {

		double f_value = cost + heuristic;

		return f_value;
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