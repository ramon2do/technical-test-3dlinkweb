package com.threedlinkweb.petclinic.owner.api.dto;

import java.time.LocalDate;

public record PetResponseDTO(
	Integer id,
	String name,
	LocalDate birthDate,
	String typeName,
	Integer ownerId
) {}
