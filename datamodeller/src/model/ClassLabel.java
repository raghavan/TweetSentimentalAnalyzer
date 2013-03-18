package model;

public enum ClassLabel {

	ONE(1), ZERO(0), NEGATIVEONE(-1), UNKNOWN(2);

	int value;

	ClassLabel(int value) {
		this.value = value;
	}

	public static String fromValue(int a) {
		for (ClassLabel polarity : ClassLabel.values()) {
			if (polarity.value == a) {
				return polarity.toString();
			}
		}
		return null;
	}

}
