package words;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
//Student 1: Jiawen Lei
//Student 2: Sarah Bang


public class Words {
	
	ArrayList<String> wordList;	//All the words
	Iterator<String> iter;		// iterator for the wordlist
	int numberOfWords;	// number of words in the file
	
	String[] words;		// this array holds your words
	
	
	public Words(){
		wordList = new ArrayList<>();
		//iter = wordList.iterator();
	}
	/**
	 * This method loads the words from a given file
	 * @param fileName	input file name
	 */
	private void loadWords(String fileName){	
		wordList.clear();
		numberOfWords = 0;
		// This will reference one line at a time
        String line = null;
        int count = 0;
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
            	wordList.add(line.toLowerCase());
                count++;
            }   

            // Always close files.
            bufferedReader.close(); 
            numberOfWords = count;
            iter = wordList.iterator();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'"); 
        }
	}
	
	public String getWord(int index){
		if(index < 0 || index >= numberOfWords){
			return null;
		}
		return words[index];
	}

	/**
	 * This method adds all the words to an array called words
	 * 
	 * variable numberOfWords is already declared and has value and contains number of words
	 * 
	 * @param fileName: input file name
	 */	
	public void addWordsToArray(String fileName){
		loadWords("data/" + fileName);	//DO NOT CHANGE
		
		//variable numberOfWords has the number of words 
		
		
		//TODO 
			// String[] words has been declared. Now instantiate the array to the words
			// array size is equal to the number of words
		words = new String[numberOfWords];	
		
				
		/**
		 * Calling iter.next() will return the next word in the file.
		 * For examples
		 * String w = iter.next();
		 * iter.next() always gives a next word 
		*/
		
		//TO DO
			//Add all word into the array  words
		for(int i = 0; i< numberOfWords;i++) {
			words[i] = iter.next();
		}
		
	}
	
	/**
	 * 
	 * @param word: input
	 * @return true if the given word exits in the words array. False otherwise
	 */
	public boolean contains(String word){
		for(String s : words) {
			if(s.equals(word)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param sentence: input sentence
	 * @return true if every word in the sentence exists in your words array. 
	 * False otherwise.
	 */
	public boolean containsSentence(String sentence){
		String noSpace[] = sentence.split(" ");
		int count = 0;
		for(String s1 : words) {
			for(int i = 0; i < noSpace.length; i++) {
				if((noSpace[i].equals(s1))) {
				count ++;
			}
				
		}
		}
		if(count == noSpace.length) {
			return true;
		}
		return false;
	}
	
	/**
	 * 	reverse a sentence
	 * 
	 * @param sentence: input sentence
	 * @return reversed sentence.
	 * For example: 
	 * 	input: "i love you"
	 *  return: "you love i" (hint: trim leading and trailing spaces")
	 */
	public String reverseSentence(String sentence){
		sentence.trim();
		String reSentence[] = sentence.split(" ");
		String newSentence = "";
		for(int i =reSentence.length-1; i >= 0; i--) {
			newSentence += reSentence[i] + " ";
			
		}
		newSentence = newSentence.trim();
		return newSentence;
	}
	
	
	/**
	 * 
	 * @param word: input word
	 * @return the number of occurrences of the word. If the word does not exist, return 0 
	 */
	public int count(String word){
		int count = 0;
		for(String s3 : words) {
			if (s3.equals(word)) {
				count ++;
			}
		}
		
		return count;
	}
	
	
	/**
	 * An anagram is word or phrase formed by rearranging the letters of a 
	 * different word or phrase, typically using all the original letters 
	 * exactly once.
	 *  For example, 
	 * 
	Tar = Rat
	Arc = Car
	Elbow = Below
	State = Taste
	Cider = Cried
	Dusty = Study
	Night = Thing
	Inch = Chin
	Brag = Grab
	Cat = Act
	Bored = Robed
	Save = Vase
	Angel = Glean
	Stressed = Desserts
 
	/**
	 * @param word1: input word 1
	 * @param word2: input word 2
	 * @return true if word1 is the anagram of word2. False otherwise
	 */
	public boolean anagram(String word11, String word22){
		if(word11.length() != word22.length()) {
			return false;
		}
		char[] c1 = word11.toCharArray();
		char[] c2 =word22.toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);
		 return Arrays.equals(c1, c2);
	}
	
	
	
	
	/**
	 
	 * 
	 * @param word: input word
	 * @param fileName: input file name
	 * 
	 * Given a file that includes a list words, PRINT all words that are the anagrams 
	 * of a given word.  
	 * File name of the file is given as the second argument 
	 */
	public void findAnagram(String word, String fileName){
		addWordsToArray(fileName);
		String x = "";
		for(int i=0;i<numberOfWords;i++) {
			if(anagram(word,words[i])) {
				x=words[i];
				System.out.println(x);
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param letters: input
	 * @param fileName: file name of the word list
	 * 
	 * PRINT all words from the file if we make the word using the characters 
	 * of the first argument letter.
	 * 
	 * For example: you can make rain, gain, virgin using the letters of virginia. 
	  
	 */
	public void findWords(String letters, String fileName){
			
	}
}
