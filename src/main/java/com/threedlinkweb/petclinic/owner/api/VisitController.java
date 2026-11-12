package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.VisitRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.VisitResponseDTO;
import com.threedlinkweb.petclinic.owner.application.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@Tag(name = "Visits", description = "API para la gestión de visitas médicas")
public class VisitController {

	private final VisitService visitService;

	public VisitController(VisitService visitService) {
		this.visitService = visitService;
	}

	@GetMapping("/pet/{petId}")
	@Operation(summary = "Listar visitas por mascota", description = "Devuelve todas las visitas asociadas a una mascota")
	@ApiResponse(responseCode = "200", description = "Lista de visitas de la mascota")
	public ResponseEntity<List<VisitResponseDTO>> findByPetId(@PathVariable Integer petId) {
		return ResponseEntity.ok(visitService.findByPetId(petId));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener una visita por ID", description = "Devuelve los datos de una visita específica")
	@ApiResponse(responseCode = "200", description = "Visita encontrada")
	@ApiResponse(responseCode = "404", description = "Visita no encontrada")
	public ResponseEntity<VisitResponseDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(visitService.findById(id));
	}

	@PostMapping
	@Operation(summary = "Registrar una nueva visita", description = "Crea una nueva visita médica para una mascota")
	@ApiResponse(responseCode = "201", description = "Visita creada exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
	@ApiResponse(responseCode = "404", description = "Mascota no encontrada")
	public ResponseEntity<VisitResponseDTO> create(@Valid @RequestBody VisitRequestDTO requestDTO) {
		VisitResponseDTO created = visitService.create(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar una visita", description = "Elimina una visita del sistema")
	@ApiResponse(responseCode = "204", description = "Visita eliminada exitosamente")
	@ApiResponse(responseCode = "404", description = "Visita no encontrada")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		visitService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
