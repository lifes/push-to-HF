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
        config.setJdbcUrl("jdbc:oracle:thin:@10.33.25.170:1521:orcl");
        config.setUsername("IPM20141117_38_BMS");
        config.setPassword("hik12345");
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
    }
}
