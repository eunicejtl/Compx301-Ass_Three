/* Name: Meecah Cahayon + Eunice Llobet
 * Student ID: 1259825 + 1330233
 */

import java.util.*;
import java.io.*;

import java.util.Stack;

class Fours {

	public static void main(String[] args) {

		try {

			// //Check there's only one argument
			// if(args.length != 1) {

			// 	System.err.println("Enter a number");
			// 	return;
			// }

			//DECLARE VARIABLES
			String expression;
			State child;
			State root;
			
			//GET INPUT
			//double input = eval(args[0]);

			//Set up initial state
			Frontier queue = new Frontier();

			// root = queue.getRoot();
			// expression = root.getExpression();

			int num = 1;

			while (num != 101) {

				queue = new Frontier();
				root = queue.getRoot();
				expression = root.getExpression();

				while (eval(expression) != num) {

					// System.out.println("Debug: Looking");

					//ADDING THE CHILDREN AT THE END OF THE LIST
					for (int i = 0; i < 8 ; i++ ) {
						
						child = move(i, root);
						queue.enqueue(child);
					}

					//REMOVE HEAD OF THE QUEUE
					queue.dequeue();
					root = queue.getRoot();
					expression = root.getExpression();
					//System.out.println("Debug: new expression = " + expression);
					//System.out.println("");
				}

				System.out.println("");
				System.out.println("FOUND: " + num + ": " + expression);
				System.out.println("");
				
				num++;
			}

			// while (eval(expression) != input) {

			// 	//System.out.println("Debug: expression = " + expression);

			// 	//ADDING THE CHILDREN AT THE END OF THE LIST
			// 	for (int i = 0; i < 8 ; i++ ) {
					
			// 		child = move(i, root);
			// 		queue.enqueue(child);
			// 	}

			// 	//REMOVE HEAD OF THE QUEUE
			// 	queue.dequeue();
			// 	root = queue.getRoot();
			// 	expression = root.getExpression();
			// 	//System.out.println("Debug: new expression = " + expression);
			// 	//System.out.println("");
			// }

			// System.out.println("");
			// System.out.println("Found: " + expression);
			// System.out.println("");

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

					child.setExpression(stateExp+" 4");
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

		// Stack for Operators: 'ops' 
		Stack<String> ops = new Stack<String>(); 

		for (int i = 0; i < tokens.length; i++) 
		{ 
			// Current token is a whitespace, skip it 
			if (tokens[i].compareTo(" ") == 0) 
				continue; 

			// Current token is a number, push it to stack for numbers 
			//if (tokens[i] >= '0' && tokens[i] <= '9')
			if (isNum(tokens[i]))
			{ 
				StringBuffer sbuf = new StringBuffer(); 
				// There may be more than one digits in number 
				//while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
				while (i < tokens.length && isNum(tokens[i])) 
					sbuf.append(tokens[i++]); 
				values.push(Double.parseDouble(sbuf.toString())); 
			} 

			// Current token is an opening brace, push it to 'ops' 
			//else if (tokens[i] == '(')
			else if (tokens[i].compareTo("(") == 0)
				ops.push(tokens[i]); 

			// Closing brace encountered, solve entire brace 
			//else if (tokens[i] == ')')
			else if (tokens[i].compareTo(")") == 0)
			{ 
				while (ops.peek().compareTo("(") != 0) 
				values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
				ops.pop(); 
			} 

			// Current token is an operator. 
			else if (tokens[i].compareTo("+") == 0 || tokens[i].compareTo("-") == 0 || 
					tokens[i].compareTo("*") == 0 || tokens[i].compareTo("/") == 0 || 
					tokens[i].compareTo("^") == 0) 
			{ 
				// While top of 'ops' has same or greater precedence to current 
				// token, which is an operator. Apply operator on top of 'ops' 
				// to top two elements in values stack 
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) 
				values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

				// Push current token to 'ops'. 
				ops.push(tokens[i]); 
			} 
		} 

		// Entire expression has been parsed at this point, apply remaining 
		// ops to remaining values 
		while (!ops.empty()) 
			values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

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
		if (op2.compareTo("(") == 0 || op2.compareTo(")") == 0) 
			return false;
		if (op1.compareTo("^") == 0 && (op2.compareTo("*") == 0 || op2.compareTo("/") == 0))
			return false;
		if ((op1.compareTo("*") == 0 || op1.compareTo("/") == 0 || op1.compareTo("^") == 0) && 
			(op2.compareTo("+") == 0 || op2.compareTo("-") == 0)) 
			return false; 
		else
			return true; 
	} 

