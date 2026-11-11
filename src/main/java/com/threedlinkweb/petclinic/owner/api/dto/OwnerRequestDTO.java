package com.threedlinkweb.petclinic.owner.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OwnerRequestDTO(
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 30, message = "El nombre no puede exceder los 30 caracteres")
	String firstName,

	@NotBlank(message = "El apellido es obligatorio")
	@Size(max = 30, message = "El apellido no puede exceder los 30 caracteres")
	String lastName,

	@NotBlank(message = "La dirección es obligatoria")
	@Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
	String address,

	@NotBlank(message = "La ciudad es obligatoria")
	@Size(max = 80, message = "La ciudad no puede exceder los 80 caracteres")
	String city,

	@NotBlank(message = "El teléfono es obligatorio")
	@Digits(fraction = 0, integer = 10, message = "El teléfono debe tener solo números y máximo 10 dígitos")
	String telephone
) {}
