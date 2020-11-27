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

import com.alsusp.wemakesoftware.dao.MobileStationDao;
import com.alsusp.wemakesoftware.exception.NotFoundException;
import com.alsusp.wemakesoftware.exception.NotUniqueNameException;
import com.alsusp.wemakesoftware.exception.NotValidQuantityException;
import com.alsusp.wemakesoftware.model.MobileStation;

@ExtendWith(MockitoExtension.class)
class MobileStationServiceTest {

	@Mock
	private MobileStationDao mobileStationDao;

	@InjectMocks
	private MobileStationService mobileStationService;

	@Test
	void whenFindAll_thenAllExistingMobileStationsFound() {
		List<MobileStation> expected = new ArrayList<>();
		MobileStation mobileStation1 = new MobileStation(UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2"), 3.0f,
				7.0f);
		MobileStation mobileStation2 = new MobileStation(UUID.fromString("33398908-8e48-48c3-82c0-954e1cd2a811"), 11.0f,
				7.0f);
		expected.add(mobileStation1);
		expected.add(mobileStation2);
		when(mobileStationDao.findAll()).thenReturn(expected);

		List<MobileStation> actual = mobileStationService.findAll();

		assertEquals(expected, actual);
		verify(mobileStationDao).findAll();
	}

	@Test
	void givenExistingId_whenFindOne_thenMobileStationFound() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation expected = new MobileStation(id, 3.0f, 7.0f);
		Optional<MobileStation> mobileStation = Optional.of(expected);
		when(mobileStationDao.findById(id)).thenReturn(mobileStation);

		MobileStation actual = mobileStationService.findOne(id);

		assertEquals(expected, actual);
		verify(mobileStationDao).findById(id);
	}

	@Test
	void givenNotExistingId_whenFindOne_thenNotFoundException() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		assertThrows(NotFoundException.class, () -> mobileStationService.findOne(id));
	}

	@Test
	void givenMobileStation_whenSave_thenMobileStationWasSaved() throws NotValidQuantityException {
		MobileStation mobileStation = new MobileStation(3.0f, 7.0f);

		mobileStationService.save(mobileStation);

		verify(mobileStationDao).save(mobileStation);
	}

	@Test
	void givenMobileStation_whenSave_thenNotValidQuantityException() {
		MobileStation mobileStation = new MobileStation(3.0f, 7.0f);
		when(mobileStationDao.findAll()).thenReturn(Arrays.asList(new MobileStation[99]));

		assertThrows(NotValidQuantityException.class, () -> mobileStationService.save(mobileStation));

		verify(mobileStationDao, never()).save(mobileStation);
	}

	@Test
	void givenMobileStation_whenUpdate_thenMobileStationWasUpdated() throws NotUniqueNameException {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);

		mobileStationService.update(mobileStation);

		verify(mobileStationDao).save(mobileStation);
	}

	@Test
	void givenMobileStation_whenDelete_thenMobileStationWasDeleted() {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation expected = new MobileStation(id, 3.0f, 7.0f);
		Optional<MobileStation> mobileStation = Optional.of(expected);
		when(mobileStationDao.findById(id)).thenReturn(mobileStation);

		mobileStationService.delete(id);

		verify(mobileStationDao).delete(expected);
	}
}
