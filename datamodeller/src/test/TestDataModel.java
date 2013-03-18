package test;

import java.util.List;

import model.Tweet;

import org.junit.Test;

import util.Constants;
import util.Utility;

import datareader.DBHandler;
import datareader.IDataHandler;

public class TestDataModel {

	@Test
	public void checkDataHandler(){
		IDataHandler dataHandler = new DBHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA);
		System.out.println(tweets.size());
	}
	
	@Test
	public void cleanTweetMessages(){
		IDataHandler dataHandler = new DBHandler();
		List<Tweet> tweets = dataHandler.getAllTweets(Constants.OBAMA);
		for(Tweet tweet : tweets)
			System.out.println(Utility.cleanTweetMessage(tweet.getTweetMessage()));
	}
}
