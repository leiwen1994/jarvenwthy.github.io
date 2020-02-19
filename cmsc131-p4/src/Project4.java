public class Project4 {

	/**
	 * Compute the largest power of two less than n.
	 * 
	 * @param n
	 *            Assume n is two or greater.
	 * @return The largest power of 2 less than n.
	 */
	static int largestPowerOfTwoLessThan(int n) {
		int Num=0;
		for(int i=0; i<n; i++) {
			if(Math.pow(2,Num)<n) {
				Num+=1;
			}
		}
		return (int) Math.pow(2, Num-1);
	}

	/**
	 * The Collatz conjecture (https://en.wikipedia.org/wiki/Collatz_conjecture)
	 * concerns the following process for generating a sequence of integers.
	 * Start the sequence any positive integer n. Then each term is obtained
	 * from the previous term as follows: if the previous term is even, the next
	 * term is one half the previous term. Otherwise, the next term is 3 times
	 * the previous term plus 1. The conjecture is that no matter what value of
	 * n, the sequence will always reach 1.
	 * 
	 * For example, starting with 3 we get the sequence [3, 10, 5, 16, 8, 4, 2,
	 * 1].
	 * 
	 * The Collatz stopping time for a number n is the number of steps needed
	 * before you reach 1. For example, the Collatz stopping time for 1 is 0,
	 * for 2 it is 1, and for 3 it is 7.
	 * 
	 * You can assume that all computation can be done using int arithmetic.
	 * 
	 * @param n
	 *            The number to compute the stopping time. You can assume n is
	 *            positive and that all of the values in the Collatz sequence
	 *            for n can be representing using an int.
	 * @return the stopping time for n
	 */
	static int collatzStoppingTime(int n) {
		int count = 0;
		while(n!=1) {
			if(n % 2 ==0) {
				n= n/2;
				count++;
			}else if(n % 2 !=0) {
				n=3*n+1;
				count++;
			}
			while(n ==1) {
				break;
			}
		}
		return count;
	}




	/**
	 * Determine if n is a perfect number
	 * (https://en.wikipedia.org/wiki/Perfect_number). You can adapt your code
	 * from countFactors in project 3.
	 * 
	 */
	static boolean isPerfect(int n) {
		if(n < 0) {
			return false;
		}
		int PerfectNum = 0;
		int Div = 1;
		while(Div < n) {
			if(n % Div ==0){
				PerfectNum = PerfectNum + Div;
			}
			Div++;
		}
		return PerfectNum == n;
	}


	/**
	 * Determine if n is prime number
	 * 
	 * @param n
	 * @return
	 */
	static boolean isPrime(int n) {
		if(n< 1) {
			return false;
		}
		int PrimeNum =0;
		int Div = 2;
		while(Div <= n) {
			if(n % Div ==0) {
				PrimeNum = PrimeNum + Div;
			}
			Div ++;
		}
		return PrimeNum == n;
	}
	

	/**
	 * Compute the number of primes less than n
	 * 
	 * @param n
	 * @return
	 */
	static int primesSmallerThan(int n) {

		/**int primes = 0;
		for (int Div =2 ; Div< n; Div++) {
			for(int count =1; count < Div; count++) {
				if(Div % count != 0 && Div< n)
				}
					
				}
			}
			primes ++;
		}
		return primes;
	}*/
		int primes = 0; 
		for (int i=1;i<n;i++) {
			if(isPrime(i)) {
				primes++;
			}
		}
		return primes;
	}




	/**
	 * Compute the check digit for a number using the Luhn algorithm.
	 * https://en.wikipedia.org/wiki/Luhn_algorithm
	 * 
	 * WARNING: You can find on-line implementations of the Luhn algorithm.
	 * Please write your own. If you need to consult one to understand the
	 * algorithm, you must include a link to the web or other source you used.
	 * 
	 * This is the algorithm used to to determine the last digit of credit cards
	 * and many other identification numbers. It is intended to protect against
	 * accidental errors, not to provide security.
	 * 
	 * For example, for 7992739871, the check digit is 3.
	 * 
	 * We want to be able to compute check digits for 16 digit credit card
	 * numbers, which can't be represented using an int, so use long rather than
	 * int for all integer variables in this method.
	 * 
	 * Some of the variables could be int variables, but it makes the code
	 * slightly simpler to just make them all long.
	 * 
	 * 
	 * Other than that change, the only thing you need to know is that you need
	 * to put L as a suffix after a integer constant to tell the compiler that
	 * yes, you intend this to be a long constant, not an int constant. For
	 * example, in the test case for this method, we call it with
	 * assertEquals(3, Project4.lunhCheckDigit(7992739871L));
	 * 
	 */
	static long lunhCheckDigit(long n) {
		
		int numDigits=0;
		for(int i=0; Math.pow(10,i)<=n;i++) {
			numDigits++;
		}
		long sumDigits[]= new long[numDigits];
		for(int i=numDigits-1; i>=0; i--) {
			long digit=(long) (n/Math.pow(10, i));
			sumDigits[i]=digit;
			n-= (long) (digit*Math.pow(10, i));
		}
		for(int i=0; i<numDigits;i+=2) {
			sumDigits[i]*=2;
			
			if (sumDigits[i]%9==0 && sumDigits[i]!=0) {
				sumDigits[i]=9;
			}
			else {
				sumDigits[i]=sumDigits[i]%9;
			}
			
		}
		long output=0;
		for(int i=0; i<sumDigits.length;i++) {
			output+=sumDigits[i];
			
		}
		return (long) (10-output%10);
	}
/*
 * 
 */
	/**
	 * You are given two arrays. The first is a collection of grades. The second
	 * is the array of grade cutoffs. For example if gradeCutOffs is { 90, 82,
	 * 68 }, then grades of 90 or higher get an A, grades of 82-89 get a B,
	 * grades of 68-82 get a C and grades below 68 get a D.
	 * 
	 * Return an array that give the number of grades that get each letter
	 * grade. For example, with the grade cuttoffs as above, and the grades {95,
	 * 85, 69, 52, 90, 75, 62}, there are 2 A's, 1 B, 2 C's and 2 D's
	 * (represented as an int array {2, 1, 2, 2}).
	 * 
	 * Note that the number of possible grades is one more than than the length
	 * of the gradeCutOffs array. Don't assume that grades match typical letter
	 * grades. There might be just 3 letter grades, or 20. You can assume that
	 * the values in gradeCutOffs are strictly decreasing (i.e., each value is
	 * less than the value before it).
	 * 
	 */
	static int[] gradeDistribution(int[] grades, int[] gradeCutOffs) {
		int[] letterGrades=new int[gradeCutOffs.length+1];
		for(int i=0; i<grades.length;i++) {
			boolean run= true;
			for(int j=0; j<gradeCutOffs.length && run;j++) {
				if (grades[i]>=gradeCutOffs[j]) {
					run=false;
					letterGrades[j]++;
				}
			}
			if (run) {
				letterGrades[gradeCutOffs.length]++;
			}
			/*if (grades[i]>=gradeCutOffs[0]) {
				letterGrades[0]++;
			}
			else if(grades[i]>=gradeCutOffs[1]) {
				letterGrades[1]++;
			}
			else if(grades[i]>=gradeCutOffs[2]) {
				letterGrades[2]++;
			}
			else {
				letterGrades[3]++;
			}*/
		}
		return letterGrades;
	}
}
