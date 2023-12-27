package util;

import java.sql.*;


public class DBConnection {
	private static Connection connection;

    private DBConnection() {
        // private constructor to prevent instantiation
    }
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load JDBC driver
            	Class.forName("com.mysql.cj.jdbc.Driver");  
                System.out.println("Driver is loaded");

                // Get database connection from the properties file
                String connectionString = PropertyUtil.getPropertyString();
                //System.out.println(connectionString);
                
                connection = DriverManager.getConnection(connectionString);
                System.out.println("connected");
            } catch (ClassNotFoundException  | SQLException e) {
            	System.out.println("Could not connect");
                e.printStackTrace(); // Handle exception appropriately in a real-world scenario
            }
        }
        return connection;
    }

}
