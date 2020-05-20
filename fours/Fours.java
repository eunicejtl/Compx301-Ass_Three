/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

class Fours {

	public static void main(String[] args) {

		try {

			//Check there's only one argument
			if(args.length != 1) {

				System.err.println("Enter a number");
				return;
			}

			//DECLARE VARIABLES
			State child;
			State curr;

			//GET INPUT
			String input = args[0];
			System.out.println("input: " + input);
			System.out.println("eval: " + eval(input));

			//Set up initial state
			//Frontier moves = new Frontier();
			Frontier queue = new Frontier();

			/*//IF INPUT IS INITIAL STAE (=4)
			if(eval(moves.getRoot().getExpression()) == input) {

				//EXPRESSION FOUND (GOAL FOUND)
				System.out.println(moves.getRoot().getExpression());
				return;
			}*/

			while (eval(queue.getRoot().getExpression()) != input) {
				
				/*
					WHILE input is not head of queue
						ADD children at the end of the list
						POP head
					END WHILE

					PRINT EXPRESSION
				*/

				/*//SETTING MOVES CHILDREN
				String rootExp = moves.getRoot().getExpression()
				moves.getRoot().setChildren(rootExp);*/

				//ADDING THE CHILDREN AT THE END OF THE LIST
				for (int i = 0; i > 8 ; i++ ) {
					
					child = move(i, curr.get)
				}


			}

		}
		catch(Exception eFours) {

			System.err.println("Error: " + eFours);
			return;
		}
	}

	public static State move(int index, State state) {

		String stateExp = state.getExpression();
		State child = new State(stateExp);

		if (!(index < 0 && index > 8)) {
			
			if (index == 0) 	 { child.setExpression(stateExp+"+4"); }
			else if (index == 1) { child.setExpression(stateExp+"-4"); }
			else if (index == 2) { child.setExpression(stateExp+"*4"); }
			else if (index == 3) { child.setExpression(stateExp+"/4"); }
			else if (index == 4) { child.setExpression(stateExp+"^4"); }
			else if (index == 5) { child.setExpression(stateExp+"4"); }
			else if (index == 6) { child.setExpression(stateExp+".4"); }
			else if (index == 7) { "("+child.setExpression(stateExp+")"); }
		}

		return child;
	}

	public static double eval(final String str) {

		return new Object() {

			int pos = -1, ch;

			void nextChar() {

				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {

				while (ch == ' ') nextChar();
				
				if (ch == charToEat) {
					nextChar();
					return true;
				}

				return false;
			}

			double parse() {

				nextChar();

				double x = parseExpression();

				if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);

				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			//        | number | functionName factor | factor `^` factor

			double parseExpression() {

				double x = parseTerm();

				for (;;) {
					
					if      (eat('+')) x += parseTerm(); // addition
					else if (eat('-')) x -= parseTerm(); // subtraction
					else return x;
				}
			}

			double parseTerm() {

				double x = parseFactor();
				
				for (;;) {

					if      (eat('*')) x *= parseFactor(); // multiplication
					else if (eat('/')) x /= parseFactor(); // division
					else return x;
				}
			}

			double parseFactor() {

				if (eat('+')) return parseFactor(); // unary plus
				if (eat('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;

				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} 
				else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers

					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				}
				else {
				
					throw new RuntimeException("Unexpected: " + (char)ch);
				}

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}
}