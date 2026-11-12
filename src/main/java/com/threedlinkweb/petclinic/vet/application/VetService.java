package com.threedlinkweb.petclinic.vet.application;

import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import com.threedlinkweb.petclinic.vet.api.dto.VetResponseDTO;
import com.threedlinkweb.petclinic.vet.api.mapper.VetMapper;
import com.threedlinkweb.petclinic.vet.domain.Vet;
import com.threedlinkweb.petclinic.vet.infrastructure.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VetService {

	private final VetRepository vetRepository;
	private final VetMapper vetMapper;

	public VetService(VetRepository vetRepository, VetMapper vetMapper) {
		this.vetRepository = vetRepository;
		this.vetMapper = vetMapper;
	}

	@Transactional(readOnly = true)
	public List<VetResponseDTO> findAll() {
		return vetRepository.findAll()
			.stream()
			.map(vetMapper::toDto)
			.toList();
	}

	@Transactional(readOnly = true)
	public VetResponseDTO findById(Integer id) {
		Vet vet = vetRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con ID: " + id));
		return vetMapper.toDto(vet);
	}
}
