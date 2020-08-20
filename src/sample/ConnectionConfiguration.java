package sample;

import java.sql.*;

public class ConnectionConfiguration {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/simdb", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }


    public static void main(String[] args) {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration();
        Connection connection = connectionConfiguration.getConnection();
        if(connection!=null) System.out.println("connected");
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from user");
            if (stmt.execute("select * from user")) {
                rs = stmt.getResultSet();
                while(rs.next()){
                    System.out.println(rs.getInt("id"));
                    System.out.println(rs.getString("password"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}