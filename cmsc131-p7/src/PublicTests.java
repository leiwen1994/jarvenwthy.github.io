import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.junit.Test;

public class PublicTests {

  /** Should print
   * {[<START>, <START>]=[I], [<START>, I]=[am], [I, am]=[not, a], [am, not]=[a], [not, a]=[number.], [a, number.]=[I], [number., I]=[am], [am, a]=[free], [a, free]=[man!], [free, man!]=[<END>]}
   */
  @Test
  public void testTrainOnNotANumber() {
    List<String> prisoner =
        Arrays.asList("<START> <START> I am not a number. I am a free man! <END>".split(" "));
    Map<List<String>, List<String>> whatComesNext = new LinkedHashMap<>();
    MarkovText.learnFromText(whatComesNext, prisoner);
    System.out.println(whatComesNext);
    assertEquals(10, whatComesNext.size());
    assertEquals(Arrays.asList("not", "a"), 
        whatComesNext.get(Arrays.asList("I", "am")));
    assertEquals(Arrays.asList("free"), 
        whatComesNext.get(Arrays.asList("am", "a")));
    assertEquals(Arrays.asList("<END>"), 
        whatComesNext.get(Arrays.asList("free","man!")));
  }
  
  /**
   * Should print 
   * {[<START>, <START>]=[a], [<START>, a]=[b], [a, b]=[a, c], [b, a]=[b], [b, c]=[<END>]}
   */
  @Test
  public void testSimpleGeneration() {
    List<String> text =
        Arrays.asList("<START> <START> a b a b c <END>".split(" "));
    Map<List<String>, List<String>> whatComesNext = new LinkedHashMap<>();
    MarkovText.learnFromText(whatComesNext, text);
    System.out.println(whatComesNext);
    assertEquals(5, whatComesNext.size());
    assertEquals(Arrays.asList("a", "c"), whatComesNext.get(Arrays.asList("a", "b")));
    Pattern pattern = Pattern.compile("(ab)+c");
    int [] histogram = new int[10];
    for(int i = 0; i < 128; i++) {
      List<String> result = MarkovText.generateText(whatComesNext, new Random(197*i));
      result = MarkovText.excludeStartAndEndMarkers(result);
      assertEquals(1, result.size() % 2);
      int repeats = (result.size()-1)/2;
      if (repeats < histogram.length)
        histogram[repeats] = histogram[repeats]+1;
      StringBuffer together = new StringBuffer();
      for(String w : result) 
        together.append(w);
      assertTrue("Doesn't match " + together, pattern.matcher(together).matches());
    }
    assertEquals(0, histogram[0]);
    assertTrue("expected distribution of results", 32 <= histogram[1] && histogram[1] <= 128 );
    assertTrue("expected distribution of results", 16 <= histogram[2] && histogram[2] <= 64 );
    assertTrue("expected distribution of results", 8 <= histogram[3] && histogram[3] <= 32 );
  }
  
  /**
   * Should print 
   * {[<START>, <START>]=[a], [<START>, a]=[a], [a, a]=[b, b], [a, b]=[a, a], [b, a]=[a, <END>]}
   */
  @Test
  public void testSimpleGeneration2() {
    List<String> text =
        Arrays.asList("<START> <START> a a b a a b a <END>".split(" "));
    Map<List<String>, List<String>> whatComesNext = new LinkedHashMap<>();
    MarkovText.learnFromText(whatComesNext, text);
    System.out.println(whatComesNext);
    assertEquals(5, whatComesNext.size());
    assertEquals(Arrays.asList("b", "b"), whatComesNext.get(Arrays.asList("a", "a")));
    Pattern pattern = Pattern.compile("(aab)+a");
    
    int [] histogram = new int[10];
    for(int i = 0; i < 128; i++) {
      
      List<String> result = MarkovText.generateText(whatComesNext, new Random(191*i));
      assertEquals("<START>", result.get(0));
      assertEquals("<START>", result.get(1));
      assertEquals("<END>", result.get(result.size()-1));
      List<String> resultDup =MarkovText.generateText(whatComesNext, new Random(191*i));
      // Should give the same answer when called with same random seed
      assertEquals(result, resultDup);
      result = MarkovText.excludeStartAndEndMarkers(result);
      
      assertEquals(1, result.size() % 3);
      int repeats = (result.size()-1)/3;
      if (repeats < histogram.length)
        histogram[repeats] = histogram[repeats]+1;
      StringBuffer together = new StringBuffer();
      for(String w : result) 
        together.append(w);
      assertTrue(pattern.matcher(together).matches());
    }
    assertEquals(0, histogram[0]);
    assertTrue("expected distribution of results", 32 <= histogram[1] && histogram[1] <= 128 );
    assertTrue("expected distribution of results", 16 <= histogram[2] && histogram[2] <= 64 );
    assertTrue("expected distribution of results", 8 <= histogram[3] && histogram[3] <= 32 );
  }

  
  
  @Test
  public void testFiveOrangePips() throws IOException {
    Map<List<String>, List<String>> whatComesNext = new LinkedHashMap<>();
    MarkovText.learnFromFile(whatComesNext, new File("FiveOrangePips.txt"));
    assertEquals(5956, whatComesNext.size());
    assertEquals(Arrays.asList("room,", "fact", "bone,"),
        whatComesNext.get(Arrays.asList("a", "single")));
    assertEquals(Arrays.asList("you", "the", "the", "she"),
        whatComesNext.get(Arrays.asList("doubt", "that")));
  }
  
}
