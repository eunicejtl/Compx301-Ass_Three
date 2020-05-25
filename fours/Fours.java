/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

import java.util.Stack;

class Fours {

	public static void main(String[] args) {

		try {

			//CHECK THERE IS ONLY ONE ARGUMENT
			if(args.length != 1) {

				System.err.println("Enter a number");
				return;
			}

			//DECLARE VARIABLES
			String expression;
			State child;
			State root;
			
			//GET INPUT
			double input = eval(args[0]);

			//SET UP INITIAL STATE
			Frontier queue = new Frontier();

			root = queue.getRoot();
			expression = root.getExpression();

			//WHILE TOTAL OF EXPRESSION IS NOT EQUAL TO THE OUTPUT
			while (eval(expression) != input) {

				//ADD ITS THE CHILDREN AT THE END OF THE LIST
				for (int i = 0; i < 8 ; i++ ) {
					
					child = move(i, root);
					queue.enqueue(child);
				}

				//REMOVE HEAD OF THE QUEUE
				queue.dequeue();
				root = queue.getRoot();
				expression = root.getExpression();
			}

			System.out.println("");
			System.out.println("Found: " + expression);

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
			
			if (index == 0) 	 { child.setExpression(stateExp+" + 4"); }
			else if (index == 1) { child.setExpression(stateExp+" - 4"); }
			else if (index == 2) { child.setExpression(stateExp+" * 4"); }
			else if (index == 3) { child.setExpression(stateExp+" / 4"); }
			else if (index == 4) { child.setExpression(stateExp+" ^ 4"); }
			else if (index == 5) { 

				//Check if our expression has parentheses at the end
				if(stateExp.substring(stateExp.length() - 1) == ")") {
					
					//Multiple the expression inside the brackets with 4
					child.setExpression(stateExp+" * 4"); 
				}
				else {

					child.setExpression(stateExp+"4");
				}
			}
			else if (index == 6) { child.setExpression(stateExp+" + .4"); }
			else if (index == 7) { child.setExpression(" ( " + stateExp + " )"); }
		}

		return child;
	}
 
	public static double eval(String expression) 
	{ 
		String[] tokens = expression.split(""); 

		// Stack for numbers: 'values' 
		Stack<Double> values = new Stack<Double>(); 

		// Stack for Operators: 'operations' 
		Stack<String> operations = new Stack<String>(); 

		for (int i = 0; i < tokens.length; i++) { 

			// If current token is a whitespace, skip it 
			if (tokens[i].compareTo(" ") == 0) { continue; }

			// If current token is a number, push it to stack for numbers 
			if (isNum(tokens[i])) {

				StringBuffer sbuf = new StringBuffer(); 

				// There may be more than one digits in number 
				while (i < tokens.length && isNum(tokens[i])) {

					sbuf.append(tokens[i++]); 
				}

				values.push(Double.parseDouble(sbuf.toString())); 
			} 

			// If current token is an opening brace, push it to 'operations'
			else if (tokens[i].compareTo("(") == 0) { operations.push(tokens[i]); }

			// If closing brace encountered, solve entire brace 
			else if (tokens[i].compareTo(")") == 0) { 

				while (operations.peek().compareTo("(") != 0) {

					values.push(applyOp(operations.pop(), values.pop(), values.pop())); 
				}

				operations.pop(); 
			} 

			// Current token is an operator. 
			else if (tokens[i].compareTo("+") == 0 || tokens[i].compareTo("-") == 0 || 
					tokens[i].compareTo("*") == 0 || tokens[i].compareTo("/") == 0 || 
					tokens[i].compareTo("^") == 0) {

				// While top of 'operations' has same or greater precedence to current 
				// token, which is an operator. Apply operator on top of 'operations' 
				// to top two elements in values stack 
				while (!operations.empty() && hasPrecedence(tokens[i], operations.peek())) {

					values.push(applyOp(operations.pop(), values.pop(), values.pop())); 
				}

				// Push current token to 'operations'. 
				operations.push(tokens[i]); 
			} 
		} 

		// Entire expression has been parsed at this point, apply remaining 
		// operations to remaining values 
		while (!operations.empty()) {

			values.push(applyOp(operations.pop(), values.pop(), values.pop())); 
		}

		// Top of 'values' contains result, return it 
		return values.pop(); 
	}

	static boolean isNum(String _num) {

		String[] NUMBERS = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};

		for (String num : NUMBERS) {

			if (num.compareTo(_num) == 0) {

				return true;
			}
		}

		return false;
	}

	// Returns true if 'op2' has higher or same precedence as 'op1', 
	// otherwise returns false. 
	public static boolean hasPrecedence(String op1, String op2) 
	{ 
		if (op2.compareTo("(") == 0 || op2.compareTo(")") == 0) { return false; }
		if (op1.compareTo("^") == 0 && (op2.compareTo("*") == 0 || op2.compareTo("/") == 0)) { return false; }
		if ((op1.compareTo("*") == 0 || op1.compareTo("/") == 0 || op1.compareTo("^") == 0) && 
			(op2.compareTo("+") == 0 || op2.compareTo("-") == 0)) { return false; }
		else {

			return true; 
		}
	} 

	// A utility method to apply an operator 'op' on operands 'a' 
	// and 'b'. Return the result. 
	public static double applyOp(String op, double b, double a) 
	{ 
		switch (op) {

			case "+": {
				return a + b; 
			}
			case "-": {
				return a - b; 
			}
			case "*": {
				return a * b; 
			}
			case "/": {
				if (b == 0) {
					throw new
					UnsupportedOperationException("Cannot divide by zero"); 
				}
				return a / b;
			}
			case "^": {
	            return Math.pow(a, b);
			}
		}
		
		return 0; 
	} 
}