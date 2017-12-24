package cn.onearth.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by wliu on 2017/12/14 0014.
 */
public class JDBCTest {

    public static void main(String[] args) throws Exception {


        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://120.55.63.232:3306/fmzs?useUnicode=true&characterEncoding=utf-8";


        String user = "root";
        String password = "STRliu_0605..";

        Connection connection = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM book_summary WHERE id = 102";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){

            String name = resultSet.getString("name");

            System.out.println(name);
        }



    }
}
