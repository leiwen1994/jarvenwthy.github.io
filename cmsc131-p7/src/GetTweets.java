import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GetTweets {

  /** Twitter access.
   * Uses the twitter4j library for access to tweets. (http://twitter4j.org)
   * 
   * To use this, you need to sign up for a twitter developer account and 
   * create an app. Do this at https://apps.twitter.com.
   * 
   * Don't worry too much about what you put in for the fields twitter asks 
   * about yout app. If you are just collecting a few tweets, no one will look at
   * the data you provided.
   * 
   * Once you have created your app, you need to put your consumer key and consumer 
   * secret in the file src/twitter4j.properties. You won't need to define the 
   * accessToken or accessTokenSecret properties in that file. 
   * 
   * 
   * 
   * @param args
   * @throws TwitterException
   */
  public static void main(String args[]) throws TwitterException {
    TwitterFactory tf = new TwitterFactory();
    Twitter twitter = tf.getInstance();
    Map<List<String>,List<String>> whatComesNext = new HashMap<>();
    for (int p = 1; p < 2; p++) {
      Paging page = new Paging(p, 30);
      ResponseList<Status> userTimeline = twitter.getUserTimeline("realDonaldTrump", page);
      for (Status s : userTimeline) {
        String text = s.getText();
        System.out.printf("%3d %s %s%n", text.length(), s.getCreatedAt(), text);
        MarkovText.learnFromText(whatComesNext, getWordsFromTweet(s));
      }
      System.out.println(userTimeline.size());
    }
    
    Random r = new Random();
    for(int i = 0; i < 10; i++) {
      for(String w : MarkovText.generateText(whatComesNext, r))
        MarkovText.printWord(w);
      MarkovText.printWord("\n");
      System.out.println();
    }
  }
  
  public static ArrayList<String> getWordsFromTweet(Status s) {
    ArrayList<String> result = new ArrayList<>();
    result.add("<START>");
    result.add("<START>");
    for(String w : s.getText().split("\\s"))
      if (!w.startsWith("https:")) result.add(w);
    result.add("<END>");
    return result;
    
  }
}
