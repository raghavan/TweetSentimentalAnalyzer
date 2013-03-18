package model;

import util.Utility;

public class Tweet {

	private String tweetMessage;
	private String labelledFor;
	private String finalConsensus;
	private Integer student1Consensus;
	private Integer student2Consensus;

	public Tweet(String tweet, String labelledFor, String finalConsensus, Integer student1Consensus,
			Integer student2Consensus) {
		super();
		this.tweetMessage = tweet;
		this.labelledFor = labelledFor;
		this.finalConsensus = finalConsensus;
		this.student1Consensus = student1Consensus;
		this.student2Consensus = student2Consensus;
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
	public String getFinalConsensus() {
		return finalConsensus;
	}
	public void setFinalConsensus(String finalConsensus) {
		this.finalConsensus = finalConsensus;
	}
	public Integer getStudent1Consensus() {
		return student1Consensus;
	}
	public void setStudent1Consensus(Integer student1Consensus) {
		this.student1Consensus = student1Consensus;
	}
	public Integer getStudent2Consensus() {
		return student2Consensus;
	}
	public void setStudent2Consensus(Integer student2Consensus) {
		this.student2Consensus = student2Consensus;
	}
	public String getCleanedTweet(){
		return Utility.cleanTweetMessage(tweetMessage);				
	}
	
	public int getClassLabel(){
		
		if(finalConsensus.equals("-1"))
			return ClassLabel.NEGATIVEONE.value;
		else if(finalConsensus.equals("1"))
			return ClassLabel.ONE.value;
		else if(finalConsensus.equals("0"))
			return ClassLabel.ZERO.value;
		
		return ClassLabel.UNKNOWN.value;
	}
	
	public int getPolarityOfTheTweet(){
		return Utility.getPolarityOfTheString(getCleanedTweet());
	}

}
