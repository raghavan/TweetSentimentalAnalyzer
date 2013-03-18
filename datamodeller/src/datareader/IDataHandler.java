package datareader;

import java.util.List;

import model.Tweet;

public interface IDataHandler {

	public List<Tweet> getAllTweets(String labelledFor);
	
}
