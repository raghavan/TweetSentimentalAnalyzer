package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Utility {

	public static String cleanDbTweetMessage(String tweetMessage) {
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
		final int NEGATIVE=0,NEUTRAL=1,POSITIVE = 2;
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
				return POSITIVE;// positive is greatest
			} else {
				return NEUTRAL;// neutral is greatest/equal to positive
			}
		} else {
			if (negativeCount > neutralCount) {
				return NEGATIVE;// negative is greatest
			} else {
				return NEUTRAL;// neutral is greatest/equal to negative
			}
		}
	}

	public static String cleanFileTweetMessage(String tweetMessage) {
		//String resultString = tweetMessage.replaceAll(".", " ");
		return tweetMessage;
	}
	
	public static List<String> readFromFile(String fileName) {
		FileInputStream fstream = null;
		List<String> fileRecords = new ArrayList<String>();
		try {
			fstream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.trim();
				fileRecords.add(strLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileRecords;
	}

	public static List<String> getAllStopWords() {
		List<String> stopWords = readFromFile(Constants.STOP_WORDS_FILE);			
		return stopWords;
	}
}
