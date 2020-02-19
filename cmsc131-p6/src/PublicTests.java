import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class PublicTests {


  @Test
  public void countUppercase() {
    assertEquals(5, CheckPasswords.countUppercaseLetters("ABC2928fhgZ5T"));
  }

  @Test
  public void countLowercase() {
    assertEquals(3, CheckPasswords.countLowercaseLetters("ABC2928fhgZ5T"));
    assertEquals(3, CheckPasswords.countLowercaseLetters("ahd"));
    assertEquals(0, CheckPasswords.countLowercaseLetters(""));
    assertEquals(0, CheckPasswords.countLowercaseLetters("1234"));
  }

  @Test
  public void longestConsecutiveIdenticalCharacters() {
    assertEquals(1, CheckPasswords.longestConsecutiveIdenticalCharacters("ABC2928fhgZ5T"));
    assertEquals(4, CheckPasswords.longestConsecutiveIdenticalCharacters("aabaaaahd"));
    assertEquals(0, CheckPasswords.longestConsecutiveIdenticalCharacters(""));
  }

  @Test
  public void similarToWordExactMatch() {
    assertEquals(true, CheckPasswords.similarToWord("apple", "apple123"));
    assertEquals(false, CheckPasswords.similarToWord("apple", "apple12345"));
    assertEquals(true, CheckPasswords.similarToWord("apple", "aappleex"));
    
  }
  

  @Test
  public void similarToWordIgnoringCase() {
    assertEquals(true, CheckPasswords.similarToWord("apple", "Apple123"));
    assertEquals(false, CheckPasswords.similarToWord("apple", "Apple12345"));
    assertEquals(true, CheckPasswords.similarToWord("apple", "aAppleex"));
  }

  @Test
  public void similarToWordAllowingSubstitutions() {
    assertEquals(true, CheckPasswords.similarToWord("apple", "app1e123"));
    assertEquals(false, CheckPasswords.similarToWord("apple", "app1e12345"));
    assertEquals(true, CheckPasswords.similarToWord("apple", "aApp1eex"));
  }

   /** Utility method, simplifies calling checkPassword with empty list of banned words */
  boolean checkPassword(String password) {
    return CheckPasswords.checkPassword(password, Collections.emptyList());
  }
  
  @Test
  public void acceptGoodPassword() {
    assertEquals(true, checkPassword("pHWpBr$EibJ3M"));
  }

  @Test
  public void mustHaveUppercase() {
    assertEquals(false, checkPassword("abcdefghi123"));
    acceptGoodPassword();
  }

  @Test
  public void mustHaveLowercase() {
    assertEquals(false, checkPassword("ABCDEFGHIJ123"));
    acceptGoodPassword();
  }

  @Test
  public void mustHaveNonLetter() {
    assertEquals(false, checkPassword("ABCDEFGHIJabc"));
    acceptGoodPassword();
  }

  @Test
  public void mustBeAtLeast8CharactersLong() {
    assertEquals(false, checkPassword("qkfQ9"));
    acceptGoodPassword();
  }

  @Test
  public void mustBeAtMost32CharactersLong() {
    assertEquals(false, checkPassword("qkfQ123456789012345678901234567890"));
    acceptGoodPassword();
  }

  @Test
  public void cantBeginOrEndWithSpace() {
    assertEquals(false, checkPassword(" pHWpBr$EibJ3M"));
    assertEquals(false, checkPassword("pHWpBr$EibJ3M "));
    acceptGoodPassword();
  }

  @Test
  public void notOnBannedList() {
    assertEquals(false, CheckPasswords.checkPassword("access14A",Arrays.asList("apple","access","butthead")));
    assertEquals(false, CheckPasswords.checkPassword("Ybutthead0",Arrays.asList("apple","access","butthead")));
    assertEquals(true, CheckPasswords.checkPassword("Bbutthead1234",Arrays.asList("apple","access","butthead")));
    acceptGoodPassword();
  }
  
  @Test
  public void generatePasswords() {
    Random r = new Random(42);
    String password = PasswordGenerator.generatePassword(4, r, Arrays.asList("A", "B", "C", "D"));
    assertEquals(4, password.length());
    assertTrue(password.contains("A"));
    assertTrue(password.contains("B"));
    assertTrue(password.contains("C"));
    assertTrue(password.contains("D"));
    String password3 = PasswordGenerator.generatePassword(3, r, Arrays.asList("A", "B", "C", "D"));
    assertEquals(3, password3.length());
    
  }
  @Test
  public void generatePasswordsFromLongerWords() {
    Random r = new Random(17);
    String password = PasswordGenerator.generatePassword(2, r, Arrays.asList("alone", "baked"));
    assertEquals(10, password.length());
    assertTrue(password.equals("alonebaked") || password.equals("bakedalone") );
  }
  
  /** Check that the passwords generated aren't all identical. I know we can't guarantee that the same password won't 
   * come up twice by chance, but the number of possible passwords are such that I'm not too concerned.
   */
  @Test
  public void generatePasswordsAreDifferent() {
    Random r = new Random(99);
    List<String> words = new ArrayList<>();
    for(char c = 'a'; c <= 'z'; c++) {
      words.add(Character.toString(c));
    }
   
    String password1 = PasswordGenerator.generatePassword(10, r, words);
    assertEquals(10, password1.length());
    String password2 = PasswordGenerator.generatePassword(10, r, words);
    assertEquals(10, password2.length());
    assertFalse(password1.equals(password2));
    String password3 = PasswordGenerator.generatePassword(10, r, words);
    assertEquals(10, password3.length());
    assertFalse(password3.equals(password1));
    assertFalse(password3.equals(password2));
    
    
  }

}
