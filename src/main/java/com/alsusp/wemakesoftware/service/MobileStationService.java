package com.alsusp.wemakesoftware.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alsusp.wemakesoftware.dao.MobileStationDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.MobileStation;

@Service
public class MobileStationService {

	private static final Logger logger = LoggerFactory.getLogger(MobileStationService.class);

	private MobileStationDao mobileStationDao;

	MobileStationService(MobileStationDao mobileStationDao) {
		this.mobileStationDao = mobileStationDao;
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
		validateMaxQuantity();
		mobileStationDao.save(mobileStation);
	}
	
	public void update(MobileStation mobileStation) {
		logger.info("Update mobile station");
		mobileStationDao.save(mobileStation);
	}

	public void delete(UUID id) {
		logger.info("Delete mobile station by ID {}", id);
		mobileStationDao.delete(findOne(id));
	}

	private void validateMaxQuantity() throws NotValidQuantityException {
		if (mobileStationDao.findAll().size() >= 99) {
			throw new NotValidQuantityException("Exceeded the maximum number of mobile stations");
		}
	}
}
