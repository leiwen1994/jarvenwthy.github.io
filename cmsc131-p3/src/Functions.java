
class Functions {

	/**
	 * Count number of unique values in parameters. For example,
	 * countUnique(7,7) returns 1 and countUnique(3,5) returns 2.
	 */
	static int countUnique(int a, int b) {
		if (a == b)
		{
			return 1;
		}
		else {
			return 2;
		}
	}


	/** Count number of unique values in parameters */
	static int countUnique(int a, int b, int c) {
		if (a==b && b==c)
		{
			return 1;
		}

		else if (a==b || b == c || a==c)
		{
			return 2;
		}
		else
		{
			return 3;
		}
	}


	/** Return the largest value of the parameters */
	static int max(int a, int b, int c) {
		if (a > b && a > c)
		{
			return a;
		}
		else if (b>a && b>c)
		{
			return b;
		}
		else
		{
			return c;
		}
	}

	/**
	 * Fill in the blanks so that this code counts the number of factors of the
	 * variable n that are less than n. For example, 6 has three factors (1, 2
	 * and 3) and 12 has five (1,2,3,4 and 6).
	 * 
	 * Remember that % is the reminder operator. For example, 7 % 4 == 3. You
	 * may find that operator useful.
	 * 
	 * @param n
	 *            - number who's factors should be counted
	 * @return the number of factors of n
	 */
	static int countFactors(int n) {
		int factors = 0;
		for(int i=1; i < n; i++) {
			if (n % i ==0)
			{
				factors++;
				//factors+=1;
				//factors = factors+1;
			}
		}
		return factors;
	}

	
	/**
	 * We are given an array of integers, and we want to count the number of
	 * adjacent pairs where the first number is less than the second element.
	 * For example, for the array [1,1,2,4,2,3] the count should be 3:
	 * <ul>
	 * <li>the pair 1,2 starting at index 1
	 * <li>the pair 2,4 starting at index 2
	 * <li>the pair 2,4 starting at index 4
	 * </ul>
	 * 
	 * Note that for an array a, the value a.length is the number of elements in
	 * the array (e.g., for the example given above, a.length is 6).
	 * 
	 * @param a
	 *            array to analyze
	 * @return number of adjacent increasing pairs
	 */
	static int countAdjacentIncreasing(int[] a) {
		int count = 0;
		for (int i=0; i<a.length-1; i++) {
			if (a[i] < a[i+1]){
				count+=1;
			}
	}
		return count;
	}
}

	
