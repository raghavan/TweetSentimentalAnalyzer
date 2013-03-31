package arrfgenerator;

import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Utility;

import model.Tweet;

public class GooglePredictor {

	public void makePredictionFile(List<Tweet> tweets) {
		List<String> predictionFile = new ArrayList<String>();
		for(Tweet tweet : tweets){
			if(tweet.getFinalConsensusAsInteger() >= -1 && tweet.getFinalConsensusAsInteger() <=2){
				String str = Constants.QUOTE+tweet.getFinalConsensusAsInteger()+Constants.QUOTE+Constants.COMMA+
						Constants.QUOTE+tweet.getTweetMessage()+Constants.QUOTE;
				predictionFile.add(str);
			}
		}
		Utility.writeDataToFile(Constants.GOOGLE_PREDICTION_FILE, predictionFile);
	}

}
