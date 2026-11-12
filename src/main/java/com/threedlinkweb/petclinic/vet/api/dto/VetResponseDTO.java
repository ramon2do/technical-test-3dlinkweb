package com.threedlinkweb.petclinic.vet.api.dto;

import java.util.Set;

public record VetResponseDTO(
	Integer id,
	String firstName,
	String lastName,
	Set<String> specialties
) {}
