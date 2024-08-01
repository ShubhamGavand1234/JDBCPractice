package org.example;
import  java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/census";
        String user = "root";
        String password = "admin";

        Connection connection = DriverManager.getConnection(url, user, password);

        if(connection != null) {
            System.out.println("DB connected");
        }

        Statement statement = connection.createStatement();
        String sql = "select count(*) as count from districts;";
        String sql2 = "select count(state_id) as count  , state_id from districts group by state_id order by state_id ASC;";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("count"));
        }
        ResultSet resultSet2 = statement.executeQuery(sql2);
        while (resultSet2.next()) {
            System.out.println(resultSet2.getInt("count") + "\t" + resultSet2.getInt("state_id"));
        }

        String preparedSQl = "select * from citizens where mother_tongue = ? and is_nri = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(preparedSQl);
        preparedStatement.setString(1,"hindi");
        preparedStatement.setInt(2,1);

        ResultSet resultSetPreparedSQL = preparedStatement.executeQuery();

        while (resultSetPreparedSQL.next()) {
            System.out.println(resultSetPreparedSQL.getInt("id") + "\t" + resultSetPreparedSQL.getString("name") + "\t" + resultSetPreparedSQL.getString("gender"));
        }


        String preparedSql2 = "select count(*) as count, gender from citizens where is_nri = ? group by gender;";
        PreparedStatement preparedStatement2 = connection.prepareStatement(preparedSql2);
        preparedStatement2.setInt(1,1);

        ResultSet resultSetPreparedSQL2 = preparedStatement2.executeQuery();

        while (resultSetPreparedSQL2.next()) {
            System.out.println(resultSetPreparedSQL2.getInt("count") + "\t" + resultSetPreparedSQL2.getString("gender"));
        }


        resultSet.close();
        resultSet2.close();
        resultSetPreparedSQL.close();
        resultSetPreparedSQL2.close();
        statement.close();
        preparedStatement.close();
        preparedStatement2.close();
        connection.close();


    }
}