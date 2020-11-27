package com.alsusp.wemakesoftware.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
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
import com.alsusp.wemakesoftware.service.BaseStationService;
import com.alsusp.wemakesoftware.service.MobileStationService;
import com.alsusp.wemakesoftware.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class BaseStationRestControllerTest {

	private static final String URI = "/api/baseStations/{0}";

	@Mock
	private BaseStationService baseStationService;
	
	@Mock
	private MobileStationService mobileStationService;
	
	@Mock
	private ReportService reportService;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(new BaseStationRestController(baseStationService, mobileStationService, reportService)).build();
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	void whenFindAll_thenGotBaseStations() throws JsonProcessingException, Exception {
		List<BaseStation> expected = new ArrayList<>();
		BaseStation baseStation1 = new BaseStation(UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2"),
				"BaseStation1", 3.0f, 7.0f, 5f);
		BaseStation baseStation2 = new BaseStation(UUID.fromString("33398908-8e48-48c3-82c0-954e1cd2a811"),
				"BaseStation2", 11.0f, 7.0f, 5.0f);
		expected.add(baseStation1);
		expected.add(baseStation2);
		when(baseStationService.findAll()).thenReturn(expected);
		
		mockMvc.perform(get("/api/baseStations").contentType(APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(mapper.writeValueAsString(expected)))
		.andDo(print());
	}

	@Test
	void givenId_whenGetBaseStation_thenGotBaseStation() throws JsonProcessingException, Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation expected = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		when(baseStationService.findOne(id)).thenReturn(expected);
		
		mockMvc.perform(get(URI, expected.getId()).contentType(APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(mapper.writeValueAsString(expected)))
		.andDo(print());
	}

	@Test
	void givenBaseStation_whenCreate_thenBaseStationWasCreated() throws JsonProcessingException, Exception {
		BaseStation expected = new BaseStation("BaseStation1", 3.0f, 7.0f, 5f);

		mockMvc.perform(post("/api/baseStations").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(expected)))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", "http://localhost/api/baseStations/"))
		.andDo(print());

		verify(baseStationService).save(expected);
	}

	@Test
	void givenBaseStation_whenUpdate_thenBaseStationWasUpdated() throws JsonProcessingException, Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		BaseStation expected = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		when(baseStationService.findOne(expected.getId())).thenReturn(expected);

		mockMvc.perform(put(URI, expected.getId()).contentType(APPLICATION_JSON).content(mapper.writeValueAsString(expected)))
		.andExpect(status().isOk())
		.andDo(print());
		
		verify(baseStationService).update(expected);
	}

	@Test
	void givenId_whenDelete_thenBaseStationWasDeleted() throws Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		mockMvc.perform(delete(URI, id).contentType(APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(print());
		
		verify(baseStationService).delete(id);
	}
	
	@Test
	void givenBaseStationReportDTO_whenReport_thenReportWasCreated() throws Exception {
		UUID id = UUID.fromString("c0ccdde8-7a43-4a67-8618-96c2562478d2");
		ReportDTO report1 = new ReportDTO(id, 5.0f, LocalDateTime.now());
		ReportDTO report2 = new ReportDTO(id, 2.0f, LocalDateTime.now());
		List<ReportDTO> reports = new ArrayList<>();
		reports.add(report1);
		reports.add(report2);
		BaseStationReportDTO expected = new BaseStationReportDTO(id, reports);
		BaseStation baseStation = new BaseStation(id, "BaseStation1", 3.0f, 7.0f, 5f);
		MobileStation mobileStation = new MobileStation(id, 3.0f, 7.0f);
		when(baseStationService.findOne(id)).thenReturn(baseStation);
		when(mobileStationService.findOne(id)).thenReturn(mobileStation);
		
		mockMvc.perform(post("/api/baseStations/reports").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(expected)))
		.andExpect(status().isCreated())
		.andDo(print());
	}
}
