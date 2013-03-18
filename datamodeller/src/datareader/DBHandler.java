package datareader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import util.Constants;
import model.Tweet;

public class DBHandler implements IDataHandler {

	@Override
	public List<Tweet> getAllTweets(String labelledFor) {
		String query = null;
		if (labelledFor.equals(Constants.OBAMA)) {
			query = Constants.GET_RESULT_FOR_OBAMA;
		} else if (labelledFor.equals(Constants.ROMNEY)) {
			query = Constants.GET_RESULT_FOR_ROMNEY;
		}
		List<Tweet> tweets = new ArrayList<Tweet>();
		if (query != null) {
			ResultSet results = getResults(query, DBConnect.getConnection());
			tweets = makeTweetFromResultSet(results);
		}
		return tweets;
	}

	private ResultSet getResults(String query, Connection conn) {

		query = Util.escape(query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (MySQLSyntaxErrorException me) {
			System.out.println("Error in select query =" + query);
		} catch (SQLException e) {
			System.out.println("Error in select query =" + query);
			e.printStackTrace();
		}
		return rs;
	}

	private List<Tweet> makeTweetFromResultSet(ResultSet results) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		if (results != null) {

			try {
				while (results.next()) {
					Tweet tweet = new Tweet(results.getString("annotated_tweet"), results.getString("labelled_for"),
							results.getString("final_class_consensus"), results.getInt("student1"),
							results.getInt("student1"));
					tweets.add(tweet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tweets;
	}

}