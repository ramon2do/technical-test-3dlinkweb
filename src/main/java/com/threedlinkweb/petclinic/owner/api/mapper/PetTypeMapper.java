package com.threedlinkweb.petclinic.owner.api.mapper;

import com.threedlinkweb.petclinic.owner.api.dto.PetTypeResponseDTO;
import com.threedlinkweb.petclinic.owner.domain.PetType;
import org.springframework.stereotype.Component;

@Component
public class PetTypeMapper {

	public PetTypeResponseDTO toDto(PetType petType) {
		return new PetTypeResponseDTO(
			petType.getId(),
			petType.getName()
		);
	}
}
