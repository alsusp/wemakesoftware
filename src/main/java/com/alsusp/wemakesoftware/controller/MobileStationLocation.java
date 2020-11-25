package com.alsusp.wemakesoftware.controller;

import java.util.UUID;

public class MobileStationLocation {

	private UUID mobileId;
	private float x;
	private float y;
	private float errorRadius;
	private int errorCode;
	private String errorDescription;
	
	public MobileStationLocation() {
	}
	
	public MobileStationLocation(UUID mobileId, float x, float y, float errorRadius, int errorCode, String errorDescription) {
		this.mobileId = mobileId;
		this.x = x;
		this.y = y;
		this.errorRadius = errorRadius;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public UUID getMobileId() {
		return mobileId;
	}

	public void setMobileId(UUID mobileId) {
		this.mobileId = mobileId;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getErrorRadius() {
		return errorRadius;
	}

	public void setErrorRadius(float errorRadius) {
		this.errorRadius = errorRadius;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
