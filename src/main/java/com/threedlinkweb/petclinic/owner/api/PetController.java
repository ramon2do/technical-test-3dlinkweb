package com.threedlinkweb.petclinic.owner.api;

import com.threedlinkweb.petclinic.owner.api.dto.PetRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.PetResponseDTO;
import com.threedlinkweb.petclinic.owner.application.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
@Tag(name = "Pets", description = "API para la gestión de mascotas")
public class PetController {

	private final PetService petService;

	public PetController(PetService petService) {
		this.petService = petService;
	}

	@GetMapping("/owner/{ownerId}")
	@Operation(summary = "Listar mascotas por propietario", description = "Devuelve todas las mascotas asociadas a un propietario")
	@ApiResponse(responseCode = "200", description = "Lista de mascotas del propietario")
	public ResponseEntity<List<PetResponseDTO>> findByOwnerId(@PathVariable Integer ownerId) {
		return ResponseEntity.ok(petService.findByOwnerId(ownerId));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener una mascota por ID", description = "Devuelve los datos de una mascota específica")
	@ApiResponse(responseCode = "200", description = "Mascota encontrada")
	@ApiResponse(responseCode = "404", description = "Mascota no encontrada")
	public ResponseEntity<PetResponseDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(petService.findById(id));
	}

	@PostMapping
	@Operation(summary = "Crear una nueva mascota", description = "Registra una nueva mascota asociada a un propietario")
	@ApiResponse(responseCode = "201", description = "Mascota creada exitosamente")
	@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
	@ApiResponse(responseCode = "404", description = "Propietario o tipo de mascota no encontrado")
	public ResponseEntity<PetResponseDTO> create(@Valid @RequestBody PetRequestDTO requestDTO) {
		PetResponseDTO created = petService.create(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una mascota", description = "Actualiza los datos de una mascota existente")
	@ApiResponse(responseCode = "200", description = "Mascota actualizada exitosamente")
	@ApiResponse(responseCode = "404", description = "Mascota o tipo de mascota no encontrado")
	public ResponseEntity<PetResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody PetRequestDTO requestDTO) {
		return ResponseEntity.ok(petService.update(id, requestDTO));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar una mascota", description = "Elimina una mascota del sistema")
	@ApiResponse(responseCode = "204", description = "Mascota eliminada exitosamente")
	@ApiResponse(responseCode = "404", description = "Mascota no encontrada")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		petService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
