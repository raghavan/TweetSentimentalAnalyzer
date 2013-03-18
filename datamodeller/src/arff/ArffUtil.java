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

	public static List<String> getArffAttributes() {
		List<String> attributes = new ArrayList<String>();
		attributes.add("@ATTRIBUTE unigram  NUMERIC");
		attributes.add("@ATTRIBUTE emoticon  NUMERIC");
		attributes.add("@ATTRIBUTE class  {-1,0,1,2}");
		return attributes;
	}

	public static List<String> getArrfData(List<ArffData> arffRecords) {
		List<String> dataRecords = new ArrayList<String>();
		dataRecords.add("@DATA");
		for (ArffData arffData : arffRecords) {
			dataRecords.add(arffData.getArffStructuredData());
		}
		return dataRecords;
	}

	public static void writeArffDataToFille(List<ArffData> arffRecords) {
		try {

			FileWriter fstream = new FileWriter(Constants.ARFF_TRAINED_FILE);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getArffRelation());
			goNLines(out,2);
			for (String str : getArffAttributes()) {
				out.write(str);
				goNLines(out,1);
			}
			goNLines(out,1);
			for (String str : getArrfData(arffRecords)) {
				out.write(str);
				goNLines(out,1);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void goNLines(BufferedWriter out, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			out.write("\n");
		}
	}
}
