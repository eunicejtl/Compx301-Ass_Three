import java.util.*;
import java.io.*;

class Frontier {

	State _initialState;

	public Frontier(State initialState) {

		_initialState = initialState;
	}

	public void calculateHeuristic(double cX, double cY, double gX, double gY, State state) {

		//USING EUCLIDEAN DISTANCE
		//sqrt(current.x - goal.x)^2 + (current.y - goal.y)^2
		double distance = Math.sqrt(Math.pow(cX - gX, 2) + Math.pow(cY - gY, 2));
			
		state.setHeuristic(distance);
	}

	public void setF(State curr) {

		int moves = curr.getMoves();
		double heuristic = curr.getHeuristic();

		double f_value = moves + heuristic;

		curr.setFValue(f_value);
	}

	public State getLowestF() {

		State curr = _initialState;
		State min_fValue = curr;

		while(curr.getNext() != null) {

			if(curr.getFValue() < curr.getNext().getFValue()) {

				min_fValue = curr.getNext();
				curr = curr.getNext();
			}
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
		if((_initialState.getX() == x) && (_initialState.getY() == y)) {

			_initialState = _initialState.getNext();
		}

		State curr = _initialState;

		while(!(curr.getNext().getX() == x) && (curr.getNext().getY() == y)) {

			curr = curr.getNext();
		}
		curr.setNext(curr.getNext().getNext());
	}


}