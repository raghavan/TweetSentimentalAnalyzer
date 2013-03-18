package util;

import java.util.List;
import java.util.Set;

public interface Constants {
		//DB props
		String dbDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:8889/tweet_classify";
		String username = "root";
		String password = "root";
		
			
		//Query
		String GET_RESULT_FOR_OBAMA = "select * from tweet where labelled_for = 'obama'";
		String GET_RESULT_FOR_ROMNEY = "select * from tweet where labelled_for = 'romney'";
		
		String ROMNEY = "romney";
		String OBAMA = "obama";
		String ARFF_TRAINED_FILE = "files/trainedmodel.arff";
		
		
}
