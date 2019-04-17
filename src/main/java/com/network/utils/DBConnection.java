package com.network.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public final class DBConnection{
    private static DataSource ds;
    //private volatile static BasicDataSource dataSource = null;
    static{
        Properties prop = new Properties();
        try{
            //prop.load(DBConnection.class.getClassLoader().getResourceAsStream("DBConfig.properties"));
            prop.load(DBConnection.class.getResourceAsStream("DBConfig.properties"));
            ds = BasicDataSourceFactory.createDataSource(prop);
            System.out.println("配置文件读取成功");
            System.out.println(ds.getConnection());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        System.out.println("正在进行连接");
        Connection conn = null;
        try{
            conn = ds.getConnection();
            System.out.println("连接成功");
        }catch (SQLException e){
            e.printStackTrace();
        }try {
            conn.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    //测试：
    //Connection conn = DBConnection.getConnection();

}
