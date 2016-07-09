package com.github.chm;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by chenhuaming on 16/7/8.
 */
public class Test {
    public static void main(String[] args){

        //config.setJdbcUrl("jdbc:oracle:thin:@10.33.25.170:1521:orcl");
        Long t1 = System.currentTimeMillis();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(10);
            DriverManager.getConnection("jdbc:mysql://10.33.25.170:3306/hmdata", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("fuck");
            e.printStackTrace();
        }
        Long t2 =System.currentTimeMillis();
        System.out.println("time===:"+(t2-t1));

        BoneCPConfig config = new BoneCPConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/hmdata");
        config.setUsername("root");
        config.setPassword("");
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        config.setConnectionTimeoutInMs(2000);
        BoneCP connectionPool = null; // setup the connection pool
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        for(int i=1;i<10000000;i++){
            try {
                System.out.println("kaishihuoqu");
                conn = connectionPool.getConnection();
                System.out.println(conn);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
