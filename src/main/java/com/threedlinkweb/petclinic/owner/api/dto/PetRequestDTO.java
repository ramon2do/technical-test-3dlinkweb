package com.threedlinkweb.petclinic.owner.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PetRequestDTO(
	@NotBlank(message = "El nombre de la mascota es obligatorio")
	@Size(max = 30, message = "El nombre no puede exceder los 30 caracteres")
	String name,

	@NotNull(message = "La fecha de nacimiento es obligatoria")
	@PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
	LocalDate birthDate,

	@NotNull(message = "El tipo de mascota es obligatorio")
	Integer typeId,

	@NotNull(message = "El ID del propietario es obligatorio")
	Integer ownerId
) {}
