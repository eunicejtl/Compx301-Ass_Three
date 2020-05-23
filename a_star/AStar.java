import java.util.*;
import java.io.*;

class AStar {

	public static FileReader mfile;
	public static BufferedReader mReader;

	public static void main(String[] args) {

		try {

			if(args.length != 1) {

				System.err.println("Please enter a text file to read.");
			}

			//READ FILE
			FileReader file = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(file);

			mfile = new FileReader(args[0]);
			mReader = new BufferedReader(mfile);

			//DECLARE VARIABLES
			String line = reader.readLine();

			ArrayList<State> possiblePaths = new ArrayList<State>();
			State goalState = new State(0,0);
			State startState = new State(0,0);

			int totalNumLines = 0;
			int gXcoord = 0;
			int gYCoord = 0;
			int sXcoord = 0;
			int sYCoord = 0;
			
			Frontier frontier;
			State lowestF;
			double heuristic = 0;
			double cost = 0;
			double fValue = heuristic + cost;

			//FIND GOAL; AND START STATE
			while(line != null) {

				//System.out.println("Line: " + line + " Total Lines: "  + totalNumLines);

				//GET THE LINE AND X POS OF G
				if(line.contains("G")) {

					//FIND Y COORDINATE OF GOAL
					gYCoord = totalNumLines;

					//FIND X COORDINATE OF GOAL
					gXcoord = line.indexOf("G");

					goalState = new State(gXcoord, gYCoord);
					//System.out.println("Goal State is: " + goalState.getXCoord() + ", " + goalState.getYCoord()); 
				}

				//GET THE LINE AND X POS OF G
				if(line.contains("S")) {

					//FIND Y COORDINATE OF GOAL
					sYCoord = totalNumLines;

					//FIND X COORDINATE OF GOAL
					sXcoord = line.indexOf("S");

					cost = 0;
					startState = new State(sXcoord, sYCoord);

					//Storing the heuristic and cost value as well as setting f value
					startState.setfValue(cost, calcHeuristic(startState, goalState));
				}

				totalNumLines++;
				line = reader.readLine();
			}

			/*System.out.println("");
			System.out.println("GOAL: ( "  + gXcoord + ", " + gYCoord + " )");
			System.out.println("START: ( "  + sXcoord + ", " + sYCoord + " )");
			System.out.println("");*/

			//SET UP START STATE IN FRONTIER
			frontier = new Frontier(startState);
			System.out.println("START: ( "  + startState.getXCoord() + ", " + startState.getYCoord() + " )");
			lowestF = frontier.getLowestF();
			System.out.println("LOWEST F: ( "  + lowestF.getXCoord() + ", " + lowestF.getYCoord() + " )");

			System.out.println("HEURISTIC VALUE OF LOWEST F: "  + lowestF.getHeuristic());


			//WHILE PATH(SMALLEST FVALUE) IS NOT GOAL
			while(lowestF.getHeuristic() != 0) {

				System.out.println("IN THE WHILE LOOP... ");

				possiblePaths = move(lowestF);

				if(possiblePaths.size() != 0) {

					System.out.println("THE LIST IS NOT EMPTY");

					for(State s: possiblePaths) {

						System.out.println("POSSIBLE PATHS: ( "  + s.getXCoord() + ", " + s.getYCoord() + " )");
					}
				}
				else {

					System.out.println("THE LIST IS EMPTY");
					break;
				}

				for (int i=0; i < possiblePaths.size() ; i++) {
					
					System.out.println("");
					System.out.println("( " + possiblePaths.get(i).getXCoord() + ", " + possiblePaths.get(i).getYCoord() + " )");

					
					System.out.println("CHECKING THE LOWEST COST WITHIN THE FRONTIER: " + frontier.getLowestCost(possiblePaths.get(i)));
					if(frontier.getLowestCost(possiblePaths.get(i))) {

						frontier.add(possiblePaths.get(i));

						//SET THE HEURISTIC VALUE AND COST OF NEW STATE
						cost++;
						heuristic = calcHeuristic(possiblePaths.get(i), goalState);

						//MUST SET THE FVALUE OF NEW STATE
						possiblePaths.get(i).setfValue(cost, heuristic);

						System.out.println("NEW PATH ADDING: ( " + possiblePaths.get(i).getXCoord() + " , " + possiblePaths.get(i).getYCoord() + " )");
					}
				}
				frontier.remove(lowestF);

				lowestF = frontier.getLowestF();
				System.out.println("NEW LOWEST F VALUE: ( " + frontier.getLowestF().getXCoord() + ", " + frontier.getLowestF().getYCoord() + " )");
				System.out.println("HEURISTIC OF NEW STATE: " + frontier.getLowestF().getHeuristic());
			}
		}
		catch(Exception eAStar) {

			System.err.println("Error: " + eAStar);
		}
	}

