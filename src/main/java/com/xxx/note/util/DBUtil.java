package com.xxx.note.util;

import com.mysql.cj.protocol.Resultset;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 10:42
 * @description：工具类
 */
public class DBUtil {

    // 接收properties的数据 得到配置对象
    private static Properties properties = new Properties();

    static {
        try {
            // 加载配置文件（输入流）
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 通过load()方法将输入流的内容加载到配置文件对象中
            properties.load(inputStream);
            // 通过配置文件对象的getProperty()方法获取驱动名，并加载驱动
            Class.forName(properties.getProperty("driverClass"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConection() {
        Connection connection = null;

        try {
            // 得到数据库连接的相关信息
            String dbUrl = properties.getProperty("url");
            String dbName = properties.getProperty("username");
            String dbPwd = properties.getProperty("password");
            // 得到数据库连接
            connection = DriverManager.getConnection(dbUrl, dbName, dbPwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭资源
     *
     * @param resultset
     * @param preparedStatement
     * @param connection
     */
    public static void close(ResultSet resultset, PreparedStatement preparedStatement, Connection connection) {

        try {
            // 判断资源对象如果不为空，则关闭
            if (resultset != null) {
                resultset.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
