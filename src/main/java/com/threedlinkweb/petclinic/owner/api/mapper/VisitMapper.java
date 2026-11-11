package com.threedlinkweb.petclinic.owner.api.mapper;

import com.threedlinkweb.petclinic.owner.api.dto.VisitRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.VisitResponseDTO;
import com.threedlinkweb.petclinic.owner.domain.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {

	public Visit toEntity(VisitRequestDTO dto) {
		Visit visit = new Visit();
		visit.setDate(dto.date());
		visit.setDescription(dto.description());
		return visit;
	}

	public VisitResponseDTO toDto(Visit visit) {
		return new VisitResponseDTO(
			visit.getId(),
			visit.getDate(),
			visit.getDescription(),
			visit.getPet() != null ? visit.getPet().getId() : null,
			visit.getPet() != null ? visit.getPet().getName() : null
		);
	}
}
