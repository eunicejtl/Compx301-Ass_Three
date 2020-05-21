import java.util.*;
import java.io.*;

import java.util.Stack;

// public class EvaluateString {

// 	// Driver method to test above methods 
// 	public static void main(String[] args) {

// 		System.out.println(EvaluateString.evaluate("10 + 2 * 6")); 
// 		System.out.println(EvaluateString.evaluate("100 * 2 + 12")); 
// 		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )")); 
// 		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"));
// 		System.out.println(EvaluateString.evaluate("2^2"));
// 	} 

// 	public static int evaluate(String expression) {

// 		char[] tokens = expression.toCharArray(); 

// 		// Stack for numbers: 'values' 
// 		Stack<Integer> values = new Stack<Integer>(); 

// 		// Stack for Operators: 'ops' 
// 		Stack<Character> ops = new Stack<Character>(); 

// 		for (int i = 0; i < tokens.length; i++) {

// 			// Current token is a whitespace, skip it 
// 			if (tokens[i] == ' ') {

// 				continue;
// 			}

// 			// Current token is a number, push it to stack for numbers 
// 			if (tokens[i] >= '0' && tokens[i] <= '9') {

// 				StringBuffer sbuf = new StringBuffer(); 
// 				// There may be more than one digits in number 
// 				while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') 
// 				sbuf.append(tokens[i++]); 
// 				values.push(Integer.parseInt(sbuf.toString())); 
// 			} 
// 			// Current token is an opening brace, push it to 'ops' 
// 			else if (tokens[i] == '(') {

// 				ops.push(tokens[i]); 
// 			}
// 			// Closing brace encountered, solve entire brace 
// 			else if (tokens[i] == ')') {

// 				while (ops.peek() != '(') {

// 					values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
// 					ops.pop();
// 				}
// 			} 
// 			// Current token is an operator. 
// 			else if (tokens[i] == '+' || tokens[i] == '-' || 
// 						tokens[i] == '*' || tokens[i] == '/') {

// 				// While top of 'ops' has same or greater precedence to current 
// 				// token, which is an operator. Apply operator on top of 'ops' 
// 				// to top two elements in values stack 
// 				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) 
// 				values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

// 				// Push current token to 'ops'. 
// 				ops.push(tokens[i]); 
// 			} 
// 		} 

// 		// Entire expression has been parsed at this point, apply remaining 
// 		// ops to remaining values 
// 		while (!ops.empty()) 
// 		values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

// 		// Top of 'values' contains result, return it 
// 		return values.pop(); 
// 	} 

// 	// Returns true if 'op2' has higher or same precedence as 'op1', 
// 	// otherwise returns false. 
// 	public static boolean hasPrecedence(char op1, char op2) {

// 		if (op2 == '(' || op2 == ')') {
// 			return false;
// 		}
// 		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
// 			return false; 
// 		}
// 		else {
// 			return true; 
// 		}
// 	} 

// 	// A utility method to apply an operator 'op' on operands 'a' 
// 	// and 'b'. Return the result. 
// 	public static int applyOp(char op, int b, int a) {

// 		switch (op) { 

// 			case '+': 
// 				return a + b; 
// 			case '-': 
// 				return a - b; 
// 			case '*': 
// 				return a * b; 
// 			case '/': 
// 				if (b == 0) {

// 					throw new UnsupportedOperationException("Cannot divide by zero"); 
// 				}
// 				return a / b;
// 			// case '^':
// 			// 	return Math.pow(a, b);
// 		} 
// 		return 0; 
// 	}
// }

/* NEW */

public class EvaluateString 
{ 

	public static double evaluate(String expression) 
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

	// Driver method to test above methods 
	public static void main(String[] args) 
	{ 
		System.out.println(EvaluateString.evaluate("10 + 2 ^ 6")); 
		System.out.println(EvaluateString.evaluate("2 ^ 4 + 100 * 2 + 12")); 
		System.out.println(EvaluateString.evaluate(" 100 * ( 2 + 12 ) ")); 
		System.out.println(EvaluateString.evaluate(" 100 * ( 2 + 12 ) / 14 "));
        System.out.println(EvaluateString.evaluate("2 ^ 2"));
        System.out.println(EvaluateString.evaluate("4 + 4.4"));
	} 
}

