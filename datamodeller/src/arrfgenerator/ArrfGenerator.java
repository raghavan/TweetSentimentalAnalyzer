package arrfgenerator;

import java.util.ArrayList;
import java.util.List;

import model.ArffData;
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
		List<ArffData> arffRecords = makeArffRecords(tweets,featureSpace);
		for(ArffData arffData : arffRecords){
			System.out.println(arffData);
		}
	}

	private static List<ArffData> makeArffRecords(List<Tweet> tweets, 
			FeatureSpace featureSpace) {
		List<ArffData> arffRecords = new ArrayList<ArffData>();
		int distinctWordCount = featureSpace.getDistinctWordCount();
		int distinctEmoticonCount = featureSpace.getDistinctEmoticonCount();
		int totalWordsInCorpus = featureSpace.getTotalWordCount();
		int totalEmoticonsInCorpus = featureSpace.getTotalEmoticonCount();
		for(Tweet tweet : tweets){
			float unigramProbability = 0.1f;
			float emoticonProbability = 0.1f;			
			for(String str : tweet.getCleanedTweet().split(" ")){
				if(!Utility.getEmoticons().contains(str)){
					unigramProbability *= ((featureSpace.getWordCount(str)+1)/
						(totalWordsInCorpus+distinctWordCount));
				}else if(Utility.getEmoticons().contains(str)){
					emoticonProbability *= ((featureSpace.getWordCount(str)+1)/
							(totalEmoticonsInCorpus+distinctEmoticonCount));					
				}
			}
			ArffData arffData = new ArffData(unigramProbability, 
					emoticonProbability,tweet.getClassLabel());
			arffRecords.add(arffData);
			//writeArffData(arffData);
		}
		return arffRecords;
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
