package arff;

public class ArffData {

	private double unigramProbability;
	private double emoticonProbability;
	private int classLabel;
    private int polarity;

	public ArffData(double unigramProbability, double emoticonProbability,int polarity, int classLabel) {
		super();
		this.unigramProbability = unigramProbability;
		this.emoticonProbability = emoticonProbability;
		this.polarity = polarity;
		this.classLabel = classLabel;
	}

	public double getUnigramProbability() {
		return unigramProbability;
	}
	public void setUnigramProbability(float unigramProbability) {
		this.unigramProbability = unigramProbability;
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

	public String getArffStructuredData() {
		return roundOffTwoDecimals(unigramProbability)+","+roundOffTwoDecimals(emoticonProbability)+
				","+polarity+","+classLabel;
	}
	
	private double roundOffTwoDecimals(double value){
		double val = Math.round( value * 100.0 ) / 100.0;
		return val;
	}
	

	@Override
	public String toString() {
		return "ArffData [unigramProbability=" + unigramProbability + ", emoticonProbability=" + emoticonProbability
				+ ", classLabel=" + classLabel + "]";
	}

}
