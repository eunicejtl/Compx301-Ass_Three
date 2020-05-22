import java.util.*;
import java.io.*;

class State {

	//DECLARE VARIABLES
	private int _xCoord;
	private int _yCoord;

	private double _cost;	
	private double _heuristicValue;
	private double _fValue;
	
	private State _next;
	

	/* CONSTRUCTOR */

	//MAINLY USED FOR GOAL
	public State(int xCoord, int yCoord) {

		_xCoord = xCoord;
		_yCoord = yCoord;
	}

	public State(int xCoord, int yCoord, double heuristicValue, double cost, double fValue) {

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

	public void setCost(double cost) {

		_cost = cost;
	}

	public double getCost() {

		return _cost;
	}

	public void setHeuristic(double heuristicValue) {

		_heuristicValue = heuristicValue;
	}

	public double getHeuristic() {

		return _heuristicValue;
	}

	public void setfValue(double fValue) {

		_fValue = fValue;
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