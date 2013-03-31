package arff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;

public class ArffUtil {

	public static String getArffRelation() {
		String arffRelation = "@RELATION TweetClassify";
		return arffRelation;
	}

	public static List<String> getArffAttributes(List<String> attributeNames) {
		List<String> attributes = new ArrayList<String>();
		for (String attributeName : attributeNames) {
			attributes.add("@ATTRIBUTE "+attributeName.toLowerCase()+"  NUMERIC");	
			//System.out.println(attributeName.toLowerCase());
		}
		//attributes.add("@ATTRIBUTE emoticonProbability NUMERIC");
		attributes.add("@ATTRIBUTE polarity NUMERIC");
		attributes.add("@ATTRIBUTE class  {-1,0,1,2}");
		return attributes;
	}

	public static List<String> getArrfData(List<String> uniqueWords,List<ArffData> arffRecords) {
		List<String> dataRecords = new ArrayList<String>();
		dataRecords.add("@DATA");
		for (ArffData arffData : arffRecords) {						
			dataRecords.add(arffData.getArffStructuredData(uniqueWords));
		}
		return dataRecords;
	}

	public static void writeArffDataToFille(List<String> attributeNames,List<ArffData> arffRecords) {
		try {

			FileWriter fstream = new FileWriter(Constants.ARFF_TRAINED_FILE);
			BufferedWriter out = new BufferedWriter(fstream);
			print(out,getArffRelation());
			goNLines(out, 2);
			List<String> structuredAtrributeNames = getArffAttributes(attributeNames);
			for (String structuredAttrib : structuredAtrributeNames ) {
				print(out,structuredAttrib);
				goNLines(out, 1);
			}
			goNLines(out, 1);
			List<String> arffData = getArrfData(attributeNames,arffRecords);
			for (String str : arffData) {
				print(out,str);
				goNLines(out, 1);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void print(BufferedWriter out,String str) throws IOException {
			out.write(str);
	}

	private static void goNLines(BufferedWriter out, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			out.write("\n");
		}
	}
}
