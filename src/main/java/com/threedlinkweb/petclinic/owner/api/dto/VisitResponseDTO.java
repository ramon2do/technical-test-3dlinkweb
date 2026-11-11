package com.threedlinkweb.petclinic.owner.api.dto;

import java.time.LocalDate;

public record VisitResponseDTO(
	Integer id,
	LocalDate date,
	String description,
	Integer petId,
	String petName
) {}
