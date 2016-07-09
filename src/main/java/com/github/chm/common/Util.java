package com.github.chm.common;

import java.io.*;
import java.util.Properties;

public class Util {
	public static String getOracleJdbcUrl(String ip, String port, String db){
		return String.format("jdbc:oracle:thin:@%s:%s:%s", ip,port,db);
	}
	public static Properties readPropertiesFromFile(String dir, String fileName){
		Properties prop = new Properties();
		File file = new File(dir + File.separator + fileName);
		if(file.exists()){
			InputStream in = null;
			try {
				 in = new FileInputStream(file);
				 prop.load(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return prop;
	}
	public static boolean writePropertiesToFile(String dir, String fileName, Properties prop, String comment) {
		if (prop == null) {
			throw new IllegalArgumentException("参数不能未null");
		}
		File folder = new File(dir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(dir + File.separator + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			prop.store(out, comment);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static void main(String args[]){
		Properties prop = Util.readPropertiesFromFile("records","couts.txt");
		System.out.println(prop.get("counts"));
		System.out.println(prop.get("count"));
	}
}
