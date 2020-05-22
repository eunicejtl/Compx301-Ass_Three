import java.util.*;
import java.io.*;

class test {

	private static ArrayList<String> lines = new ArrayList<String>();
	
	public static void main(String[] args) {

		try {

			FileReader file = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(file);

			//Store lines in the list
			//Modify in the list
			//then rewrite the strings from the list onto the file

			String line = br.readLine();
			int numLines = 0;
			
			while(line != null) {

				lines.add(line);

				numLines++;
				line = br.readLine();
			}

			System.out.println("Number of lines: " + numLines);

			for(int i = 0; i < lines.size(); i++) {

				//System.out.println("line: " + s);
				//System.out.println();

				if(lines.get(i).contains("S")) {

					//Check if right side has a symbol (method does this)
					//FIRST get the index of state
					//THEN nagivagate to right and left using index
					
					//Get the line that has the start state
					String start_line = lines.get(i);

					//Get the line index of the start state
					int index = lines.get(i).indexOf("S");
					
					//Check right side if it's a valid path
					replace(start_line, index+1, i);
					//Check left side if it's a valid path
					replace(start_line, index-1, i);
					//Check up if it's a valid path
					replace(lines.get(i-1), index, i-1);
					//Check down if it's a valid path
					replace(lines.get(i+1), index, i+1);
				}
 
				//System.out.println("Line: " + lines.get(i));
			}

			for(String s: lines) {

				System.out.println(s);
			}

		}
		catch(Exception ex) {

			System.err.println(ex);
		}
	}

	//The line passed is to check whether the index is a valid path
	//If it is a valid path, the white space is replaced with '.'
	//The lineIndex is used to check which part of the string we are checking
	//And the arrayIndex is used to be able to replace the correct string in the array list
	private static void replace(String line, int lineIndex, int arrayIndex) {

		/*System.out.println("Line passed: " + line);
		System.out.println("lineIndex: " + lineIndex);
		System.out.println("arrayIndex: " + arrayIndex);*/

		StringBuilder new_line;

		//If the start has a white space to the right
		if(line.charAt(lineIndex) == ' ') {

			//Replace white space with '.'
			new_line = new StringBuilder(line);
			new_line.setCharAt(lineIndex, '.');

			String array_line = new_line.toString();

			//Change line in the array
			lines.set(arrayIndex, array_line);
			//System.out.println("Array Index = " + arrayIndex + " array_line: " + array_line);

			//System.out.println("Replaced Line: " + array_line);
			//System.out.println("Array line: " + lines.get(arrayIndex));
		}
		else if(line.charAt(lineIndex) == 'x') {

			//do nothing
			return;
		}
		else if(line.charAt(lineIndex) == 'G') {

			//?
		}

		//To check 'up', pass in a different string with the same index 
	}
}