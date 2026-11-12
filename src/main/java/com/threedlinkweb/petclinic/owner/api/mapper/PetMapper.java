package com.threedlinkweb.petclinic.owner.api.mapper;

import com.threedlinkweb.petclinic.owner.api.dto.PetRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.PetResponseDTO;
import com.threedlinkweb.petclinic.owner.domain.Pet;
import com.threedlinkweb.petclinic.owner.domain.PetType;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

	public Pet toEntity(PetRequestDTO dto, PetType petType) {
		Pet pet = new Pet();
		pet.setName(dto.name());
		pet.setBirthDate(dto.birthDate());
		pet.setType(petType);
		return pet;
	}

	public PetResponseDTO toDto(Pet pet) {
		return new PetResponseDTO(
			pet.getId(),
			pet.getName(),
			pet.getBirthDate(),
			pet.getType() != null ? pet.getType().getName() : null,
			pet.getOwner() != null ? pet.getOwner().getId() : null
		);
	}
}
