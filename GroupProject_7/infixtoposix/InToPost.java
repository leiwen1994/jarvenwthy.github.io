package infixtoposix;

import java.util.Stack;

public class InToPost {
	private String infix;
	private String postfix;

	/*
	 * Initializes infix and postfix instance variables.
	 * Throws IllegalArgumentException if the input infix expression has unbalanced parentheses.
	 */
	public InToPost(String given) {
		this.infix = given;
		if (!hasBalancedParentheses(given))
			throw new IllegalArgumentException();

		this.postfix = convertToPost(given);
	}

	/*
	 * DONNOT change, this method is given for you!
	 */
	private String convertToPost(String exp) {
		exp = exp.replaceAll(" ", "");
		Stack<String> stk = new Stack<String>();
		StringBuilder sb = new StringBuilder();
		int precedence = -1; // 0 for {+,-} and 1 for {*,/} (-1 for initialization)
		int scanIndex = 0;
		while (exp.charAt(scanIndex) == '(') {
			stk.push("(");
			++scanIndex;
		}
		int operandEnd = getOperandEnd(exp, scanIndex);
		sb.append(exp.substring(scanIndex, operandEnd) + " ");
		for (scanIndex = operandEnd; scanIndex < exp.length();) {
			char op = exp.charAt(scanIndex);
			if (op == '(') {
				stk.push(op + "");
				++scanIndex;
			} else if (op == ')') {
				while (stk.peek().charAt(0) != '(') sb.append(stk.pop() + " ");
				stk.pop();
				if (stk.isEmpty() || stk.peek().charAt(0) == '(') {
					precedence = -1;
				} else {
					op = stk.peek().charAt(0);
					precedence = (op == '+' || op == '-')? 0 : 1;
				}
				++scanIndex;
			} else {
				int currPrec = (op == '+' || op == '-')? 0 : 1;

				if (currPrec > precedence) {
					stk.push(op + "");
					int operandStart = scanIndex + 1;
					while (exp.charAt(operandStart) == '(') {
						stk.push("(");
						++operandStart;
						currPrec = -1;
					}
					scanIndex = getOperandEnd(exp, operandStart);
					sb.append(exp.substring(operandStart, scanIndex) + " ");
					precedence = currPrec;
				} else {
					sb.append(stk.pop() + " ");
					if (stk.isEmpty() || stk.peek().charAt(0) == '(') {
						precedence = -1;
					} else {
						op = stk.peek().charAt(0);
						precedence = (op == '+' || op == '-')? 0 : 1;
					}
				}
			}
		}
		while (!stk.isEmpty()) sb.append(stk.pop() + " ");
		return sb.toString().trim();
	}

	/*
	 * DONNOT change: this method is given for you!
	 */
	private int getOperandEnd(String exp, int start) {
		int end = start;
		while (exp.charAt(end) == ' ') ++end;

		if (exp.charAt(end) == '-') ++end;

		while (end < exp.length() && Character.isDigit(exp.charAt(end))) ++end;

		return end;
	}

	/*
	 * Evaluate postfix expression (check test cases)
	 * To evaluate a postfix expression, you start by splitting the string on spaces.
	 * Then, for each element:
	 * 		-> if it is an operand, then push it into the stack
	 * 		-> if it is an operator, then 1) pop the last two operands from the stack, 2) perform the operation
	 * 			and 3) push the result into the stack.
	 * After scanning all elements, the stack should have only one item which is the result.
	 *
	 * For example:
	 * infix: 2-3*4
	 * postfix: 2 3 4 * -
	 * solution: -10
	 *
	 * Another example:
	 * infix: 2*3+5
	 * postfix: 2 3 * 5 +
	 * solution: 11
	 */
	public int evaluatePostfix() {
//		String[] post = infix.split(" ");
//		for(int i =0; i< post.length;i++) {
//			if()
//			convertToPost(post[i]);
//			
//		
//			
//		}
		int num1 = 0;
		int num2 = 0;
		int result = 0;
		Stack<String> ip = new Stack<String>();

		//postfix = convertToPost(infix);
		String[] post = postfix.split(" ");
		for(int i =0; i< post.length;i++) {		
			if(post[i].equals("+") || post[i].equals("-") || post[i].equals("*") || post[i].equals("/")) {
				num2 = Integer.parseInt(ip.pop());
				num1 = Integer.parseInt(ip.pop());
				result = performOp(num1, num2, post[i].charAt(0));
				ip.push("" + result);
			} else {
				ip.push(post[i]);
		}
		}
		return Integer.parseInt(ip.pop());
	}

	/*
	 * This takes in an infix expression, and check if the expression has balanced parentheses.
	 */
	public boolean hasBalancedParentheses(String infix) {
		int count =0;
		for(int i = 0; i < infix.length(); i ++) {
			if(infix.charAt(i) == '(') {
				count ++;
			}
			if(infix.charAt(i) == ')') {
				count--;
			}
			if(count < 0) {
				return false;
			}
		}
		return count ==0;
	}

	/*
	 * [OPTIONAL] This is a helper method that might be useful.
	 */
	private int performOp(int a, int b, char op) {

	
		switch (op) {
			case '+' :
			return a + b;
			case '-' :
				return a - b;
			case '*' :
				return a * b;
			case '/' :
				return a / b;
			
		}
		return 0;
	}
	
		
	
}
