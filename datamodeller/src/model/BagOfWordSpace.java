package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import util.Constants;
import util.Utility;

public class BagOfWordSpace {
	Map<String, Integer> wordCount = new HashMap<String, Integer>();

	public Integer getWordCount(String word) {
		return wordCount.get(word);
	}

	public void addWordCount(String word) {
		if (word != null && !word.equalsIgnoreCase(" ") && !word.isEmpty()) {
			word = word.trim();
			int count = 0;
			if (wordCount.containsKey(word)) {
				count = wordCount.get(word);
			}
			count += 1;
			wordCount.put(word, count);
		}
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

	public List<String> getAllUniqueWords() {
		Set<String> keys = getKeysInDesc();
		List<String> keyList = new ArrayList<String>(keys);
		List<String> stopWords = Utility.getAllStopWords();
		keyList.removeAll(stopWords);
		//keyList = keyList.subList(0, 500);	
		return keyList;
	}

	public Set<String> getKeysInDesc() {
		Set<String> words = new HashSet<String>();
		for (String key : wordCount.keySet()) {
			if (wordCount.get(key) > Constants.COUNT_THRESHOLD) {
				words.add(key.trim());
			}
		}
		return words;
	}
	
	/*public  Set<String> getKeysInDesc(){
		ValueComparator valComparator =  new ValueComparator(wordCount);
        TreeMap<String,Integer> sortedMapBasedOnValue = new TreeMap<String,Integer>(valComparator);
        sortedMapBasedOnValue.putAll(wordCount);
        Set<String> sortedString = new HashSet<String>();
        for(String str : sortedMapBasedOnValue.keySet()){
        	sortedString.add(str);
        }       
        return sortedString;
	}*/

}
