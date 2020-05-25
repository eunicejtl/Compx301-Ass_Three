import java.util.*;
import java.io.*;

class State {

	//DECLARE VARIABLES
	private int _xCoord;
	private int _yCoord;

	private double _cost;	
	private double _heuristicValue;
	private double _fValue;
	private State _previousPath;
	
	private State _next;
	
	/* CONSTRUCTOR */

	//MAINLY USED FOR GOAL
	public State(int xCoord, int yCoord) {

		_xCoord = xCoord;
		_yCoord = yCoord;
	}

	/* METHOD */

	public int getXCoord() {

		return _xCoord;
	}

	public int getYCoord() {

		return _yCoord;
	}

	public double getCost() {

		return _cost;
	}

	public double getHeuristic() {

		return _heuristicValue;
	}

	public double getFValue() {

		return _fValue;
	}

	public void setPP(State previousPath) {

		_previousPath = previousPath;
	}

	public State getPP() {

		return _previousPath;
	}

	public void setNext(State next) {

		_next = next;
	}

	public State getNext() {

		return _next;
	}

	/* METHODS */

	//CALCULATE THE F_VALUE OF THE STATE
	public void setfValue(double cost, double heuristic) {

		_heuristicValue = heuristic;
		_cost = cost;
		_fValue = cost + heuristic;

		return;
	}
}