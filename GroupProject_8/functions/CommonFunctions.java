package functions;

import java.util.Stack;

/*
 * You must implement the following methods using Java's Stack object to 
 * replace the iteration/recursion in the original functions with a series
 * of stack operations, including push, pop, peek and isEmpty. Use no other
 * special library functions or classes; in other words, your code should use
 * standard arithmetic operators and in the case of the reverse function, only
 * push and pop and the default constructor for whichever Java collection
 * class you choose to represent lists.
 */

public class CommonFunctions {
	/**
	 * computes the factorial of n
	 * @param n-integer value greater or equal to 0
	 * @return n!
	 */
	public static int factorial( int n ) {
		Stack<Integer> result = new Stack<Integer>();
		int sum=1;           
		if(n <=1) {
			return 1;
		}
		while(n > 1) {
		result.push(n);
			n--;
	}
		while(!result.isEmpty()) {
			sum *= result.pop();
		}
		return sum;
			
	}
	/**
	 * computes the nth term of the fibonacci sequence
	 * @param n -nth term to find
	 * @return -the nth term
	 */
	public static int fibonacci( int n ) {
		Stack<Integer> result = new Stack<Integer>();
		int a =0;
		int b = 1;
		int sum = 0;
		int i =1;
		if(n == 0) {
			return 0;
		}else if(n == 1 || n ==2) {
			return 1;
		}else {
			result.push(a);
			result.push(b);
			while(i < n) {
			a= result.pop();
			b = result.pop();
			sum = a + b;
			i++;
			result.push(a);
			result.push(sum);
			}
			
		}
		return result.pop();
	}
	/**
	 * find the min value using the comparable interface of the elements found in theStack
	 * @param theStack-the stack of objects to search
	 * @return the min value
	 */
	public static <T extends Comparable< T> > T min( Stack< T > theStack ) {
		T min = null;
		if(theStack == null || theStack.isEmpty()) {
			return null;
		}else{
			T a = theStack.pop();
			if(theStack.isEmpty()) {
				min = a;
			}
			while(!theStack.isEmpty()) {
				T b = theStack.pop();
				if(a.compareTo(b) >= 0) {
					min = b;
					theStack.push(b);
					a = theStack.pop();
				}else {
					min = a;
				}
			}	
		}
		//System.out.println(min);
		return min;
		
	}
	/**
	 * take a string and makes sure the parenthesis are correctly formatted
	 * @param text the text to check
	 * @return if correctly fomrated to not
	 */
	public static boolean isBalanced( String text ) {
		Stack<String> result = new Stack<String>();
		if(text == null || !result.isEmpty()){
			return false;
		}
		for(int i = 0; i < text.length(); i ++) {
			if(text.charAt(i) == '(') {
				result.push("1");
			}
			if(text.charAt(i) == ')' && !result.isEmpty()) {
				if(result.pop()!="1") {
					return false;
				}
			}
			else if(text.charAt(i) == ')' && result.isEmpty()) {
				return false;
			}
		}
			return result.isEmpty() ;
	}

	/**
	 * 
	 * checks a string for palindrome-ness
	 * @param str string to check
	 * @return returns true if str is a palnindrome
	 */
	public static boolean isPalindrome( String str ) {
		Stack<String> result = new Stack<String>();
		String s = "";
		 
		if(str == null) {
			return false;
		}
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != ' ') {
				String c = " " +str.charAt(i);
				result.push(c);
				s += c;
			}
			
		}
		String s2 = "";
		while(!result.isEmpty()) {
			s2 += result.pop();
		}
		return s.equals(s2);
	}
}
