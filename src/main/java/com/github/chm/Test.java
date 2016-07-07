package com.github.chm;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chenhuaming on 16/7/8.
 */
public class Test {
    public static void main(String[] args){
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/hmdata"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        config.setUsername("root");
        config.setPassword("");
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        BoneCP connectionPool = null; // setup the connection pool
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        for(;;){
            try {
                System.out.println("kaishihuoqu");
                 conn = connectionPool.getConnection();
                System.out.println(conn);
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

        // fetch a connectionß

        // connection = connectionPool.getConnection();
    }
}
