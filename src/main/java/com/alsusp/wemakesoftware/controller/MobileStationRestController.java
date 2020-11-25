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

import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.service.MobileStationService;
import com.alsusp.wemakesoftware.service.ReportService;

@RestController
@RequestMapping("/api/mobileStations")
public class MobileStationRestController {

	MobileStationService mobileStationService;

	ReportService reportService;

	public MobileStationRestController(MobileStationService mobileStationService, ReportService reportService) {
		this.mobileStationService = mobileStationService;
		this.reportService = reportService;
	}

	@GetMapping
	public List<MobileStation> findAll() {
		return mobileStationService.findAll();
	}

	@GetMapping("/{uuid}")
	public MobileStation getMobileStation(@PathVariable("uuid") UUID id) {
		return mobileStationService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity<Object> create(@Valid @RequestBody MobileStation mobileStation, UriComponentsBuilder builder) throws NotValidQuantityException {
		mobileStationService.save(mobileStation);
		URI uri = builder.path("api/mobileStations/{uuid}").build(mobileStation.getId());
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{uuid}")
	public void update(@PathVariable("uuid") UUID id, @Valid @RequestBody MobileStation mobileStation) throws NotValidQuantityException {
		if (mobileStationService.findOne(id) != null) {
			mobileStationService.save(mobileStation);
		}
	}

	@DeleteMapping("/{uuid}")
	public void delete(@PathVariable("uuid") UUID id) {
		mobileStationService.delete(id);
	}
	
	@GetMapping("/location/{uuid}")
	public MobileStationLocation getMobileStationLocation(@PathVariable("uuid") UUID id) {
		MobileStation mobileStation =  mobileStationService.findOne(id);
		MobileStationLocation mobileStationLocation = new MobileStationLocation();
		mobileStationLocation.setMobileId(mobileStation.getId());
		mobileStationLocation.setX(mobileStation.getLastKnownX());
		mobileStationLocation.setY(mobileStation.getLastKnownY());
		mobileStationLocation.setErrorRadius(1f);
		mobileStationLocation.setErrorCode(4);
		mobileStationLocation.setErrorDescription("Hard situation");
		return mobileStationLocation;
	}
}
