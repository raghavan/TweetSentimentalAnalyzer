package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Utility {

	public static String cleanTweetMessage(String tweetMessage) {
		String resultString = tweetMessage.replaceAll("[^\\p{L}\\p{N}]", " ");
		return resultString;
	}

	public static Set<String> getEmoticons() {
		Set<String> emoticons = new HashSet<String>();
		emoticons.add(":)");
		emoticons.add(":P");
		emoticons.add(";)");
		emoticons.add(":(");
		return emoticons;
	}

	static Map<String, String> propContents = null;
	private static Map<String, String> readpropFile(String fileName) {
		if (propContents == null) {
			propContents = new HashMap<String,String>();
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(fileName));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				propContents.put(key, props.getProperty(key));
			}
		}
		return propContents;
	}

	public static int getPolarityOfTheString(String cleanedTweet) {
		int positiveCount = 0, negativeCount = 0, neutralCount = 0;
		Map<String, String> propContents = readpropFile(Constants.POLARITY_WORD_FILE);
		for (String str : cleanedTweet.split(" ")) {
			String polarity = propContents.get(str);
			if (polarity != null) {
				if (polarity.equalsIgnoreCase("positive"))
					positiveCount += 1;
				else if (polarity.equalsIgnoreCase("negative"))
					negativeCount += 1;
				else if (polarity.equalsIgnoreCase("neutral"))
					neutralCount += 1;
			}
		}

		if (positiveCount == negativeCount)
			return 0;

		if (positiveCount > negativeCount) {
			if (positiveCount > neutralCount) {
				return 1;// positive is greatest
			} else {
				return 0;// neutral is greatest/equal to positive
			}
		} else {
			if (negativeCount > neutralCount) {
				return -1;// negative is greatest
			} else {
				return 0;// neutral is greatest/equal to negative
			}
		}
	}
}
