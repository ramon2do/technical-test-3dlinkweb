package com.threedlinkweb.petclinic.vet.api;

import com.threedlinkweb.petclinic.vet.api.dto.VetResponseDTO;
import com.threedlinkweb.petclinic.vet.application.VetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vets")
@Tag(name = "Vets", description = "API para la consulta de veterinarios y especialidades")
public class VetController {

	private final VetService vetService;

	public VetController(VetService vetService) {
		this.vetService = vetService;
	}

	@GetMapping
	@Operation(summary = "Listar todos los veterinarios", description = "Devuelve una lista de todos los veterinarios con sus especialidades")
	@ApiResponse(responseCode = "200", description = "Lista de veterinarios")
	public ResponseEntity<List<VetResponseDTO>> findAll() {
		return ResponseEntity.ok(vetService.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un veterinario por ID", description = "Devuelve los datos de un veterinario específico")
	@ApiResponse(responseCode = "200", description = "Veterinario encontrado")
	@ApiResponse(responseCode = "404", description = "Veterinario no encontrado")
	public ResponseEntity<VetResponseDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(vetService.findById(id));
	}
}
