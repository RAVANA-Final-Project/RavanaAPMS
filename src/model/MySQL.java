package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author dulanjaya
 */
public class MySQL {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rapms_db", "root", "459@SofT#374");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String query) throws Exception {
        Statement statement = connection.createStatement();

        if (query.startsWith("SELECT")) {
            ResultSet resultset = statement.executeQuery(query);
            return resultset;
        } else {
            statement.executeUpdate(query);
            return null;
        }
    }
}