	// A utility method to apply an operator 'op' on operands 'a' 
	// and 'b'. Return the result. 
	public static double applyOp(String op, double b, double a) 
	{ 
		switch (op) 
		{ 
		case "+": 
			return a + b; 
		case "-": 
			return a - b; 
		case "*": 
			return a * b; 
		case "/": 
			if (b == 0) 
				throw new
				UnsupportedOperationException("Cannot divide by zero"); 
			return a / b;
          case "^":
            return Math.pow(a, b);
		} 
		return 0; 
	}

	/*// Driver method to test above methods 
	public static void main(String[] args) 
	{ 
		System.out.println(EvaluateString.evaluate("10+2^6")); 
		System.out.println(EvaluateString.evaluate("2 ^ 4 + 100 * 2 + 12")); 
		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )")); 
		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"));
        System.out.println(EvaluateString.evaluate("2 ^ 2"));
	} */

	/* NEW */

	/*public static double eval(final String str) {

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

				if (pos < str.length()) throw new RuntimeException("Unexpected in Parse: " + (char)ch);

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
				
					throw new RuntimeException("Unexpected in Factor: " + (char)ch);
				}

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}*/

	/* NEW */

	// public static double eval(final String str) {

	// 	return new Object() {

	// 		//DECLARING VARIABLES
	// 		int index;

	// 		double parser() {

	// 			//INDEX OF EXPRESSION
	// 			index = 0;

	// 			double total = expression();

	// 			//IF DID NOT CHECK ALL EXPRESSION
	// 			if (index < str.length()) {
					
	// 				error("In Parser");
	// 			}

	// 			return total;
	// 		}

	// 		double expression() {

	// 			double total = term();

	// 			if (index < str.length()) {

	// 				if (str.charAt(index) == '4' || str.charAt(index) == '(' ||
	// 					str.charAt(index) == '+' || str.charAt(index) == '-') {

	// 					total = expression();
						
	// 				}
	// 			}

	// 			return total;
	// 		}

	// 		double term() {

	// 			double total = factor();

	// 			if (index < str.length()) {

	// 				if (str.charAt(index) == '*') {

	// 					index++;
	// 					total *= factor(); 
	// 				}
	// 				else if (str.charAt(index) == '/') {

	// 					index++;
	// 					total /= factor(); 
	// 				}
	// 			}

	// 			return total;
	// 		}

	// 		double factor() {

	// 			double total = 0;
	// 			String strNum = "";

	// 			if (str.charAt(index) == '+') {

	// 				index++;
	// 				total += factor();
	// 			}
	// 			else if (str.charAt(index) == '-') {

	// 				index++;
	// 				total += factor();
	// 			}

	// 	    	if (str.charAt(index) == '4' || str.charAt(index) == '.') {

	// 	    		while (str.charAt(index) == '4' || str.charAt(index) == '.') {

	// 	    			strNum += String.valueOf(str.charAt(index));
	// 	    			index++;
	// 	    		}

	// 	    		total = Double.parseDouble(strNum);
	// 	    	}
	// 	    	else if (str.charAt(index) == '(') {
					
	// 				index++;
	// 				total = expression();

	// 	    		if (index < str.length()) {

	// 	    			//CHECK '(' HAS MATCHING ')'
	// 		    		if (str.charAt(index) == ')') {

	// 		    			index++;
	// 		    		}
	// 		    		else {

	// 		    			error("In Factor: ) -> else");
	// 		    		}
	// 		    	}
	// 		    	else {

	// 		    		error("In Factor: str.length() -> else");
	// 		    	}
	// 			}

	// 			if (str.charAt(index) == '^') {
					
	// 				total = Math.pow(total, factor());
	// 			}

	// 			return total;
	// 		}

	// 		void error(String where) {

	// 			throw new RuntimeException("Unexpected: " + where);
	// 		}
	// 	}.parser();
	// }
}