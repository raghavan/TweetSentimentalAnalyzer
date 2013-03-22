package test;

import java.util.List;

import model.Tweet;

import org.junit.Test;

import util.Constants;
import util.Utility;

import datareader.DBHandler;
import datareader.FileHandler;
import datareader.IDataHandler;

public class TestDataModel {

	@Test
	public void testDataHandler(){
		IDataHandler dataHandler = new DBHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA);
		System.out.println(tweets.size());
	}
	
	@Test
	public void testCleanTweetMessages(){
		IDataHandler dataHandler = new DBHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA);
		for(Tweet tweet : tweets)
			System.out.println(Utility.cleanDbTweetMessage(tweet.getTweetMessage()));
	}
	
	@Test
	public void testFileHandler(){
		IDataHandler dataHandler = new FileHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA_FILE);
		System.out.println(tweets.size());
	}
	
	@Test
	public void testCleanTweetMessagesFromFile(){
		IDataHandler dataHandler = new FileHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA_FILE);
		for(Tweet tweet : tweets){
			System.out.println(Utility.cleanDbTweetMessage(tweet.getTweetMessage()));			
		}
	}
	
	@Test
	public void testFinalConsensesFromFile(){
		IDataHandler dataHandler = new FileHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA_FILE);
		for(Tweet tweet : tweets){			
			System.out.println(tweet.getFinalConsensus());
		}
	}
	
	@Test
	public void testGetAllStopWords() {
		List<String> stopWords = Utility.getAllStopWords();		
		System.out.println(stopWords.contains(">"));
	}
	
	
}
