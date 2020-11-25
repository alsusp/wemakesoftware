package com.alsusp.wemakesoftware.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alsusp.wemakesoftware.dao.BaseStationDao;
import com.alsusp.wemakesoftware.dao.MobileStationDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.BaseStation;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.model.Report;

@Service
public class MobileStationService {

	private static final Logger logger = LoggerFactory.getLogger(MobileStationService.class);

	private MobileStationDao mobileStationDao;

	private BaseStationDao baseStationDao;

	private ReportService reportService;

	MobileStationService(MobileStationDao mobileStationDao, BaseStationDao baseStationDao,
			ReportService reportService) {
		this.mobileStationDao = mobileStationDao;
		this.baseStationDao = baseStationDao;
		this.reportService = reportService;
	}

	public List<MobileStation> findAll() {
		logger.info("Find all mobile stations");
		return mobileStationDao.findAll();
	}

	public MobileStation findOne(UUID id) {
		logger.info("Find mobile station by ID {}", id);
		return mobileStationDao.findById(id).orElseThrow(() -> new NotFoundException("Mobile station not found by ID"));
	}

	public void save(MobileStation mobileStation) throws NotValidQuantityException {
		logger.info("Save mobile station");
		if (mobileStation.getId() == null) {
			validateMaxQuantity();
		}
		validateDetection(mobileStation);
		mobileStationDao.save(mobileStation);
	}

	public void delete(UUID id) {
		logger.info("Delete mobile station by ID {}", id);
		mobileStationDao.delete(findOne(id));
	}

	private void validateMaxQuantity() throws NotValidQuantityException {
		if (mobileStationDao.findAll().size() >= 100) {
			throw new NotValidQuantityException("Exceeded the maximum number of mobile stations");
		}
	}

	private void validateDetection(MobileStation mobileStation) {
		baseStationDao.findAll().forEach(baseStation -> {
			if (isMobileStationInTheRadiusOfBaseStation(baseStation, mobileStation)) {
				float distance = findDistanceBetweenBaseStationAndMobileStation(baseStation, mobileStation);
				reportService.save(new Report(baseStation, mobileStation, distance, LocalDateTime.now()));
			}
		});
	}

	private boolean isMobileStationInTheRadiusOfBaseStation(BaseStation baseStation, MobileStation mobileStation) {
		return Math.pow(mobileStation.getLastKnownX() - baseStation.getX(), 2) + Math.pow(mobileStation.getLastKnownY() - baseStation.getY(), 2) <= Math
				.pow(baseStation.getDetectionRadiusInMeters(), 2);
	}

	private float findDistanceBetweenBaseStationAndMobileStation(BaseStation baseStation, MobileStation mobileStation) {
		return (float) Math.sqrt(Math.pow(mobileStation.getLastKnownX() - baseStation.getX(), 2)
				+ Math.pow(mobileStation.getLastKnownY() - baseStation.getY(), 2));
	}
}
