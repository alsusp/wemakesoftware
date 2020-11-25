package com.alsusp.wemakesoftware.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "mobile_station")
public class MobileStation {

	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@NotNull
	@Positive
	@Column(name = "last_known_x")
	private float lastKnownX;

	@NotNull
	@Positive
	@Column(name = "last_known_y")
	private float lastKnownY;
	
	@OneToMany(mappedBy = "mobileStation")
	@JsonIgnore
	private List<Report> reports;

	public MobileStation() {
	}

	public MobileStation(float lastKnownX, float lastKnownY) {
		this(null, lastKnownX, lastKnownY, new ArrayList<>());
	}
	
	public MobileStation(UUID id, float lastKnownX, float lastKnownY) {
		this(id, lastKnownX, lastKnownY, new ArrayList<>());
	}
	
	public MobileStation(float lastKnownX, float lastKnownY, List<Report> reports) {
		this(null, lastKnownX, lastKnownY, reports);
	}

	public MobileStation(UUID id, float lastKnownX, float lastKnownY, List<Report> reports) {
		this.id = id;
		this.lastKnownX = lastKnownX;
		this.lastKnownY = lastKnownY;
		this.reports = reports;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public float getLastKnownX() {
		return lastKnownX;
	}

	public void setLastKnownX(float lastKnownX) {
		this.lastKnownX = lastKnownX;
	}

	public float getLastKnownY() {
		return lastKnownY;
	}

	public void setLastKnownY(float lastKnownY) {
		this.lastKnownY = lastKnownY;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || object.getClass() != this.getClass()) {
			return false;
		}
		MobileStation mobileStation = (MobileStation) object;
		return (id == mobileStation.id || (mobileStation.id != null && id.equals(mobileStation.getId())))
				&& lastKnownX == mobileStation.lastKnownX && lastKnownY == mobileStation.lastKnownY
				&& (reports == mobileStation.reports || (reports != null && reports.equals(mobileStation.getReports())));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(lastKnownX);
		result = prime * result + Float.floatToIntBits(lastKnownY);
		result = prime * result + ((reports == null) ? 0 : reports.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Mobile station ID: {0}, last known x: {1}, last known y: {2}", id, lastKnownX,
				lastKnownY);
	}
}
