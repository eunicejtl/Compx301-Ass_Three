import java.util.*;
import java.io.*;

class State {

	//DECLARE VARIABLES
	private int _xCoord;
	private int _yCoord;

	private double _fValue;
	private int _cost;	
	private double _heuristicValue;
	
	private State _next;
	

	/* CONSTRUCTOR */

	//MAINLY USED FOR GOAL AND STARTER
	public State(int xCoord, int yCoord) {

		_xCoord = xCoord;
		_yCoord = yCoord;
	}

	public State(int xCoord, int yCoord, double heuristicValue, int cost, double fValue) {

		_xCoord = xCoord;
		_yCoord = yCoord;
		_heuristicValue = heuristicValue;
		_cost = cost;
		_fValue = fValue;

	}

	/* METHOD */

	public int getXCoord() {

		return _xCoord;
	}

	public int getYCoord() {

		return _yCoord;
	}

	public int getCost() {

		return _cost;
	}

	public double getHeuristic() {

		return _heuristicValue;
	}

	public double getFValue() {

		return _fValue;
	}

	public void setNext(State next) {

		_next = next;
	}

	public State getNext() {

		return _next;
	}

}