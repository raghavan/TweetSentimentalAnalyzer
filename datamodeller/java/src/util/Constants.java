package util;


public interface Constants {
		//DB props
		String dbDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:8889/tweet_classify";
		String username = "root";
		String password = "root";
		
			
		//Query
		String GET_RESULT_FOR_OBAMA = "select * from tweet";
		String GET_RESULT_FOR_ROMNEY = "select * from tweet where labelled_for = 'romney'";
		
		String ROMNEY = "romney";
		String OBAMA = "obama";
		String ARFF_TRAINED_FILE = "files/trainedmodel.arff";
		String POLARITY_WORD_FILE = "files/wordpolarity.properties";
		String OBAMA_FILE = "files/obamaRomneyEq";
		String STOP_WORDS_FILE = "files/stopwords";
		Integer COUNT_THRESHOLD = 25;
		String GOOGLE_PREDICTION_FILE = "files/google_tweet_predictor.txt";
		
		//Symbols
		String QUOTE = "\"";
		String COMMA = ",";
		
		
}
