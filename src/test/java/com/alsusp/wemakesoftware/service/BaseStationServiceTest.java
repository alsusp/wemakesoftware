package com.alsusp.wemakesoftware.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alsusp.wemakesoftware.dao.BaseStationDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.exception.NotUniqueNameException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.BaseStation;

@ExtendWith(MockitoExtension.class)
class BaseStationServiceTest {

	@Mock
	private BaseStationDao baseStationDao;

	@InjectMocks
	private BaseStationService baseStationService;

	@Test
	void whenFindAll_thenAllExistingBaseStationsFound() {
		List<BaseStation> expected = new ArrayList<>();
		BaseStation baseStation1 = new BaseStation(UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2"),
				"BaseStation1", 3.0f, 7.0f, 5f);
		BaseStation baseStation2 = new BaseStation(UUID.fromString("33398908-8e48-48c3-82c0-954e1cd2a811"),
				"BaseStation2", 11.0f, 7.0f, 5.0f);
		expected.add(baseStation1);
		expected.add(baseStation2);
		when(baseStationDao.findAll()).thenReturn(expected);

		List<BaseStation> actual = baseStationService.findAll();

		assertEquals(expected, actual);
		verify(baseStationDao).findAll();
	}

	@Test
	void givenExistingId_whenFindOne_thenBaseStationFound() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation expected = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		Optional<BaseStation> baseStation = Optional.of(expected);
		when(baseStationDao.findById(id)).thenReturn(baseStation);

		BaseStation actual = baseStationService.findOne(id);

		assertEquals(expected, actual);
		verify(baseStationDao).findById(id);
	}

	@Test
	void givenNotExistingId_whenFindOne_thenNotFoundException() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		assertThrows(NotFoundException.class, () -> baseStationService.findOne(id));
	}

	@Test
	void givenBaseStation_whenSave_thenBaseStationWasSaved() throws NotUniqueNameException, NotValidQuantityException {
		BaseStation baseStation = new BaseStation("BaseStation1", 3.0f, 7.0f, 5f);

		baseStationService.save(baseStation);

		verify(baseStationDao).save(baseStation);
	}
	
	@Test
	void givenBaseStationWithExistingName_whenSave_thenNotUniqueNameException() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		Optional<BaseStation> existingBaseStation = Optional.of(baseStation);
		BaseStation newBaseStation = new BaseStation("BaseStation1", 3.0f, 7.0f, 5f);
		when(baseStationDao.findByName("BaseStation1")).thenReturn(existingBaseStation);

		assertThrows(NotUniqueNameException.class, () -> baseStationService.save(newBaseStation));

		verify(baseStationDao, never()).save(newBaseStation);
	}
	
	@Test
	void givenBaseStation_whenSave_thenNotValidQuantityException() {
		BaseStation baseStation = new BaseStation("BaseStation1", 3.0f, 7.0f, 5f);
		when(baseStationDao.findAll()).thenReturn(Arrays.asList(new BaseStation[99]));

		assertThrows(NotValidQuantityException.class, () -> baseStationService.save(baseStation));

		verify(baseStationDao, never()).save(baseStation);
	}

	@Test
	void givenBaseStation_whenUpdate_thenBaseStationWasUpdated() throws NotUniqueNameException {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);

		baseStationService.update(baseStation);

		verify(baseStationDao).save(baseStation);
	}

	@Test
	void givenBaseStation_whenDelete_thenBaseStationWasDeleted() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation expected = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		Optional<BaseStation> baseStation = Optional.of(expected);
		when(baseStationDao.findById(id)).thenReturn(baseStation);

		baseStationService.delete(id);

		verify(baseStationDao).delete(expected);
	}

	@Test
	void givenName_whenFindByName_thenBaseStationFound() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation expected = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		Optional<BaseStation> baseStation = Optional.of(expected);
		when(baseStationDao.findByName("Basestation1")).thenReturn(baseStation);

		BaseStation actual = baseStationService.findByName("Basestation1");

		assertEquals(expected, actual);
		verify(baseStationDao).findByName("Basestation1");
	}
}
