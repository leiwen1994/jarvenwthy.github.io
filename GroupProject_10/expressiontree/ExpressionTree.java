package expressiontree;

import java.util.Arrays;
import java.util.Stack;

public class ExpressionTree {

	private Node root;
	private String postfix;

	public class Node {

		Node left, right;
		String data;

		private Node(String s){
			data = s;
			left = null;
			right = null;
		}

	}

	/* Initialize the tree, assign parameter, and generate the ExpressionTree
	 * based on the postfix string. */
	public ExpressionTree(String postfix) {
		root = null;
		this.postfix = postfix;
		generateTreeFromPostFix();
	}

	public Node getRoot() {
		return root;
	}

	/* Generate the Expression Tree based on the postfix string. For example:
	 * - postfix string: 2 3 4 * +
	 * - ExpressionTree would be:
	 * 			+
	 * 		/		\
	 * 		2		*
	 * 			/		\
	 * 			3		4
	 * - HINT: Use stack */
	private void generateTreeFromPostFix() {
		String [] temp = postfix.split(" ");
		
		Stack<String> stk = new Stack<String>();
		stk.addAll(Arrays.asList(temp));
		root = helper(stk);
	}
	private Node helper(Stack<String> stk) {
		String head = stk.pop();
		Node node = new Node(head);
		if(head.equals("-") ||head.equals("+") || head.equals("*") || head.equals("/")) {
			node.right = helper(stk);
			node.left = helper(stk);
			
		}
		return node;
	}


	/* Evaluate the result by traversing the tree. For example:
	 * - postfix string: 2 3 4 * +
	 * - result: 14 */
	public int evaluate(Node n) {
		int result = 0;
		
	
		if(n.left == null || n.right == null) {
			return Integer.parseInt(n.data);
		}else {
			
		result = performOp(evaluate(n.left),evaluate(n.right),n.data.charAt(0));
		}
		return result;
		}
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
	
		
	


	/* Return the height of the tree from the root to the deepest leaf. */
	public int height(Node n) {
		if(n == null) return 0;
		else {
			int lheight = height(n.left);
			int rheight = height(n.right);
			if(lheight > rheight) {
				return (lheight +1);
			}
			else
				return rheight +1;
		}
	}


	/* Return the sum of all evaluations of a specific level. For example: 
	 * 			+
	 * 		/		\
	 * 		2		*
	 * 			/		\
	 * 			3		4
	 * - sumEvaluationsAtLevel(2) should return 14, sumEvaluationsAtLevel(3)
	 * should return 7. */
	public int sumEvaluationsAtLevel(int level) {
	
		return sumHelper(root,level);
	}

private int sumHelper(Node n, int level) {
	int sum =0;
	if(n == null)return 0;
	if(level ==0) return 0;
	if(level == 1) {
		return evaluate(n);
	}else {
		if(n.left == null || n.right == null) {
			return 0;
		}else {
			int rightSum = sumHelper(n.right, level-1);
			int leftSum = sumHelper(n.left,level -1);
			sum = rightSum + leftSum;
		}
	}
	return sum;
}
}
