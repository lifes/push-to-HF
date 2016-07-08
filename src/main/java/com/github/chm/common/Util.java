package com.github.chm.common;

public class Util {
	public static String getOracleJdbcUrl(String ip, String port, String db){
		return String.format("jdbc:oracle:thin:@%s:%s:%s", ip,port,db);
	}
}
