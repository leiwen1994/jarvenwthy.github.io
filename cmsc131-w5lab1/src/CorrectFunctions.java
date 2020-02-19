
class CorrectFunctions {
	/** 
	 * selection sort an array
	 *  
	 * @param array
	 * @return
	 */
	static void selectionSort (int[] array) {
		int i = 0;
		while (i < array.length-1) {	
		    int idxMin = i;
			int j = i+1;
			while (j < array.length) {
				if(array[idxMin] > array[j]) {
					idxMin = j;
				}
				j++;
			}
			
			if(i != idxMin) {
				swap(array, i, idxMin);
			}

			i++;
		}
		
		//printArray(array);
	}	
	
	/** 
	 * compute partial sum array 
	 * each element of the new array will have the sum from the 1st element to i-th element
	 * 
	 * @param array
	 * @return
	 */
	static int[] partialSum (int[] array) {
		int[] pSumArray = new int[array.length];
		
		int sum = 0;
		for(int idx = 0; idx < array.length ; idx++) {
			pSumArray[idx] = sum + array[idx]; 
			sum += array[idx];
		}
		
		//printArray(pSumArray);
		return pSumArray;
	}
	
	
	/**
	 * swap two array elements at position i and j
	 * 
	 * @param array
	 * @param i
	 * @param j
	 */
	static void swap (int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	
	/**
	 * Use this to explain System.out.println method
	 * 
	 * @param array
	 */
	static void printArray (int[] array) {
		System.out.print("[");
		
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(array[i]);
		}
		
		System.out.println("]");
	}
	
}
