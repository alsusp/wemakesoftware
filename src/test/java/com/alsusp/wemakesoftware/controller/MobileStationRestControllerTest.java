package com.alsusp.wemakesoftware.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alsusp.wemakesoftware.model.BaseStation;
import com.alsusp.wemakesoftware.model.MobileStation;
import com.alsusp.wemakesoftware.model.Report;
import com.alsusp.wemakesoftware.service.MobileStationService;
import com.alsusp.wemakesoftware.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class MobileStationRestControllerTest {

	private static final String URI = "/api/mobileStations/{0}";

	@Mock
	private MobileStationService mobileStationService;

	@Mock
	private ReportService reportService;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new MobileStationRestController(mobileStationService, reportService))
				.build();
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	void whenFindAll_thenGotMobileStations() throws JsonProcessingException, Exception {
		List<MobileStation> expected = new ArrayList<>();
		MobileStation mobileStation1 = new MobileStation(UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2"), 3.0f,
				7.0f);
		MobileStation mobileStation2 = new MobileStation(UUID.fromString("33398908-8e48-48c3-82c0-954e1cd2a811"), 11.0f,
				7.0f);
		expected.add(mobileStation1);
		expected.add(mobileStation2);
		when(mobileStationService.findAll()).thenReturn(expected);

		mockMvc.perform(get("/api/mobileStations").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(expected))).andDo(print());
	}

	@Test
	void givenId_whenGetMobileStation_thenGotMobileStation() throws JsonProcessingException, Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation expected = new MobileStation(id, 3.0f, 7.0f);
		when(mobileStationService.findOne(id)).thenReturn(expected);

		mockMvc.perform(get(URI, expected.getId()).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(expected))).andDo(print());
	}

	@Test
	void givenMobileStation_whenCreate_thenMobileStationWasCreated() throws JsonProcessingException, Exception {
		MobileStation expected = new MobileStation(3.0f, 7.0f);

		mockMvc.perform(
				post("/api/mobileStations").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(expected)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/api/mobileStations/")).andDo(print());

		verify(mobileStationService).save(expected);
	}

	@Test
	void givenMobileStation_whenUpdate_thenMobileStationWasUpdated() throws JsonProcessingException, Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation expected = new MobileStation(id, 3.0f, 7.0f);
		when(mobileStationService.findOne(expected.getId())).thenReturn(expected);

		mockMvc.perform(
				put(URI, expected.getId()).contentType(APPLICATION_JSON).content(mapper.writeValueAsString(expected)))
				.andExpect(status().isOk()).andDo(print());

		verify(mobileStationService).update(expected);
	}

	@Test
	void givenId_whenDelete_thenMobileStationWasDeleted() throws Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		mockMvc.perform(delete(URI, id).contentType(APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());

		verify(mobileStationService).delete(id);
	}

	@Test
	void givenId_whenGetMobileStationLocation_thenGotLocation() throws JsonProcessingException, Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		Report report = new Report(id, baseStation, mobileStation, 5.0f, LocalDateTime.now());
		MobileStationLocationDTO expected = new MobileStationLocationDTO(mobileStation.getId(),
				mobileStation.getLastKnownX(), mobileStation.getLastKnownY(), report.getDistance());
		when(mobileStationService.findOne(id)).thenReturn(mobileStation);
		when(reportService.getLastReportForMobileStation(mobileStation)).thenReturn(report);

		mockMvc.perform(get("/api/mobileStations/location/{0}", expected.getMobileId()).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(expected)))
				.andDo(print());
	}
}
