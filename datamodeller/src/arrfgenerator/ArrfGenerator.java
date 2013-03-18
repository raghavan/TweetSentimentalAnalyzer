package arrfgenerator;

import java.util.ArrayList;
import java.util.List;

import arff.ArffData;
import arff.ArffUtil;

import model.FeatureSpace;
import model.Tweet;
import util.Constants;
import util.Utility;
import datareader.DBHandler;
import datareader.IDataHandler;

public class ArrfGenerator {

	public static void main(String args[]) {
		IDataHandler dataHandler = new DBHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA);
		FeatureSpace featureSpace = generateFeatureSpace(tweets);
		// featureSpace.print();
		List<ArffData> arffRecords = makeArffRecords(tweets, featureSpace);

		ArffUtil.writeArffDataToFille(arffRecords);
	}

	private static List<ArffData> makeArffRecords(List<Tweet> tweets, FeatureSpace featureSpace) {
		List<ArffData> arffRecords = new ArrayList<ArffData>();
		int distinctWordCount = featureSpace.getDistinctWordCount();
		int distinctEmoticonCount = featureSpace.getDistinctEmoticonCount();
		int totalWordsInCorpus = featureSpace.getTotalWordCount();
		int totalEmoticonsInCorpus = featureSpace.getTotalEmoticonCount();

		int emoticonDemominator = distinctEmoticonCount;
		int wordDenominator = distinctWordCount;
		for (Tweet tweet : tweets) {
			double unigramProbability = 0;
			double emoticonProbability = 0f;
			for (String str : tweet.getCleanedTweet().split(" ")) {
				int numerator = featureSpace.getWordCount(str);
				if (!Utility.getEmoticons().contains(str)) {
					unigramProbability += getProbability(numerator, wordDenominator);
				} else if (Utility.getEmoticons().contains(str)) {
					emoticonProbability += getProbability(numerator, emoticonDemominator);
				}
			}
			ArffData arffData = new ArffData(unigramProbability, emoticonProbability, tweet.getClassLabel());
			System.out.println(arffData);
			arffRecords.add(arffData);
		}
		return arffRecords;
	}

	private static double getProbability(int numerator, int denominator) {
		double ratio = numerator / denominator;
		if (ratio > 0) {
			return Math.log(ratio);
		}
		return 0;
	}
	

	private static FeatureSpace generateFeatureSpace(List<Tweet> tweets) {
		FeatureSpace featureSpace = new FeatureSpace();
		for (Tweet tweet : tweets) {
			String cleanTweetMessage = tweet.getCleanedTweet();
			for (String str : cleanTweetMessage.split(" ")) {
				featureSpace.addWordCount(str);
			}
		}
		return featureSpace;
	}

}
