import java.util.*;
import java.io.*;

class State {

	//DECLARE VARIABLES
	private int _numMoves;
	private double _heuristicValue;
	//String _path;
	private int xCoord;
	private int yCoord;
	private State _next;
	private double _fValue;

	public State(int x, int y) {

		xCoord = x;
		yCoord = y;
	}

	/*public String getPath() {

		return _path;
	}*/

	public int getX() {

		return xCoord;
	}

	public int getY() {

		return yCoord;
	}

	public void setMoves(int numMoves) {

		_numMoves = numMoves;
	}

	public int getMoves() {

		return _numMoves;
	}

	public void setHeuristic(double heuristicValue) {

		_heuristicValue = heuristicValue;
	}

	public double getHeuristic() {

		return _heuristicValue;
	}

	public double getFValue() {

		return _fValue;
	}

	public void setFValue(double fValue) {

		_fValue = fValue;
	}

	public void setNext(State next) {

		_next = next;
	}

	public State getNext() {

		return _next;
	}

}