package model;

public class ArffData {

	private float unigramProbability;
	private float emoticonProbability;
	private ClassLabel classLabel;

	// private float polarityRatio;

	public ArffData(float unigramProbability, float emoticonProbability, ClassLabel classLabel) {
		super();
		this.unigramProbability = unigramProbability;
		this.emoticonProbability = emoticonProbability;
		this.classLabel = classLabel;
	}

	public float getUnigramProbability() {
		return unigramProbability;
	}
	public void setUnigramProbability(float unigramProbability) {
		this.unigramProbability = unigramProbability;
	}
	public float getEmoticonProbability() {
		return emoticonProbability;
	}
	public void setEmoticonProbability(float emoticonProbability) {
		this.emoticonProbability = emoticonProbability;
	}
	public ClassLabel getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(ClassLabel classLabel) {
		this.classLabel = classLabel;
	}

	@Override
	public String toString() {
		return "ArffData [unigramProbability=" + unigramProbability + ", emoticonProbability=" + emoticonProbability
				+ ", classLabel=" + classLabel + "]";
	}

}
