package com.alsusp.wemakesoftware.controller;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileStationLocationDTO {

	private UUID mobileId;
	private float x;
	private float y;
	
	@JsonProperty("error_radius")
	private float errorRadius;
	
	public MobileStationLocationDTO() {
	}
	
	public MobileStationLocationDTO(UUID mobileId, float x, float y, float errorRadius) {
		this.mobileId = mobileId;
		this.x = x;
		this.y = y;
		this.errorRadius = errorRadius;
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
}
