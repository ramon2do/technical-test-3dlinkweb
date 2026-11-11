package com.threedlinkweb.petclinic.owner.api.mapper;

import com.threedlinkweb.petclinic.owner.api.dto.OwnerRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.OwnerResponseDTO;
import com.threedlinkweb.petclinic.owner.domain.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

	// Convierte de DTO (Request) a Entidad
	public Owner toEntity(OwnerRequestDTO dto) {
		Owner owner = new Owner();
		owner.setFirstName(dto.firstName());
		owner.setLastName(dto.lastName());
		owner.setAddress(dto.address());
		owner.setCity(dto.city());
		owner.setTelephone(dto.telephone());
		return owner;
	}

	// Convierte de Entidad a DTO (Response)
	public OwnerResponseDTO toDto(Owner owner) {
		return new OwnerResponseDTO(
			owner.getId(),
			owner.getFirstName(),
			owner.getLastName(),
			owner.getAddress(),
			owner.getCity(),
			owner.getTelephone()
		);
	}
}
