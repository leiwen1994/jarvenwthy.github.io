import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

	/** Generate a password from 4 of the words supplied in shortWords.txt */
	public static void main(String [] args) throws Exception {
		ArrayList<String> words = new ArrayList<>();
		Scanner s = new Scanner(new File("shortWords.txt"));
		while (s.hasNextLine()) 
			words.add(s.nextLine());

		System.out.println(generatePassword(4, new Random(), words));
	}


	/**
	 * Generate a password from the list of words provided.
	 * The password should be concatenation of wordCount number of words, 
	 * and no word should be repeated more than once. There are several ways to ensure that no word is used more than once.
	 * The simplest is, as you generate a candidate word to be appended to the password being generated, to 
	 * ensure that it isn't already contained in what has already been generated. 
	 * 
	 * There is no need to check that the password satisfies the UMD password restrictions, or any of the conditions checked by
	 * CheckPassword.
	 * 
	 * All random numbers should be generated using the supplied Random object. This will allow
	 * for deterministic testing.
	 * 
	 */

	static String generatePassword(int wordCount, Random r, List<String> words) {
		String password = new String();
		for (int i=0; i<wordCount; i++) {
			String newWord = words.get(r.nextInt(words.size()));
			if(password.indexOf(newWord)== -1){
				password = password + newWord;
			}
			else {
				i--;
			}
		
		}
		return password;
	}
}


