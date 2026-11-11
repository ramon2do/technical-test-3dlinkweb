package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.OwnerRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.OwnerResponseDTO;
import com.threedlinkweb.petclinic.owner.application.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@Tag(name = "Owners", description = "API para la gestión de propietarios de mascotas")
public class OwnerController {

	private final OwnerService ownerService;

	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@GetMapping
	@Operation(summary = "Listar todos los propietarios", description = "Devuelve una lista de todos los propietarios registrados")
	public ResponseEntity<List<OwnerResponseDTO>> findAll() {
		return ResponseEntity.ok(ownerService.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un propietario por ID", description = "Devuelve los datos de un propietario específico")
	@ApiResponse(responseCode = "200", description = "Propietario encontrado")
	@ApiResponse(responseCode = "404", description = "Propietario no encontrado")
	public ResponseEntity<OwnerResponseDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(ownerService.findById(id));
	}

	@PostMapping
	@Operation(summary = "Crear un nuevo propietario", description = "Registra un nuevo propietario en el sistema")
	@ApiResponse(responseCode = "201", description = "Propietario creado exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
	public ResponseEntity<OwnerResponseDTO> create(@Valid @RequestBody OwnerRequestDTO requestDTO) {
		OwnerResponseDTO created = ownerService.create(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un propietario", description = "Actualiza los datos de un propietario existente")
	@ApiResponse(responseCode = "200", description = "Propietario actualizado exitosamente")
	@ApiResponse(responseCode = "404", description = "Propietario no encontrado")
	public ResponseEntity<OwnerResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody OwnerRequestDTO requestDTO) {
		return ResponseEntity.ok(ownerService.update(id, requestDTO));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un propietario", description = "Elimina un propietario del sistema")
	@ApiResponse(responseCode = "204", description = "Propietario eliminado exitosamente")
	@ApiResponse(responseCode = "404", description = "Propietario no encontrado")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		ownerService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
