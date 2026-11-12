package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.PetRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.PetResponseDTO;
import com.threedlinkweb.petclinic.owner.application.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PetService petService;

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	void getPetsByOwner_ReturnsList() throws Exception {
		PetResponseDTO pet = new PetResponseDTO(1, "Leo", LocalDate.now(), "cat", 1);
		when(petService.findByOwnerId(1)).thenReturn(List.of(pet));

		mockMvc.perform(get("/api/v1/pets/owner/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(1))
			.andExpect(jsonPath("$[0].name").value("Leo"));
	}

	@Test
	void createPet_WhenValid_Returns201() throws Exception {
		PetRequestDTO request = new PetRequestDTO("Rocky", LocalDate.now(), 1, 1);
		PetResponseDTO response = new PetResponseDTO(2, "Rocky", LocalDate.now(), "dog", 1);

		when(petService.create(any(PetRequestDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("Rocky"));
	}
}
