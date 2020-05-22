import java.util.*;
import java.io.*;

class Frontier {

	State _initialState;

	public Frontier(State initialState) {

		_initialState = initialState;
	}

	public double calcHeuristic(int currXCoord, int currYCoord, int goalXCoord, int goalYCoord) {

		//USING EUCLIDEAN DISTANCE
		//sqrt((current.x - goal.x)^2 + (current.y - goal.y)^2)
		double distance = Math.sqrt(Math.pow(currXCoord - goalXCoord, 2) + Math.pow(currYCoord - goalYCoord, 2));
			
		return distance;
	}

	public double calcF(int cost, double heuristic) {

		double f_value = cost + heuristic;

		return f_value;
	}

	public State getLowestF() {

		State curr = _initialState;
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

		State curr = _initialState;

		while(curr.getNext() != null) {

			curr = curr.getNext();
		}

		curr.setNext(state);
	}

	//REMOVE FROM THE LIST AT A GIVEN PATH 
	public void remove(int x, int y) {

		//IF THE STATE TO REMOVE IS AT THE HEAD OF THE LIST
		if((_initialState.getXCoord() == x) && (_initialState.getYCoord() == y)) {

			_initialState = _initialState.getNext();
		}

		State curr = _initialState;

		while(!(curr.getNext().getXCoord() == x) && (curr.getNext().getYCoord() == y)) {

			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext());
	}

	//CHECK IF COORD EXISTS IN THE LIST
	// public State doesExist() {
		
	// }
}