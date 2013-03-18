package util;

import java.util.HashSet;
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
}
