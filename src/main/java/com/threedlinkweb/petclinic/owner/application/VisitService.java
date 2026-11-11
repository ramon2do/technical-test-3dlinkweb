package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.VisitRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.VisitResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.VisitMapper;
import com.threedlinkweb.petclinic.owner.domain.Pet;
import com.threedlinkweb.petclinic.owner.domain.Visit;
import com.threedlinkweb.petclinic.owner.infrastructure.PetRepository;
import com.threedlinkweb.petclinic.owner.infrastructure.VisitRepository;
import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VisitService {

	private final VisitRepository visitRepository;
	private final PetRepository petRepository;
	private final VisitMapper visitMapper;

	public VisitService(VisitRepository visitRepository,
						PetRepository petRepository,
						VisitMapper visitMapper) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
		this.visitMapper = visitMapper;
	}

	@Transactional(readOnly = true)
	public List<VisitResponseDTO> findByPetId(Integer petId) {
		return visitRepository.findByPetId(petId)
			.stream()
			.map(visitMapper::toDto)
			.toList();
	}

	@Transactional(readOnly = true)
	public VisitResponseDTO findById(Integer id) {
		Visit visit = visitRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con ID: " + id));
		return visitMapper.toDto(visit);
	}

	@Transactional
	public VisitResponseDTO create(VisitRequestDTO dto) {
		Pet pet = petRepository.findById(dto.petId())
			.orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + dto.petId()));

		Visit visit = visitMapper.toEntity(dto);
		visit.setPet(pet);

		Visit savedVisit = visitRepository.save(visit);
		return visitMapper.toDto(savedVisit);
	}

	@Transactional
	public void delete(Integer id) {
		if (!visitRepository.existsById(id)) {
			throw new ResourceNotFoundException("Visita no encontrada con ID: " + id);
		}
		visitRepository.deleteById(id);
	}
}
