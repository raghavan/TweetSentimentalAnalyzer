package datareader;

import java.util.ArrayList;
import java.util.List;

import model.Tweet;
import util.Constants;
import util.Utility;

public class FileHandler implements IDataHandler {

	@Override
	public List<Tweet> getAllTweets(String fileName) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		List<String> stopWords = Utility.getAllStopWords();
		for (String str : Utility.readFromFile(fileName)) {
			if (str != null) {
				String[] messageAndConsenses = str.split("	");
				if (messageAndConsenses != null && messageAndConsenses.length == 2) {
					StringBuffer newStr = new StringBuffer();
					String[] words = messageAndConsenses[0].split(" ");
					for (String word : words) {
						word = word.trim();
						word = word.replace("\'", "");
						word = word.replace("{", "");
						word = word.replace("}", "");
						word = word.replace("%", "");
						word = word.replace("\"", "");
						word = word.replace(",", "");
						word = word.replace("class", "class_word");
						word = word.toLowerCase();
						if (!stopWords.contains(word)) {							
							newStr.append(word);
							newStr.append(" ");
						}
					}
					String consensus = messageAndConsenses[1].trim();
					Tweet tweet = new Tweet(newStr.toString(), Constants.OBAMA, consensus, false);
					tweets.add(tweet);
				}
			}
		}
		return tweets;
	}

}
