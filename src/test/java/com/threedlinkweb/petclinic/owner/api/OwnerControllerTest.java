package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.OwnerRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.OwnerResponseDTO;
import com.threedlinkweb.petclinic.owner.application.OwnerService;
import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerService ownerService;

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	void getAllOwners_ReturnsListOfOwners() throws Exception {
		OwnerResponseDTO owner = new OwnerResponseDTO(1, "John", "Doe", "123 Main St", "Springfield", "1234567890");
		when(ownerService.findAll()).thenReturn(List.of(owner));

		mockMvc.perform(get("/api/v1/owners"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(1))
			.andExpect(jsonPath("$[0].firstName").value("John"));
	}

	@Test
	void getOwnerById_WhenExists_ReturnsOwner() throws Exception {
		OwnerResponseDTO owner = new OwnerResponseDTO(1, "John", "Doe", "123 Main St", "Springfield", "1234567890");
		when(ownerService.findById(1)).thenReturn(owner);

		mockMvc.perform(get("/api/v1/owners/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	void getOwnerById_WhenNotExists_Returns404() throws Exception {
		when(ownerService.findById(99)).thenThrow(new ResourceNotFoundException("Owner no encontrado"));

		mockMvc.perform(get("/api/v1/owners/99"))
			.andExpect(status().isNotFound());
	}

	@Test
	void createOwner_WhenValidData_Returns201() throws Exception {
		OwnerRequestDTO request = new OwnerRequestDTO("John", "Doe", "123 Main St", "Springfield", "1234567890");
		OwnerResponseDTO response = new OwnerResponseDTO(1, "John", "Doe", "123 Main St", "Springfield", "1234567890");

		when(ownerService.create(any(OwnerRequestDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/owners")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(request))) // Cambiado a jsonMapper
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void createOwner_WhenInvalidData_Returns400() throws Exception {
		OwnerRequestDTO request = new OwnerRequestDTO("", "", "", "", ""); // Datos inválidos

		mockMvc.perform(post("/api/v1/owners")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(request))) // Cambiado a jsonMapper
			.andExpect(status().isBadRequest());
	}
}
