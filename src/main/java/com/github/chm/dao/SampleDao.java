package com.github.chm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.github.chm.common.DateUtil;
import com.github.chm.common.JdbcUtil;
import com.github.chm.model.HfData;

public class SampleDao {
	public Long getMinVehicleIdByTime(Date vehicleStartTime) throws SQLException {
		Long vehicle_id = null;
		Connection conn = null;	
		try {
			conn = JdbcUtil.getConnection();
			String startTime = DateUtil.format(vehicleStartTime);
			String endTime = DateUtil.format(new Date(vehicleStartTime.getTime() + 60L));
			String sql = String.format(
					"select vehicle_id from ( select vehicle_id from BMS_VEHICLE_PASS where  pass_Time Between to_date('%s','yyyy-mm-dd hh24:mi:ss')AND to_date('%s','yyyy-mm-dd hh24:mi:ss') ORDER BY vehicle_id asc )where rownum = 1",
					startTime, endTime);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				vehicle_id = rs.getLong(1);
			}
			rs.close();
			stmt.close();			
		} finally {
			if(conn!=null){
				conn.close();
			}
		}
		return vehicle_id;
	}
	public List<HfData> getVehicleDatas(long beginId, long endId) throws SQLException {
		Long vehicle_id = null;
		Connection conn = null;	
		try {
			conn = JdbcUtil.getConnection();
			
			String sql = String.format(
					"select vehicle_id from ( select vehicle_id from BMS_VEHICLE_PASS where  pass_Time Between to_date('%s','yyyy-mm-dd hh24:mi:ss')AND to_date('%s','yyyy-mm-dd hh24:mi:ss') ORDER BY vehicle_id asc )where rownum = 1"
					);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				vehicle_id = rs.getLong(1);
			}
			rs.close();
			stmt.close();			
		} finally {
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
}
