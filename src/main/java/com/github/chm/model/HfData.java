package com.github.chm.model;

import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chenhuaming on 16/7/11.
 */
public class HfData {
	
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String magic = "hfrz";
	private String cmd = "addinfo";
	private String fileName = UUID.randomUUID().toString().replaceAll("-", "");
	private String imageData ;	
	private String imageURL;
	private Long  snapshotTime;
	private String deviceId;
	private String plateNumber;
	private String plateType;
	private String laneID;
	private	String speed;
	private String alarmID="0";
	private String carStatus;
	private Integer vehicleId;
	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	@JSONField(name = "ImageURL")
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Long snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getLaneID() {
		return laneID;
	}

	public void setLaneID(String laneID) {
		this.laneID = laneID;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(String alarmID) {
		this.alarmID = alarmID;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

}
