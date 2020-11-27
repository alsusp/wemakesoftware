package com.alsusp.wemakesoftware.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alsusp.wemakesoftware.exception.NotUniqueNameException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.BaseStation;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.model.Report;
import com.alsusp.wemakesoftware.service.BaseStationService;
import com.alsusp.wemakesoftware.service.MobileStationService;
import com.alsusp.wemakesoftware.service.ReportService;

@RestController
@RequestMapping("/api/baseStations")
public class BaseStationRestController {

	BaseStationService baseStationService;

	MobileStationService mobileStationService;

	ReportService reportService;

	public BaseStationRestController(BaseStationService baseStationService, MobileStationService mobileStationService,
			ReportService reportService) {
		this.baseStationService = baseStationService;
		this.mobileStationService = mobileStationService;
		this.reportService = reportService;
	}

	@GetMapping
	public List<BaseStation> findAll() {
		return baseStationService.findAll();
	}

	@GetMapping("/{uuid}")
	public BaseStation getBaseStation(@PathVariable("uuid") UUID id) {
		return baseStationService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity<Object> create(@Valid @RequestBody BaseStation baseStation, UriComponentsBuilder builder)
			throws NotUniqueNameException, NotValidQuantityException {
		baseStationService.save(baseStation);
		URI uri = builder.path("api/baseStations/{uuid}").build(baseStation.getId());
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{uuid}")
	public void update(@PathVariable("uuid") UUID id, @Valid @RequestBody BaseStation baseStation)
			throws NotUniqueNameException {
		if (baseStationService.findOne(id) != null) {
			baseStationService.update(baseStation);
		}
	}

	@DeleteMapping("/{uuid}")
	public void delete(@PathVariable("uuid") UUID id) {
		baseStationService.delete(id);
	}

	@PostMapping(value = "/reports")
	@ResponseStatus(CREATED)
	public void report(@Valid @RequestBody BaseStationReportDTO baseStationReportDTO) {
		BaseStation baseStation = baseStationService.findOne(baseStationReportDTO.getBaseStationId());
		baseStationReportDTO.getReports().forEach(report -> {
			MobileStation mobileStation = mobileStationService.findOne(report.getMobileStationId());
			updateMobileStationPosition(baseStation, mobileStation);
			reportService.save(new Report(baseStation, mobileStation, report.getDistance(), report.getTimestamp()));
		});
	}

	private void updateMobileStationPosition(BaseStation baseStation, MobileStation mobileStation) {
		mobileStation.setLastKnownX(baseStation.getX());
		mobileStation.setLastKnownY(baseStation.getY());
		mobileStationService.update(mobileStation);
	}
}
