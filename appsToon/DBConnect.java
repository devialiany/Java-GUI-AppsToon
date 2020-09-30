package appsToon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
	private Connection connect;
	private Statement state;
	
	public void openCon() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/apps_toon", "root", "");
		state = connect.createStatement();
		System.out.println("Connect to database");
	}
	
	// FOR SELECT QUERY
	public ResultSet executeQuery(String sql) throws Exception{
		return state.executeQuery(sql);
	}
	
	// FOR INSERT, DELETE, UPDATE QUERY
	public void executeUpdate(String sql) throws Exception{
		state.executeUpdate(sql);
	}
	
	public void closeCon() throws Exception{
		state.close();
		connect.close();
	}
}
