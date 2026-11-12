package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.OwnerRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.OwnerResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.OwnerMapper;
import com.threedlinkweb.petclinic.owner.domain.Owner;
import com.threedlinkweb.petclinic.owner.infrastructure.OwnerRepository;
import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;

	public OwnerService(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerMapper = ownerMapper;
	}

	@Transactional(readOnly = true)
	public List<OwnerResponseDTO> findAll() {
		return ownerRepository.findAll()
			.stream()
			.map(ownerMapper::toDto)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public OwnerResponseDTO findById(Integer id) {
		Owner owner = ownerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Owner no encontrado con ID: " + id));
		return ownerMapper.toDto(owner);
	}

	@Transactional
	public OwnerResponseDTO create(OwnerRequestDTO dto) {
		Owner owner = ownerMapper.toEntity(dto);
		Owner savedOwner = ownerRepository.save(owner);
		return ownerMapper.toDto(savedOwner);
	}

	@Transactional
	public OwnerResponseDTO update(Integer id, OwnerRequestDTO dto) {
		Owner existingOwner = ownerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Owner no encontrado con ID: " + id));

		// Actualizamos los campos
		existingOwner.setFirstName(dto.firstName());
		existingOwner.setLastName(dto.lastName());
		existingOwner.setAddress(dto.address());
		existingOwner.setCity(dto.city());
		existingOwner.setTelephone(dto.telephone());

		Owner updatedOwner = ownerRepository.save(existingOwner);
		return ownerMapper.toDto(updatedOwner);
	}

	@Transactional
	public void delete(Integer id) {
		if (!ownerRepository.existsById(id)) {
			throw new ResourceNotFoundException("Owner no encontrado con ID: " + id);
		}
		ownerRepository.deleteById(id);
	}
}