	//This method is used to get the list of possible paths a current state can go to
	//Also checks if the state has encountered a previous path
	public static ArrayList<State> move(State currState) {

		System.out.println("");
		System.out.println("WITHIN THE MOVE METHOD");
		System.out.println("CURRENT STATE'S COORDS: (" + currState.getXCoord() + " , " + currState.getYCoord() + " )");
		System.out.println("");
		
		ArrayList<State> possiblePaths = new ArrayList<State>();

		try {

			String line = mReader.readLine();
			int lineIndex = 0;
			
			State path;

			//Get the coordinates of the state we are looking at
			int currXCoord = currState.getXCoord();
			int currYCoord = currState.getYCoord();

			//WHILE NOT END OF THE LINE
			while (line != null) {

				// System.out.println("Line: " + lineIndex);

				//CHECK IF UP IS A POSSIBLE PATH
				//IF LINE IS ABOVE THE CURR STATE
				if (lineIndex == (currYCoord - 1)) {

					System.out.println("	Line: " + lineIndex);
					
					//CHECK IF SAME XCOORD IS POSSIBLE PATH (BUT ABOVE)
					if (line.charAt(currXCoord) != 'X') {
						
						//IF ITS NOT PREVIOUS PATH
						//if (!((currState.getPP().getXCoord() == currXCoord) && 
						//	(currState.getPP().getYCoord() == (currYCoord-1)))) {

							//CREATE A STATE AND ADD INTO POSSIBLEPATHS
							path = new State(currXCoord, currYCoord-1);
							path.setPP(currState);
							possiblePaths.add(path);
						//}
						
					}
				}
				
				//CHECK IF SIDE IS A POSSIBLE PATHS
				//IF LINE IS THE SAME AS CURR STATE
				if (lineIndex == (currYCoord)) {

					// System.out.println("	Line: " + lineIndex);
					
					//IF CURR STATE'S LEFT IS A POSSIBLE PATH
					if (line.charAt(currXCoord-1) != 'X') {

						//if (!((currState.getPP().getXCoord() == (currXCoord-1)) && 
						//	(currState.getPP().getYCoord() == (currYCoord)))) {

							//CREATE A STATE AND ADD INTO POSSIBLEPATHS
							path = new State(currXCoord-1, currYCoord);
							path.setPP(currState);
							possiblePaths.add(path);
						//}
					}

					//IF CURR STATE'S RIGHT IS A POSSIBLE PATH
					if (line.charAt(currXCoord+1) != 'X') {

						//if (!((currState.getPP().getXCoord() == (currXCoord+1)) && 
						//	(currState.getPP().getYCoord() == (currYCoord)))) {

							//CREATE A STATE AND ADD INTO POSSIBLEPATHS
							path = new State(currXCoord+1, currYCoord);
							path.setPP(currState);
							possiblePaths.add(path);
						//}
					}
				}

				//CHECK IF DOWN IS A POSSIBLE PATH
				//IF LINE IS BELOW THE CURR STATE
				if (lineIndex == (currYCoord + 1)) {

					// System.out.println("	Line: " + lineIndex);
					
					//CHECK IF SAME XCOORD IS POSSIBLE PATH (BUT BELOW)
					if (line.charAt(currXCoord) != 'X') {

						//if (!((currState.getPP().getXCoord() == (currXCoord)) && 
						//	(currState.getPP().getYCoord() == (currYCoord+1)))) {

							//CREATE A STATE AND ADD INTO POSSIBLEPATHS
							path = new State(currXCoord, currYCoord+1);
							path.setPP(currState);
							possiblePaths.add(path);
						//}
					}
				}

				lineIndex++;
				line = mReader.readLine();
			}
		}
		catch (Exception eMove) {
			
			System.err.println("Error: " + eMove);
		}

		System.out.println("");
		System.out.println("THIS IS WITHIN THE MOVE METHOD: ");
		System.out.println("");

		for (int i=0; i < possiblePaths.size() ; i++) {
				
		 	System.out.println("");
			System.out.println("Possible Paths inside move");
			System.out.println("( " + possiblePaths.get(i).getXCoord() + ", " + possiblePaths.get(i).getYCoord() + " )");
			System.out.println("");
		}

		System.out.println("ENDING MOVE");
		return possiblePaths;
	}

	//CALCULATE THE HEURISTIC VALUE OF A STATE USING EUCLIDEAN DISTANCE
	//sqrt((current.x - goal.x)^2 + (current.y - goal.y)^2)
	//public double calcHeuristic(int currXCoord, int currYCoord, int goalXCoord, int goalYCoord) {
	public static double calcHeuristic(State currState, State goalState) {
		
		//double distance = Math.sqrt(Math.pow(currXCoord - goalXCoord, 2) + Math.pow(currYCoord - goalYCoord, 2));
		double distance = Math.sqrt(Math.pow(currState.getXCoord() - goalState.getXCoord(), 2) + Math.pow(currState.getYCoord() - goalState.getYCoord(), 2));
			
		return distance;
	}

	// //CALCULATE THE F_VALUE OF THE STATE
	// public static double calcF(double cost, double heuristic) {

	// 	double f_value = cost + heuristic;

	// 	return f_value;
	// }
}