package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	private static final String PROPERTY_FILE = "db.properties";

    private PropertyUtil() {
        // private constructor to prevent instantiation
    }

    public static String getPropertyString() {
        try (InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + PROPERTY_FILE);
            }

            Properties properties = new Properties();
            properties.load(input);

            // Build connection string
            String host = properties.getProperty("db.host");
            String dbName = properties.getProperty("db.name");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String port = properties.getProperty("db.port");
            
            String ans = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?user=" + username + "&password=" + password;

            return ans;

        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately in a real-world scenario
            throw new RuntimeException("Error reading property file: " + PROPERTY_FILE);
        }
    }

}
