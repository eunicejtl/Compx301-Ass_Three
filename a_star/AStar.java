import java.util.*;
import java.io.*;

import java.awt.*;
import javax.swing.*;

class AStar extends Canvas {

	public static Frontier frontier;
	public static Frontier visited;
	public static State goalState = new State(0,0);
	public static State startState = new State(0,0);

	public static ArrayList<String> map = new ArrayList<String>();

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

			ArrayList<State> possiblePaths = new ArrayList<State>();
			StringBuilder new_line_sb;
			String new_line_s;
			State lowestF;
			int totalNumLines = 0;
			
			//FIND GOAL; AND START STATE
			while(line != null) {

				System.out.println("Line: " + line + " Total Lines: "  + totalNumLines);
				map.add(line);

				//GET THE LINE AND X POS OF G
				if(line.contains("G")) {

					goalState = new State(line.indexOf("G"), totalNumLines);
				}

				//GET THE LINE AND X POS OF G
				if(line.contains("S")) {

					startState = new State(line.indexOf("S"), totalNumLines);
					startState.setfValue(0, calcHeuristic(startState, goalState));
				}

				totalNumLines++;
				line = reader.readLine();
			}

			System.out.println("");
			System.out.println("GOAL: ( "  + goalState.getXCoord() + ", " + goalState.getYCoord() + " )");
			System.out.println("START: ( "  + startState.getXCoord() + ", " + startState.getYCoord() + " )");
			System.out.println("");

			//SET UP START STATE IN FRONTIER
			frontier = new Frontier(startState);
			visited = new Frontier(new State(0,0));
			lowestF = frontier.getLowestF();

			//WHILE PATH(SMALLEST FVALUE) IS NOT GOAL
            while(lowestF.getHeuristic() != 0) {

                possiblePaths = move(lowestF);

                if(possiblePaths.size() != 0) {

					System.out.println("	POSSIBLE PATHS FOR: ( "  + lowestF.getXCoord() + ", " + lowestF.getYCoord() + " )");
					System.out.println("");

					for(State s: possiblePaths) {

						System.out.println("		POSSIBLE PATHS: ( "  + s.getXCoord() + ", " + s.getYCoord() + " )");
					}
				}
				System.out.println("");

                for (int i=0; i < possiblePaths.size() ; i++) {

                    // if(frontier.getLowestCost(possiblePaths.get(i))) {

                    //     frontier.add(possiblePaths.get(i));
                    // }

                    if(frontier.findLowestF(possiblePaths.get(i))) {

                        if (!isVisited(possiblePaths.get(i))) {
                        	
                        	frontier.add(possiblePaths.get(i));
                        }
                    }
                }

                System.out.println("	ADDING TO VISITED");
                System.out.println("");

                visited.add(new State(lowestF.getXCoord(), lowestF.getYCoord()));
                visited.displayFrontier();
                System.out.println("");

                frontier.remove(lowestF);
                lowestF = frontier.getLowestF();

                frontier.displayFrontier();
                System.out.println("");
                System.out.println("SIZE: " + frontier.getSize());
                System.out.println("");
                System.out.println("	NEW LOWEST F: ( "  + lowestF.getXCoord() + ", " + lowestF.getYCoord() + " )");
                System.out.println("");

                new_line_sb = new StringBuilder(map.get(lowestF.getYCoord()));
                new_line_sb.setCharAt(lowestF.getXCoord(), '.');
                new_line_s = new_line_sb.toString();
                map.set(lowestF.getYCoord(), new_line_s);
            }

            new_line_sb = new StringBuilder(map.get(goalState.getYCoord()));
            new_line_sb.setCharAt(goalState.getXCoord(), 'G');
            new_line_s = new_line_sb.toString();
            map.set(goalState.getYCoord(), new_line_s);

            new_line_sb = new StringBuilder(map.get(startState.getYCoord()));
            new_line_sb.setCharAt(startState.getXCoord(), 'S');
            new_line_s = new_line_sb.toString();
            map.set(startState.getYCoord(), new_line_s);

            frontier.displayFrontier();

