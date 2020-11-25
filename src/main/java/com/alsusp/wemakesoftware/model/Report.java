package com.alsusp.wemakesoftware.model;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Report {

	@Id
	@GeneratedValue
	@Column
	private UUID id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "base_station_id")
	private BaseStation baseStation;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "mobile_station_id")
	private MobileStation mobileStation;
	
	@NotNull
	@Positive
	@Column
	private float distance;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column
	private LocalDateTime timestamp;
	
	public Report() {
	}
	
	public Report(BaseStation baseStation, MobileStation mobileStation, float distance, LocalDateTime timestamp) {
		this(null, baseStation, mobileStation, distance, timestamp);
	}
	
	public Report(UUID id, BaseStation baseStation, MobileStation mobileStation, float distance, LocalDateTime timestamp) {
		this.id = id;
		this.baseStation = baseStation;
		this.mobileStation = mobileStation;
		this.distance = distance;
		this.timestamp = timestamp;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BaseStation getBaseStation() {
		return baseStation;
	}

	public void setBaseStation(BaseStation baseStation) {
		this.baseStation = baseStation;
	}

	public MobileStation getMobileStation() {
		return mobileStation;
	}

	public void setMobileStation(MobileStation mobileStation) {
		this.mobileStation = mobileStation;
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
	
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || object.getClass() != this.getClass()) {
			return false;
		}
		Report report = (Report) object;
		return (id == report.id || (id != null && id.equals(report.getId())))
				&& (baseStation == report.baseStation || (baseStation != null && baseStation.equals(report.getBaseStation())))
				&& (mobileStation == report.mobileStation || mobileStation != null && mobileStation.equals(report.getMobileStation()))
				&& distance == report.distance
				&& (timestamp == report.timestamp || (timestamp != null && timestamp.equals(report.getTimestamp())));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((baseStation == null) ? 0 : baseStation.hashCode());
		result = prime * result + ((mobileStation == null) ? 0 : mobileStation.hashCode());
		result = prime * result + Float.floatToIntBits(distance);
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Report ID: {0}, baseStation: {1}, mobileStation: {2}, distance: {3}, timestamp: {4}", id, baseStation,
				mobileStation, distance, timestamp);
	}
}
