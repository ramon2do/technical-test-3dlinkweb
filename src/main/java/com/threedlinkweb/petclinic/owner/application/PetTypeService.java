package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.PetTypeResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.PetTypeMapper;
import com.threedlinkweb.petclinic.owner.infrastructure.PetTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetTypeService {

	private final PetTypeRepository petTypeRepository;
	private final PetTypeMapper petTypeMapper;

	public PetTypeService(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
		this.petTypeRepository = petTypeRepository;
		this.petTypeMapper = petTypeMapper;
	}

	@Transactional(readOnly = true)
	public List<PetTypeResponseDTO> findAll() {
		return petTypeRepository.findAll()
			.stream()
			.map(petTypeMapper::toDto)
			.toList();
	}
}
