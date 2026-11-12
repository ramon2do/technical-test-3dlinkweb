package com.threedlinkweb.petclinic.owner.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VisitRequestDTO(
	@NotNull(message = "La fecha de la visita es obligatoria")
	LocalDate date,

	@NotBlank(message = "La descripción es obligatoria")
	@Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
	String description,

	@NotNull(message = "El ID de la mascota es obligatorio")
	Integer petId
) {}