/* NEW */
// public class EvaluateString {

// 	public static double evaluate(String expression) 
// 	{ 
// 		char[] tokens = expression.toCharArray(); 

// 		// Stack for numbers: 'values' 
// 		Stack<Double> values = new Stack<Double>(); 

// 		// Stack for Operators: 'ops' 
// 		Stack<Character> ops = new Stack<Character>(); 

// 		for (int i = 0; i < tokens.length; i++) 
// 		{ 
// 			// Current token is a whitespace, skip it 
// 			if (tokens[i] == ' ') 
// 				continue; 

// 			// Current token is a number, push it to stack for numbers 
// 			if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') 
// 			{ 
// 				StringBuffer sbuf = new StringBuffer(); 
// 				// There may be more than one digits in number 
// 				while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') 
// 					sbuf.append(tokens[i++]); 
// 				values.push(Double.parseDouble(sbuf.toString())); 
// 			} 

// 			// Current token is an opening brace, push it to 'ops' 
// 			else if (tokens[i] == '(') 
// 				ops.push(tokens[i]); 

// 			// Closing brace encountered, solve entire brace 
// 			else if (tokens[i] == ')') 
// 			{ 
// 				while (ops.peek() != '(') 
// 				values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
// 				ops.pop(); 
// 			} 

// 			// Current token is an operator. 
// 			else if (tokens[i] == '+' || tokens[i] == '-' || 
// 					tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') 
// 			{ 
// 				// While top of 'ops' has same or greater precedence to current 
// 				// token, which is an operator. Apply operator on top of 'ops' 
// 				// to top two elements in values stack 
// 				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) 
// 				values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

// 				// Push current token to 'ops'. 
// 				ops.push(tokens[i]); 
// 			} 
// 		} 

// 		// Entire expression has been parsed at this point, apply remaining 
// 		// ops to remaining values 
// 		while (!ops.empty()) 
// 			values.push(applyOp(ops.pop(), values.pop(), values.pop())); 

// 		// Top of 'values' contains result, return it 
// 		return values.pop(); 
// 	} 

// 	// Returns true if 'op2' has higher or same precedence as 'op1', 
// 	// otherwise returns false. 
// 	public static boolean hasPrecedence(char op1, char op2) 
// 	{ 
// 		if (op2 == '(' || op2 == ')') 
// 			return false;
// 		if ((op1 == '^') && (op2 == '*' || op2 == '/'))
// 			return false;
// 		if ((op1 == '*' || op1 == '/' || op1 == '^') && (op2 == '+' || op2 == '-')) 
// 			return false; 
// 		else
// 			return true; 
// 	} 

// 	// A utility method to apply an operator 'op' on operands 'a' 
// 	// and 'b'. Return the result. 
// 	public static double applyOp(char op, double b, double a) 
// 	{ 
// 		switch (op) 
// 		{ 
// 		case '+': 
// 			return a + b; 
// 		case '-': 
// 			return a - b; 
// 		case '*': 
// 			return a * b; 
// 		case '/': 
// 			if (b == 0) 
// 				throw new
// 				UnsupportedOperationException("Cannot divide by zero"); 
// 			return a / b;
//           case '^':
//             return Math.pow(a, b);
// 		} 
// 		return 0; 
// 	} 

// 	// Driver method to test above methods 
// 	public static void main(String[] args) 
// 	{ 
// 		System.out.println(EvaluateString.evaluate("10+2^6")); 
// 		System.out.println(EvaluateString.evaluate("2 ^ 4 + 100 * 2 + 12")); 
// 		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )")); 
// 		System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"));
//         System.out.println(EvaluateString.evaluate("2 ^ 2"));
// 	} 
// }
