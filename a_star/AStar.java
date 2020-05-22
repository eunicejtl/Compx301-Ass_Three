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

			//FIND GOAL; AND START STATE
			while(line != null) {

				System.out.println("Line: " + line + " Total Lines: "  + totalNumLines);

				//GET THE LINE AND X POS OF G
				if(line.contains("G")) {

					//FIND Y COORDINATE OF GOAL
					gYCoord = totalNumLines;

					//FIND X COORDINATE OF GOAL
					gXcoord = line.indexOf("G");

					goalState = new State(gXcoord, gYCoord);
				}

				//GET THE LINE AND X POS OF G
				if(line.contains("S")) {

					//FIND Y COORDINATE OF GOAL
					sYCoord = totalNumLines;

					//FIND X COORDINATE OF GOAL
					sXcoord = line.indexOf("S");

					startState = new State(sXcoord, sYCoord);
				}

				totalNumLines++;
				line = reader.readLine();
			}

			//SET UP START STATE IN FRONTIER
			frontier = new Frontier(startState);

			System.out.println("");
			System.out.println("GOAL: ( "  + gXcoord + ", " + gYCoord + " )");
			System.out.println("");
			System.out.println("START: ( "  + sXcoord + ", " + sYCoord + " )");
			System.out.println("");

			
			//HERE IS THE ALGORITHM
			

			possiblePaths = move(startState);

			for (int i=0; i < possiblePaths.size() ; i++) {
				
				System.out.println("");
				// System.out.println("Possible Paths inside main");
				System.out.println("( " + possiblePaths.get(i).getXCoord() + ", " + possiblePaths.get(i).getYCoord() + " )");
				System.out.println("");

				frontier.add(possiblePaths.get(i));
			}


		}
		catch(Exception eAStar) {

			System.err.println("Error: " + eAStar);
		}
	}

	public static ArrayList<State> move(State currState) {

		// System.out.println("");
		// System.out.println("STARTING MOVE");
		
		ArrayList<State> possiblePaths = new ArrayList<State>();

		try {

			String line = mReader.readLine();
			int lineIndex = 0;

			
			State path;
			int currXCoord = currState.getXCoord();
			int currYCoord = currState.getYCoord();

			//WHILE NOT END OF THE LINE
			while (line != null) {

				// System.out.println("Line: " + lineIndex);

				//CHECK IF UP IS A POSSIBLE PATH
				//IF LINE IS ABOVE THE CURR STATE
				if (lineIndex == (currYCoord - 1)) {

					// System.out.println("	Line: " + lineIndex);
					
					//CHECK IF SAME XCOORD IS POSSIBLE PATH (BUT ABOVE)
					if (line.charAt(currXCoord) == ' ') {
						
						path = new State(currXCoord, currYCoord-1);
						possiblePaths.add(path);
					}
				}
				
				//CHECK IF SIDE IS A POSSIBLE PATHS
				//IF LINE IS THE SAME AS CURR STATE
				if (lineIndex == (currYCoord)) {

					// System.out.println("	Line: " + lineIndex);
					
					//IF CURR STATE'S LEFT IS A POSSIBLE PATH
					if (line.charAt(currXCoord-1) == ' ') {
						
						//CREATE A STATE AND ADD INTO POSSIBLEPATHS
						path = new State(currXCoord-1, currYCoord);
						possiblePaths.add(path);
					}

					//IF CURR STATE'S RIGHT IS A POSSIBLE PATH
					if (line.charAt(currXCoord+1) == ' ') {
						
						//CREATE A STATE AND ADD INTO POSSIBLEPATHS
						path = new State(currXCoord+1, currYCoord);
						possiblePaths.add(path);
					}
				}

				//CHECK IF DOWN IS A POSSIBLE PATH
				//IF LINE IS BELOW THE CURR STATE
				if (lineIndex == (currYCoord + 1)) {

					// System.out.println("	Line: " + lineIndex);
					
					//CHECK IF SAME XCOORD IS POSSIBLE PATH (BUT BELOW)
					if (line.charAt(currXCoord) == ' ') {
						
						path = new State(currXCoord, currYCoord+1);
						possiblePaths.add(path);
					}
				}

				lineIndex++;
				line = mReader.readLine();
			}

			// for (int i=0; i < possiblePaths.size() ; i++) {
				
			// 	System.out.println("");
			// 	System.out.println("Possible Paths inside move");
			// 	System.out.println("( " + possiblePaths.get(i).getXCoord() + ", " + possiblePaths.get(i).getYCoord() + " )");
			// 	System.out.println("");
			// }
		}
		catch (Exception eMove) {
			
			System.err.println("Error: " + eMove);
		}

		// System.out.println("ENDING MOVE");
		return possiblePaths;
	}
}