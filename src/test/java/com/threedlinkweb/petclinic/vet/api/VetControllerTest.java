package com.threedlinkweb.petclinic.vet.api;

import com.threedlinkweb.petclinic.vet.api.dto.VetResponseDTO;
import com.threedlinkweb.petclinic.vet.application.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VetController.class)
class VetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VetService vetService;

	@Test
	void getAllVets_ReturnsList() throws Exception {
		VetResponseDTO vet = new VetResponseDTO(1, "James", "Carter", Set.of("surgery"));
		when(vetService.findAll()).thenReturn(List.of(vet));

		mockMvc.perform(get("/api/v1/vets"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(1))
			.andExpect(jsonPath("$[0].firstName").value("James"));
	}
}
