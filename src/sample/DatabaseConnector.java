package sample;

/*
 * Created by martsime on 28/02/2017.
 */

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector {

    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/martsime_databaseprosjekt";

    // Database credentials, login information.
    private final String USER = "martsime";
    private final String PASS = "Orakelerbest123";

    private Connection conn = null;
    private Statement stmt = null;

    private PreparedStatement preparedStatement;

    private ResultSet rs;

    public void establishConnection() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminateConnection() {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public ArrayList<String> select(String query) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String label = meta.getColumnName(i);
                    row.append(label).append(";");
                    row.append(rs.getString(label));
                    if (i != meta.getColumnCount()) {
                        row.append(",");
                    }
                }
                result.add(row.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void insert(String query) {

        try {
            stmt = conn.createStatement();
            preparedStatement = conn.prepareStatement(query);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
            ;
        }

    }

}
