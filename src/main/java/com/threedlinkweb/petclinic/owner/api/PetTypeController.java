package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.PetTypeResponseDTO;
import com.threedlinkweb.petclinic.owner.application.PetTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-types")
@Tag(name = "Pet Types", description = "API para consultar los tipos de mascota disponibles")
public class PetTypeController {

	private final PetTypeService petTypeService;

	public PetTypeController(PetTypeService petTypeService) {
		this.petTypeService = petTypeService;
	}

	@GetMapping
	@Operation(summary = "Listar todos los tipos de mascota", description = "Devuelve la lista de tipos de mascota disponibles (Perro, Gato, etc.)")
	@ApiResponse(responseCode = "200", description = "Lista de tipos de mascota")
	public ResponseEntity<List<PetTypeResponseDTO>> findAll() {
		return ResponseEntity.ok(petTypeService.findAll());
	}
}
