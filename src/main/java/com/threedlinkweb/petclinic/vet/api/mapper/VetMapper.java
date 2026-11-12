package com.threedlinkweb.petclinic.vet.api.mapper;

import com.threedlinkweb.petclinic.vet.api.dto.VetResponseDTO;
import com.threedlinkweb.petclinic.vet.domain.Specialty;
import com.threedlinkweb.petclinic.vet.domain.Vet;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VetMapper {

	public VetResponseDTO toDto(Vet vet) {
		return new VetResponseDTO(
			vet.getId(),
			vet.getFirstName(),
			vet.getLastName(),
			vet.getSpecialties().stream()
				.map(Specialty::getName)
				.collect(Collectors.toSet())
		);
	}
}
