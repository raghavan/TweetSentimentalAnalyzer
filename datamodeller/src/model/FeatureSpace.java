package model;

import java.util.HashMap;
import java.util.Map;

import util.Utility;

public class FeatureSpace {
	Map<String, Integer> wordCount = new HashMap<String, Integer>();

	public Integer getWordCount(String word) {
		return wordCount.get(word);
	}

	public void addWordCount(String word) {
		int count = 0;
		if (wordCount.containsKey(word)) {
			count = wordCount.get(word);
		}
		count += 1;
		wordCount.put(word, count);
	}

	public Map<String, Integer> getWordCountMap() {
		return wordCount;
	}

	public Integer getWordCountTotalSize() {
		if (wordCount != null)
			return wordCount.size();
		return 0;
	}

	public int getTotalEmoticonCount() {
		int count = 0;
		for (String str : wordCount.keySet()) {
			if (Utility.getEmoticons().contains(str))
				count += wordCount.get(str);
		}
		return count;
	}

	public int getTotalWordCount() {
		int count = 0;
		for (String str : wordCount.keySet()) {
			if (!Utility.getEmoticons().contains(str))
				count += wordCount.get(str);
		}
		return count;
	}

	public int getDistinctWordCount() {
		int count = 0;
		for (String str : wordCount.keySet()) {
			if (!Utility.getEmoticons().contains(str))
				count += 1;
		}
		return count;
	}

	
	public int getDistinctEmoticonCount() {
		int count = 0;
		for (String str : wordCount.keySet()) {
			if (Utility.getEmoticons().contains(str))
				count += 1;
		}
		return count;
	}

	public void print() {
		for (String str : wordCount.keySet()) {
			System.out.println("Word = " + str + " count = " + wordCount.get(str));
		}
	}


}
