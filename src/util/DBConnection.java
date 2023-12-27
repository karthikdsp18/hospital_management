package util;

import java.sql.*;


public class DBConnection {
	
	static Connection connection = null;
	
    public static Connection getConnection() {
    	String propstr=DBPropertyUtil.getPropertyString("./src/db.properties");
		connection=DBConnUtil.getConnection(propstr);
		return connection;
    }

}
