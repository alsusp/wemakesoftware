package com.alsusp.wemakesoftware.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportDTO {
	
	@JsonProperty("mobile_station_id")
	private UUID mobileStationId;
	private float distance;
	private LocalDateTime timestamp;
	
	public ReportDTO() {
	}
	
	public ReportDTO(UUID mobileStationId, float distance, LocalDateTime timestamp) {
		this.mobileStationId = mobileStationId;
		this.distance = distance;
		this.timestamp = timestamp;
	}

	public UUID getMobileStationId() {
		return mobileStationId;
	}

	public void setMobileStationId(UUID mobileStationId) {
		this.mobileStationId = mobileStationId;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
