package arrfgenerator;

import java.util.ArrayList;
import java.util.List;

import model.BagOfWordSpace;
import model.Tweet;
import util.Constants;
import util.Utility;
import arff.ArffData;
import arff.ArffUtil;
import datareader.FileHandler;
import datareader.IDataHandler;

public class ArrfGenerator {

	public static void main(String args[]) {
		IDataHandler dataHandler = new FileHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA_FILE);
		BagOfWordSpace bagOfWordsSpace = makeBagOfWordsSpace(tweets);
		List<ArffData> arffRecords = makeArffRecords(tweets, bagOfWordsSpace);
		List<String> uniqueWords = bagOfWordsSpace.getAllUniqueWords();
		ArffUtil.writeArffDataToFille(uniqueWords, arffRecords);
	}

	private static List<ArffData> makeArffRecords(List<Tweet> tweets, BagOfWordSpace featureSpace) {
		List<ArffData> arffRecords = new ArrayList<ArffData>();
		/*
		 * int distinctWordCount = featureSpace.getDistinctWordCount(); int
		 * distinctEmoticonCount = featureSpace.getDistinctEmoticonCount(); int
		 * totalWordsInCorpus = featureSpace.getTotalWordCount(); int
		 * totalEmoticonsInCorpus = featureSpace.getTotalEmoticonCount();
		 * 
		 * int emoticonDemominator = distinctEmoticonCount; int wordDenominator
		 * = distinctWordCount; for (Tweet tweet : tweets) { double
		 * unigramProbability = 0; double emoticonProbability = 0f; for (String
		 * str : tweet.getCleanedTweet().split(" ")) { int numerator =
		 * featureSpace.getWordCount(str); if
		 * (!Utility.getEmoticons().contains(str)) { unigramProbability +=
		 * getProbability(numerator, wordDenominator); } else if
		 * (Utility.getEmoticons().contains(str)) { emoticonProbability +=
		 * getProbability(numerator, emoticonDemominator); } } ArffData arffData
		 * = new ArffData(unigramProbability,
		 * emoticonProbability,tweet.getPolarityOfTheTweet(),
		 * tweet.getClassLabel()); System.out.println(arffData);
		 * arffRecords.add(arffData); }
		 */
		for (Tweet tweet : tweets) {
			ArffData arffData = new ArffData(tweet.getWordCountMap(), 0, tweet.getPolarityOfTheTweet(),
					tweet.getClassLabel());
			arffRecords.add(arffData);
		}

		return arffRecords;
	}

	/*
	 * private static double getProbability(int numerator, int denominator) {
	 * double ratio = numerator / denominator; if (ratio > 0) { return
	 * Math.log(ratio); } return 0; }
	 */

	private static BagOfWordSpace makeBagOfWordsSpace(List<Tweet> tweets) {
		BagOfWordSpace featureSpace = new BagOfWordSpace();
		List<String> stopWords = Utility.getAllStopWords();
		for (Tweet tweet : tweets) {
			String cleanTweetMessage = tweet.getTweetMessage();
			for (String str : cleanTweetMessage.split(" ")) {
				if (!stopWords.contains(str)) {
					featureSpace.addWordCount(str);
				}
			}
		}
		return featureSpace;
	}

}