            //CREATING GUI
            AStar gui = new AStar(); 
			JFrame frame = new JFrame("Assignment 3");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800,800);

			//JButton button = new JButton("Press");
			//frame.getContentPane().add(button);
			frame.add(gui);
			frame.setVisible(true);

		}
		catch(Exception eAStar) {

			System.err.println("Error: " + eAStar);
		}
	}

	public void paint(Graphics g) {

		int width = 10;
		int height = 10;
		int dSize = 20; //20
		int fSize = 16; //16


		for (int i = 0; i < map.size() ; i++) {

			String line = map.get(i);

			for (int j = 0; j < line.length() ; j++) {

				if (line.charAt(j) == '+' || line.charAt(j) == '-' || line.charAt(j) == '|') {
					
					g.setColor(new Color(128,76,88));
					g.drawRect(width, height, dSize, dSize);
					g.fillRect(width+2,height+2, fSize, fSize);

				}
				else if (line.charAt(j) == 'X') {

					g.setColor(new Color(179,128,139));
					g.drawRect(width, height, dSize, dSize);
					g.fillRect(width+2,height+2, fSize, fSize);
				}
				else if (line.charAt(j) == 'S') {
					
					g.setColor(new Color(142,175,188));
					g.drawRect(width, height, dSize, dSize);
					g.fillRect(width+2,height+2, fSize, fSize);
				}
				else if (line.charAt(j) == 'G') {
					
					g.setColor(new Color(85,127,144));
					g.drawRect(width, height, dSize, dSize);
					g.fillRect(width+2,height+2, fSize, fSize);
				}
				else if (line.charAt(j) == '.') {
					
					g.setColor(new Color(198,158,187));
					g.drawRect(width, height, dSize, dSize);
					g.fillRect(width+2,height+2, fSize, fSize);
				}
				width += 20;
			}

			height += 20;
			width = 10;
		}
	}

	public static ArrayList<State> move(State currState) {
		
		ArrayList<State> possiblePaths = new ArrayList<State>();

		State path;
		int currXCoord = currState.getXCoord();
		int currYCoord = currState.getYCoord();

		//CHECK IF UP IS A POSSIBLE PATH
		if ((map.get(currYCoord-1).charAt(currXCoord)) != 'X' && 
			(map.get(currYCoord-1).charAt(currXCoord)) != '|' && 
			(map.get(currYCoord-1).charAt(currXCoord)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord, currYCoord - 1);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord)) && 
				(currState.getPP().getYCoord() == (currYCoord - 1)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord, currYCoord - 1);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);
			}	
		}

		//CHECK IF SIDE IS A POSSIBLE PATHS
		//IF CURR STATE'S LEFT IS A POSSIBLE PATH
		if ((map.get(currYCoord).charAt(currXCoord-1)) != 'X' && 
			(map.get(currYCoord).charAt(currXCoord-1)) != '|' && 
			(map.get(currYCoord).charAt(currXCoord-1)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord - 1 , currYCoord);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord - 1)) && 
				(currState.getPP().getYCoord() == (currYCoord)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord - 1, currYCoord);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);
			}
		}

		//IF CURR STATE'S RIGHT IS A POSSIBLE PATH
		if ((map.get(currYCoord).charAt(currXCoord+1)) != 'X' && 
			(map.get(currYCoord).charAt(currXCoord+1)) != '|' && 
			(map.get(currYCoord).charAt(currXCoord+1)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord + 1, currYCoord);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord + 1)) && 
				(currState.getPP().getYCoord() == (currYCoord)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord + 1, currYCoord);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);
			}
		}

		//CHECK IF DOWN IS A POSSIBLE PATH
		//CHECK IF SAME XCOORD IS POSSIBLE PATH (BUT BELOW)
		if ((map.get(currYCoord+1).charAt(currXCoord)) != 'X' && 
			(map.get(currYCoord+1).charAt(currXCoord)) != '|' && 
			(map.get(currYCoord+1).charAt(currXCoord)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord, currYCoord + 1);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord)) && 
				(currState.getPP().getYCoord() == (currYCoord + 1)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				path = new State(currXCoord, currYCoord + 1);
				path.setfValue((currState.getCost()+1), calcHeuristic(path, goalState));
				path.setPP(currState);
				possiblePaths.add(path);

			}
		}

		return possiblePaths;
	}

	public static boolean isVisited(State state) {

		State curr = visited.getRoot();

		//WHILE NOT END OF THE LIST
		while (curr != null) {

			if (visited.compareState(curr, state)) {
				
				return true;
			}

			curr = curr.getNext();
		}

		return false;
	}

	//CALCULATE THE HEURISTIC VALUE OF A STATE USING EUCLIDEAN DISTANCE
	//sqrt((current.x - goal.x)^2 + (current.y - goal.y)^2)
	public static double calcHeuristic(State currState, State goalState) {
		
		//double distance = Math.sqrt(Math.pow(currXCoord - goalXCoord, 2) + Math.pow(currYCoord - goalYCoord, 2));
		double distance = Math.sqrt(Math.pow(currState.getXCoord() - goalState.getXCoord(), 2) + Math.pow(currState.getYCoord() - goalState.getYCoord(), 2));
			
		return distance;
	}


}