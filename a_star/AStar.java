/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

import java.awt.*;
import javax.swing.*;

class AStar extends Canvas {

	private static Frontier frontier;
	private static Frontier visited;
	private static Frontier allNoode;
	private static State goalState = new State(0,0);
	private static State startState = new State(0,0);

	private static ArrayList<String> map = new ArrayList<String>();

	public static void main(String[] args) {

		try {

			//IF USER DID NOT INPUT 1 ARGUMENT
			if(args.length != 1) {

				System.err.println("Please enter a text file to read.");
			}

			//READ FILE
			FileReader file = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(file);
			String line = reader.readLine();

			//DECLARE VARIABLES
			ArrayList<State> possiblePaths = new ArrayList<State>();
			ArrayList<State> shortestPath = new ArrayList<State>();
			State lowestF;
			int totalNumLines = 0;
			
			//FIND GOAL AND START STATE
			while(line != null) {

				map.add(line);

				//GET THE Y AND X POS OF G
				if(line.contains("G")) {

					goalState = new State(line.indexOf("G"), totalNumLines);
				}

				//GET THE Y AND X POS OF G
				if(line.contains("S")) {

					startState = new State(line.indexOf("S"), totalNumLines);
					startState.setfValue(0, calcHeuristic(startState, goalState));
				}

				totalNumLines++;
				line = reader.readLine();
			}

			//SET UP FRONTIERS
			frontier = new Frontier(startState);
			visited = new Frontier(new State(0,0));
			allNoode = new Frontier(startState);
			lowestF = frontier.getLowestF();

			//WHILE PATH(SMALLEST FVALUE) IS NOT GOAL
            while(lowestF.getHeuristic() != 0) {

                //FIND ALL POSSIBLE PATHS OF THE LOWESTF
                possiblePaths = move(lowestF);

                for (int i=0; i < possiblePaths.size() ; i++) {

                    //MULTIPLE PATHPRUNING: IF 2 DIFF PATH CONVERGE ON SAME STATE
                    //DISCARD FROM THE FRONTIER THE ONE WITH LOWEST COST
                    if(frontier.getLowestCost(possiblePaths.get(i))) {

                        //IF THE STATE IS NOT YET VISITED
                        if (!isVisited(possiblePaths.get(i))) {
                        	
                        	//ADD TO FRONTIER AND ALLNODE(FOR GETTING THE SHORTEST PATH)
                        	frontier.add(possiblePaths.get(i));
                        	allNoode.add(possiblePaths.get(i));
                        }
                    }
                }

                //ADDING CURR LOWEST F TO VISITED (SO WE DONT NEED TO ADD IT AGAIN LATER TO REVISIT - MAKES THE PROGRAM FASTER)
                visited.add(new State(lowestF.getXCoord(), lowestF.getYCoord()));
                
                //REMOVING THE LOWEST AND AND FINDING NEW LOWEST F
                frontier.remove(lowestF);
                lowestF = frontier.getLowestF();

                System.out.println("SIZE: " + frontier.getSize());
                System.out.println("");

                //REPLACING LOWESTF TO '.' FROM THE MAP
                replaceCharAt(lowestF, '.');
            }

            System.out.println("GOAL FOUND!");

            //GET THE SHORTEST PATH USING THE LOWESTF(GOAL)
            shortestPath = allNoode.getPath(startState, lowestF);

            //REPLACE EVERY PATH IN THE SHORTEST PATH TO 'P'
            for(State currPath: shortestPath) {

	            replaceCharAt(currPath, 'P');
            }

            //RESETTING GOAL STATE CAHR TO 'G' AND START STATE TO 'S' 
            replaceCharAt(goalState, 'G');
            replaceCharAt(startState, 'S');

            //CREATING GUI
            AStar gui = new AStar(); 
			JFrame frame = new JFrame("Assignment 3: 1259825 + 1330233");

			// JScrollPane pane;
			// JFrame frame = new JFrame("Assignment 3: 1259825 + 1330233");
			// JScrollPane = new JScrollPane();
			// frame.getContentPane().add(pane);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800,800);

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
		int dSize = 20; //NORMALLY 20 BUT CAN CHANGE IF YOU HAVE BIG FILE
		int fSize = 16; //NORMALLY 16 BUT CAN CHANGE IF YOU HAVE BIG FILE

		//FOR EVERY LINE
		for (int i = 0; i < map.size() ; i++) {

			String line = map.get(i);

			//FOR EVERY STING INDEX
			for (int j = 0; j < line.length() ; j++) {

				//IF ITS A BORDER
				if (line.charAt(j) == '+' || line.charAt(j) == '-' || line.charAt(j) == '|') {

					drawBlock(g, new Color(128,76,88), width, height, dSize, fSize, null);
				}
				//IF ITS AN OBSTACLE
				else if (line.charAt(j) == 'X') {

					drawBlock(g, new Color(179,128,139), width, height, dSize, fSize, null);
				}
				//IF ITS THE START STATE
				else if (line.charAt(j) == 'S') {
					
					drawBlock(g, new Color(128,165,179), width, height, dSize, fSize, null);
				}
				//IF ITS THE GOAL STATE
				else if (line.charAt(j) == 'G') {
					

					drawBlock(g, new Color(86,128,144), width, height, dSize, fSize, null);
				}
				//IF IT HAS BEEN THROUGH IT
				// else if (line.charAt(j) == '.') {
				// 
				// 	drawBlock(g, new Color(176,208,185), width, height, dSize, fSize);
				// }
				//IF ITS THE SHORTEST PATH
				else if (line.charAt(j) == 'P') {

					drawBlock(g, new Color(128,179,143), width, height, dSize, fSize, null);
				}
				
				//MOVE 20 RIGHT
				width += 20;
			}

			//MOVE 20 DOWN AND RESET WIDTH TO ORIGINAL
			height += 20;
			width = 10;
		}

		g.setFont(new Font("AvantGrande", Font.PLAIN, 15));

		//Map Border
		height += 30;
		drawBlock(g, new Color(128,76,88), width, height, dSize, fSize, "Map Border");
		//Obstacle
		height += 30;
		drawBlock(g, new Color(179,128,139), width, height, dSize, fSize, "OBSTACLE");
		//Start State
		height += 30;
		drawBlock(g, new Color(128,165,179), width, height, dSize, fSize, "Start");
		//Goal State
		height += 30;
		drawBlock(g, new Color(86,128,144), width, height, dSize, fSize, "Goal");
		//Shortest Path
		height += 30;
		drawBlock(g, new Color(128,179,143), width, height, dSize, fSize, "Shortest Path");
		// //Visited Paths
		// height += 30;
		// drawBlock(g, new Color(176,208,185), width, height, dSize, fSize, "Visited Path");
	}

	//RETURNS ALL POSSIBLE PATH AT A GIVEN STATE
	public static ArrayList<State> move(State currState) {
		
		ArrayList<State> possiblePaths = new ArrayList<State>();
		int currXCoord = currState.getXCoord();
		int currYCoord = currState.getYCoord();

		//CHECK IF UP IS A POSSIBLE PATH
		if ((map.get(currYCoord-1).charAt(currXCoord)) != 'X' && 
			(map.get(currYCoord-1).charAt(currXCoord)) != '|' && 
			(map.get(currYCoord-1).charAt(currXCoord)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord, currYCoord-1));

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord)) && 
				(currState.getPP().getYCoord() == (currYCoord - 1)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord, currYCoord-1));

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
				possiblePaths.add(createState(currState, currXCoord-1, currYCoord));

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord - 1)) && 
				(currState.getPP().getYCoord() == (currYCoord)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord-1, currYCoord));

			}
		}

		//IF CURR STATE'S RIGHT IS A POSSIBLE PATH
		if ((map.get(currYCoord).charAt(currXCoord+1)) != 'X' && 
			(map.get(currYCoord).charAt(currXCoord+1)) != '|' && 
			(map.get(currYCoord).charAt(currXCoord+1)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord+1, currYCoord));

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord + 1)) && 
				(currState.getPP().getYCoord() == (currYCoord)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord+1, currYCoord));

			}
		}

		//CHECK IF DOWN IS A POSSIBLE PATH
		if ((map.get(currYCoord+1).charAt(currXCoord)) != 'X' && 
			(map.get(currYCoord+1).charAt(currXCoord)) != '|' && 
			(map.get(currYCoord+1).charAt(currXCoord)) != '-') {

			//IF CURRSTATE IS THE STARTSTATE == NO PRREVIOUS PATH
			if (frontier.compareState(currState, startState)) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord, currYCoord+1));

			}
			//IF NOT START STATE AND NOT PREVIOUS PATH
			else if (!((currState.getPP().getXCoord() == (currXCoord)) && 
				(currState.getPP().getYCoord() == (currYCoord + 1)))) {

				//CREATE A STATE AND ADD INTO POSSIBLEPATHS
				possiblePaths.add(createState(currState, currXCoord, currYCoord+1));

			}
		}

		return possiblePaths;
	}

	//CHECK IF A GIVEN STATE HAS BEEN VISITED ALREADY
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

	//REPLACING CHAR USING THE STATES X AND Y COORDINATES
	public static void replaceCharAt(State _state, char _char) {

		StringBuilder new_line_sb_;
		String new_line_s_;

		//REPLACING CHAR AT STRING INDEX
		new_line_sb_ = new StringBuilder(map.get(_state.getYCoord()));
        new_line_sb_.setCharAt(_state.getXCoord(), _char);

        //REPLACING TO THE UPDATED STRING
        new_line_s_ = new_line_sb_.toString();
        map.set(_state.getYCoord(), new_line_s_);
	}

	public static State createState(State state, int xCoord, int yCoord) {

		//CREATE A STATE AND SET ITS COST, HEURISTIC VALUE, FVALUE, AND PREVIOUSPATH
		State path = new State(xCoord, yCoord);
		path.setfValue((state.getCost()+1), calcHeuristic(path, goalState));
		path.setPP(state);

		return path;
	}

	public static void drawBlock(Graphics g, Color color, int width, int height, int dSize, int fSize, String string) {

		g.setColor(color);
		g.drawRect(width, height, dSize, dSize);
		g.fillRect(width+2,height+2, fSize, fSize);

		if (string != null) {

			g.drawString(string, width+30, height+15);
		}
	}
}