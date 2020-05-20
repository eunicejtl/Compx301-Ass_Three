import java.util.*;
import java.io.*;

class Fours {

	public static void main(String[] args) {

		try {

			//Check there's only one argument
			if(args.length != 1) {

				System.err.println("Enter a number.");
			}

			//Get input
			int input = Integer.parseInt(args[0]);
			System.out.println(input);

			//Set up initial state
			Frontier frontier = new Frontier();

			//Check if input is equals to initial state
			if(Integer.parseInt(frontier.getRoot().getState()) == input) {


			}
		}
		catch(Exception eFours) {

			System.err.println("Error: " + eFours);
			return;
		}
	}
}