package com.threedlinkweb.petclinic.owner.api.dto;

public record OwnerResponseDTO(
	Integer id,
	String firstName,
	String lastName,
	String address,
	String city,
	String telephone
) {}
