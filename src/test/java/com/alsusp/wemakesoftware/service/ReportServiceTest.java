package com.alsusp.wemakesoftware.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alsusp.wemakesoftware.dao.ReportDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.model.BaseStation;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.model.Report;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

	@Mock
	private ReportDao reportDao;

	@InjectMocks
	private ReportService reportService;

	@Test
	void whenFindAll_thenAllExistingReportsFound() {
		List<Report> expected = new ArrayList<>();
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report report1 = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());
		Report report2 = new Report(id, baseStation, mobileStation, 2.0f, LocalDateTime.now());
		expected.add(report1);
		expected.add(report2);
		when(reportDao.findAll()).thenReturn(expected);

		List<Report> actual = reportService.findAll();

		assertEquals(expected, actual);
		verify(reportDao).findAll();
	}

	@Test
	void givenExistingId_whenFindOne_thenReportFound() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report expected = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());
		Optional<Report> report = Optional.of(expected);
		when(reportDao.findById(id)).thenReturn(report);

		Report actual = reportService.findOne(id);

		assertEquals(expected, actual);
		verify(reportDao).findById(id);
	}

	@Test
	void givenNotExistingId_whenFindOne_thenNotFoundException() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		assertThrows(NotFoundException.class, () -> reportService.findOne(id));
	}

	@Test
	void givenReport_whenSave_thenReportWasSaved() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report report = new Report(baseStation, mobileStation, 5.0f, LocalDateTime.now());

		reportService.save(report);

		verify(reportDao).save(report);
	}

	@Test
	void givenReport_whenSave_thenReportWasUpdated() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report report = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());

		reportService.save(report);

		verify(reportDao).save(report);
	}

	@Test
	void givenReport_whenDelete_thenReportWasDeleted() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report expected = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());
		Optional<Report> report = Optional.of(expected);
		when(reportDao.findById(id)).thenReturn(report);

		reportService.delete(id);

		verify(reportDao).delete(expected);
	}
	
	@Test
	void givenMobileStation_whenGetLastReportForMobileStation_thenGotReport() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		Report expected = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());
		Optional<Report> report = Optional.of(expected);
		when(reportDao.findTopByMobileStationOrderByTimestampDesc(mobileStation)).thenReturn(report);

		Report actual = reportService.getLastReportForMobileStation(mobileStation);
		
		assertEquals(expected, actual);
		verify(reportDao).findTopByMobileStationOrderByTimestampDesc(mobileStation);
	}
}
