package com.alsusp.wemakesoftware.controller;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseStationReportDTO {

	@JsonProperty("base_station_id")
	private UUID baseStationId;
	private List<ReportDTO> reports;
	
	public BaseStationReportDTO() {
	}
	
	public BaseStationReportDTO(UUID baseStationId, List<ReportDTO> reports) {
		this.baseStationId = baseStationId;
		this.reports = reports;
	}

	public UUID getBaseStationId() {
		return baseStationId;
	}

	public void setBaseStationId(UUID baseStationId) {
		this.baseStationId = baseStationId;
	}

	public List<ReportDTO> getReports() {
		return reports;
	}

	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}
}
