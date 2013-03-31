package arff;

import java.util.List;
import java.util.Map;

public class ArffData {

	private Map<String, Integer> wordCountOfTweet;
	private double emoticonProbability;
	private int polarity;
	private int classLabel;

	public ArffData(Map<String, Integer> wordCountOfTweet, double emoticonProbability, int polarity, int classLabel) {

		this.wordCountOfTweet = wordCountOfTweet;
		this.emoticonProbability = emoticonProbability;
		this.polarity = polarity;
		this.classLabel = classLabel;
	}

	public Map<String, Integer> getWordCountOfTweet() {
		return wordCountOfTweet;
	}
	public void setWordCountOfTweet(Map<String, Integer> wordCountOfTweet) {
		this.wordCountOfTweet = wordCountOfTweet;
	}

	public double getEmoticonProbability() {
		return emoticonProbability;
	}
	public void setEmoticonProbability(float emoticonProbability) {
		this.emoticonProbability = emoticonProbability;
	}
	public int getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(int classLabel) {
		this.classLabel = classLabel;
	}

	public int getPolarity() {
		return polarity;
	}

	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public String getArffStructuredData(List<String> uniqueWords) {
		StringBuffer arrfStructure = new StringBuffer();
		for (String str : uniqueWords) {
			if (wordCountOfTweet.containsKey(str)) {
				arrfStructure.append(wordCountOfTweet.get(str) + ",");
			}else{
				arrfStructure.append(0 + ",");
			}				
		}
		//arrfStructure.append(roundOffTwoDecimals(emoticonProbability) + ",");
		arrfStructure.append(polarity + "," + classLabel);
		return arrfStructure.toString();
	}
	private double roundOffTwoDecimals(double value) {
		double val = Math.round(value * 100.0) / 100.0;
		return val;
	}

	@Override
	public String toString() {
		return "ArffData = [ emoticonProbability=" + emoticonProbability + ", classLabel=" + classLabel + "]";
	}

}
