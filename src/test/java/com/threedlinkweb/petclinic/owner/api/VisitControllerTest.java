package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.VisitRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.VisitResponseDTO;
import com.threedlinkweb.petclinic.owner.application.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
class VisitControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VisitService visitService;

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	void createVisit_WhenValid_Returns201() throws Exception {
		VisitRequestDTO request = new VisitRequestDTO(LocalDate.now(), "Chequeo", 1);
		VisitResponseDTO response = new VisitResponseDTO(1, LocalDate.now(), "Chequeo", 1, "Leo");

		when(visitService.create(any(VisitRequestDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/visits")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.description").value("Chequeo"));
	}
}
