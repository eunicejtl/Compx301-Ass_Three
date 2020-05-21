import java.util.*;
import java.io.*;

class AStar {

	public static void main(String[] args) {

		try {

			if(args.length != 1) {

				System.err.println("Please enter a text file to read.");
			}

			//READ FILE
			FileReader file = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(file);

			//DECLARE VARIABLES
			String line = reader.readLine();
			int totalNumLines = 0;
			int gXcoord = 0;
			int gYCoord = 0;
			int sXcoord = 0;
			int sYCoord = 0;
			
			Frontier frontier;

			//GET THE TOTAL NUMBER OF LINES
			while(line != null) {

				System.out.println("Line: " + line + " Total Lines: "  + totalNumLines);
				totalNumLines++;

				//GET THE LINE AND X POS OF G
				if(line.contains("G")) {

					//FIND Y COORDINATE OF GOAL
					gYCoord = totalNumLines - 1;

					//FIND X COORDINATE OF GOAL
					gXcoord = line.indexOf("G") + 1;

					State goalState = new State(gXcoord, gYCoord);
				}

				//GET THE LINE AND X POS OF G
				if(line.contains("S")) {

					//FIND Y COORDINATE OF GOAL
					sYCoord = totalNumLines + 1;

					//FIND X COORDINATE OF GOAL
					sXcoord = line.indexOf("S") + 1;

					State startState = new State(sXcoord, sYCoord);

					//SET UP START STATE IN FRONTIER
					frontier = new Frontier(startState);
				}

				line = reader.readLine();
			}

			System.out.println("Total Number of Lines " + totalNumLines);
			System.out.println("goal x coordinate: "  + gXcoord + " goal y coordinate: " + gYCoord);
			System.out.println("start x coordinate: "  + sXcoord + " start y coordinate: " + sYCoord);


		}
		catch(Exception eAStar) {

			System.err.println("Error: " + eAStar);
		}
	}
}