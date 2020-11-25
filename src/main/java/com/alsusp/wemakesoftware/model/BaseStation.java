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
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "base_station")
public class BaseStation {

	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Pattern(regexp = "^[A-Za-z0-9\s]{1,50}$")
	@Column
	private String name;

	@NotNull
	@Positive
	@Column
	private float x;

	@NotNull
	@Positive
	@Column
	private float y;

	@NotNull
	@Positive
	@Column(name = "detection_radius_in_meters")
	private float detectionRadiusInMeters;
	
	@OneToMany(mappedBy = "baseStation")
	@JsonIgnore
	private List<Report> reports;

	public BaseStation() {
	}
	
	public BaseStation(String name, float x, float y, float detectionRadiusInMeters) {
		this(null, name, x, y, detectionRadiusInMeters, new ArrayList<>());
	}
	
	public BaseStation(UUID id, String name, float x, float y, float detectionRadiusInMeters) {
		this(id, name, x, y, detectionRadiusInMeters, new ArrayList<>());
	}

	public BaseStation(String name, float x, float y, float detectionRadiusInMeters, List<Report> reports) {
		this(null, name, x, y, detectionRadiusInMeters, reports);
	}

	public BaseStation(UUID id, String name, float x, float y, float detectionRadiusInMeters, List<Report> reports) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.detectionRadiusInMeters = detectionRadiusInMeters;
		this.reports = reports;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public float getDetectionRadiusInMeters() {
		return detectionRadiusInMeters;
	}

	public void setDetectionRadiusInMeters(float detectionRadiusInMeters) {
		this.detectionRadiusInMeters = detectionRadiusInMeters;
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
		BaseStation baseStation = (BaseStation) object;
		return (id == baseStation.id || (id != null && id.equals(baseStation.getId())))
				&& (name == baseStation.name || (name != null && name.equals(baseStation.getName())))
				&& x == baseStation.x && y == baseStation.y
				&& detectionRadiusInMeters == baseStation.detectionRadiusInMeters
				&& (reports == baseStation.reports || (reports != null && reports.equals(baseStation.getReports())));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(detectionRadiusInMeters);
		result = prime * result + ((reports == null) ? 0 : reports.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Base station ID: {0}, name: {1}, x: {2}, y: {3}, detection radius in meters: {4}",
				id, name, x, y, detectionRadiusInMeters);
	}
}
