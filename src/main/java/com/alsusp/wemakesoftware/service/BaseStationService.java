package com.alsusp.wemakesoftware.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alsusp.wemakesoftware.dao.BaseStationDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.exception.NotUniqueNameException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.BaseStation;

@Service
public class BaseStationService {

	private static final Logger logger = LoggerFactory.getLogger(BaseStationService.class);

	private BaseStationDao baseStationDao;

	BaseStationService(BaseStationDao baseStationDao) {
		this.baseStationDao = baseStationDao;
	}

	public List<BaseStation> findAll() {
		logger.info("Find all base stations");
		return baseStationDao.findAll();
	}

	public BaseStation findOne(UUID id) {
		logger.info("Find base station by ID {}", id);
		return baseStationDao.findById(id).orElseThrow(() -> new NotFoundException("Base station not found by ID"));
	}

	public void save(BaseStation baseStation) throws NotUniqueNameException, NotValidQuantityException {
		logger.info("Save base station");
		validateMaxQuantity();
		validateNameUnique(baseStation);
		baseStation.setName(StringUtil.capitalize(baseStation.getName()));
		baseStationDao.save(baseStation);
	}
	
	public void update(BaseStation baseStation) throws NotUniqueNameException {
		logger.info("Update base station");
		validateNameUnique(baseStation);
		baseStation.setName(StringUtil.capitalize(baseStation.getName()));
		baseStationDao.save(baseStation);
	}

	public void delete(UUID id) {
		logger.info("Delete base station by ID {}", id);
		baseStationDao.delete(findOne(id));
	}

	public BaseStation findByName(String name) {
		logger.info("Find base station by name {}", name);
		return baseStationDao.findByName(StringUtil.capitalize(name))
				.orElseThrow(() -> new NotFoundException("Base station not found by name"));
	}

	private void validateNameUnique(BaseStation baseStation) throws NotUniqueNameException {
		Optional<BaseStation> existingBaseStation = baseStationDao.findByName(baseStation.getName());
		if (existingBaseStation.isPresent() && !existingBaseStation.get().getId().equals(baseStation.getId())) {
			throw new NotUniqueNameException("Base station with name " + baseStation.getName() + " already exists");
		}
	}

	private void validateMaxQuantity() throws NotValidQuantityException {
		if (baseStationDao.findAll().size() >= 99) {
			throw new NotValidQuantityException("Exceeded the maximum number of base stations");
		}
	}
}
