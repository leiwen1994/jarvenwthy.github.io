import java.util.List;

import javax.xml.stream.events.Characters;

/**
 * Check rules on UMD passwords, as described at
 * https://identity.umd.edu/password/changepassword
 * 
 * <ul>
 * <li>A password must be at least 8 and no more than 32 characters in length.
 * <li>A password must contain at least one character from each of the following
 * sets:
 * <ul>
 * <li>Uppercase alphabet (A-Z)
 * <li>Lowercase alphabet (a-z)
 * <li>Number (0-9) and special characters (such as # @ $ & among others)
 * </ul>
 * <li>A password may not begin or end with the space character.
 * <li>A password may not contain more than two consecutive identical
 * characters.
 * <li>A password may not be (or be a variation of ) a dictionary word in
 * English or many other languages. This includes making simple substitutions of
 * digits or punctuation that resemble alphabetic characters (such as replacing
 * the letter S in a common word with the $...
 * <li>Passwords should not contain: carriage return, linefeed, /, \, or a
 * trailing * symbol).
 * </ul>
 *
 */
public class CheckPasswords {


	/**
	 * Count the number of uppercase letters in password; can assume only ASCII
	 * characters
	 */
	static int countUppercaseLetters(String password) {
		int Uppercase = 0;
		for(int i=0;i<password.length();i++) {
			if(Character.isUpperCase(password.charAt(i))) {
				Uppercase ++;
			}
		}
		return Uppercase;
	}

	/**
	 * Count the number of lowercase letters in password; can assume only ASCII
	 * characters
	 */
	static int countLowercaseLetters(String password) {
		int Lowercase = 0;
		for(int i=0;i<password.length();i++) {
			if(Character.isLowerCase(password.charAt(i))) {
				Lowercase ++;
			}
		}
		return Lowercase;
	}


	/**
	 * Count the longest sequences of consecutive identical characters; can assume
	 * only ASCII characters
	 */

	static int longestConsecutiveIdenticalCharacters(String password) {
		if(password.length() == 0){
			return 0; 
		}

		int count = 0;
		int max = 0;
		char character = password.charAt(0);
		for(int i = 0;i < password.length(); i++){
			if(character == password.charAt(i)){
				count++;
			}
			else{
				character = password.charAt(i);
				if(max < count){
					max = count;
				}
				count = 1;
			}
		}

		if(max < count){
			max = count;
		}
		return max;
	}




	/**
	 * Check to see if a password is to similar to a dictionary word. It is too
	 * similar if the dictionary word is contained in the password when ignoring
	 * case and treating '1' and 'l' as identical , 'o' and '0' as identical, and
	 * 's' and '$' as identical, and the length of the password is at least 5
	 * characters longer than the word.
	 */
	static boolean similarToWord(String word, String password) {
		//System.out.println("Checking word " + word);
		if(password.toLowerCase().replace('0', 'o').replace('1','l').replace('$','s').indexOf(word) != -1){
			//System.out.println(word);
			if(password.length() < word.length() +5) {
				return true;
			}
			if(password.contains(word)) {
				return false;
			}
		}
		return false;
	}

	/** Check to see if password is an acceptable password by UMD standards */
	static boolean checkPassword(String password, List<String> dictionary) {
		int len = password.length();
		boolean num=false;

		for(int i=0;i<dictionary.size();i++) {
			if(similarToWord(dictionary.get(i),password)) {
				return false;
			}
		}

		if(len >=8 && len < 32) {	
			int value;
			for (int i=0; i< len;i++) {
				value= (int) password.charAt(i);
				if (value == 13 || value == 10 || value == 47 || value ==92) {
					return 	false;
				}
				if(value >=33 && value <= 64 ) {
					num=true;
				}
				if(password.charAt(password.length()-1)=='*'){
					return false;
				}
				if(Character.isWhitespace(password.charAt(0)) || Character.isWhitespace(password.charAt(len-1))) {
					return false;
				}

			}
			if (num && countUppercaseLetters(password) >0 && countLowercaseLetters(password) >0) {
				return true;
			}

		}





		return false;
	}
}


