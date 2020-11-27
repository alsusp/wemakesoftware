package com.alsusp.wemakesoftware.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alsusp.wemakesoftware.dao.ReportDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.model.Report;

@Service
public class ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

	private ReportDao reportDao;

	ReportService(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public List<Report> findAll() {
		logger.info("Find all reports");
		return reportDao.findAll();
	}

	public Report findOne(UUID id) {
		logger.info("Find report by ID {}", id);
		return reportDao.findById(id).orElseThrow(() -> new NotFoundException("Report not found by ID"));
	}

	public void save(Report report) {
		logger.info("Save report");
		reportDao.save(report);
	}

	public void delete(UUID id) {
		logger.info("Delete report by ID {}", id);
		reportDao.delete(findOne(id));
	}

	public Report getLastReportForMobileStation(MobileStation mobileStation) {
		return reportDao.findTopByMobileStationOrderByTimestampDesc(mobileStation).orElse(new Report());
	}
}
