package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Utility;

public class Tweet {

	private String tweetMessage;
	private String labelledFor;
	private String finalConsensus;
	private boolean isFromDb;

	public Tweet(String tweet, String labelledFor, String finalConsensus, boolean isFromDb) {
		super();
		this.tweetMessage = tweet;
		this.labelledFor = labelledFor;
		this.finalConsensus = finalConsensus;
		this.isFromDb = isFromDb;
	}

	public String getTweetMessage() {
		return tweetMessage;
	}

	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}

	public String getLabelledFor() {
		return labelledFor;
	}
	public void setLabelledFor(String labelledFor) {
		this.labelledFor = labelledFor;
	}
	
	public void setFinalConsensus(String finalConsensus) {
		this.finalConsensus = finalConsensus;
	}

	public boolean isFromDb() {
		return isFromDb;
	}

	public void setFromDb(boolean isFromDb) {
		this.isFromDb = isFromDb;
	}

	public String getCleanedTweet() {
		if (isFromDb)
			return Utility.cleanDbTweetMessage(tweetMessage);
		else
			return Utility.cleanFileTweetMessage(tweetMessage);
	}

	public int getClassLabel() {

		if (finalConsensus.equals("-1"))
			return ClassLabel.NEGATIVEONE.value;
		else if (finalConsensus.equals("1"))
			return ClassLabel.ONE.value;
		else if (finalConsensus.equals("0"))
			return ClassLabel.ZERO.value;

		return ClassLabel.UNKNOWN.value;
	}

	public int getPolarityOfTheTweet() {
		return Utility.getPolarityOfTheString(getCleanedTweet());
	}

	public Map<String, Integer> getWordCountMap() {
		Map<String, Integer> wordCount = new HashMap<String, Integer>();
		List<String> stopWords = Utility.getAllStopWords();
		for (String word : tweetMessage.split(" ")) {
			if (!stopWords.contains(word)) {
				int count = 0;
				if (wordCount.containsKey(word)) {
					count = wordCount.get(word);
				}
				count += 1;
				wordCount.put(word, count);
			}
		}
		return wordCount;
	}

	public int getFinalConsensusAsInteger() {
		int consensus = 100;
		try {
			consensus = Integer.parseInt(finalConsensus);
			if(consensus == 1 && labelledFor.equalsIgnoreCase("romney")){
				consensus = -1;				
			}else if(consensus == -1 && labelledFor.equalsIgnoreCase("romney")){
				consensus = 1;				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return consensus;
	}

}
