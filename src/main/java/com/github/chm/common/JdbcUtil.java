package com.github.chm.common;

import com.github.chm.exception.InitDataConnectionPoolException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chenhuaming on 16/7/8.
 */
public class JdbcUtil {
    private static volatile BoneCP connectionPool;
    public static synchronized void initConnectionPool(String jdbcurl, String username, String password) throws InitDataConnectionPoolException {
        shutDownConnectionPool();
        connectionPool = null;
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(jdbcurl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            throw new InitDataConnectionPoolException("初始化数据库连接池失败!!!(可能由于地址或用户名或密码不对,或有问题数据库服务异常引起)",e);
        }
    }

    public static synchronized void shutDownConnectionPool() {
        if (connectionPool != null) {
            connectionPool.shutdown();
        }
    }
    public static Connection getConnection() throws SQLException {
        if(connectionPool == null){
            throw new RuntimeException("数据库连接池未初始化或初始化失败(可能由于地址或用户名或密码不对,或有问题数据库服务异常引起)");//程序应该保证不会出现这种情况
        }
        return connectionPool.getConnection();
    }
    public static String testDataConnectionStatus(){
        if(connectionPool == null){
            return "数据库连接池未初始化,或初始化异常(可能由于地址或用户名或密码不对,或有问题数据库服务异常引起)";
        }
        if(connectionPool.getDbIsDown().get()){
            return "数据库状态: 挂掉了";
        }
        return "数据库状态: 正常";
    }
}
