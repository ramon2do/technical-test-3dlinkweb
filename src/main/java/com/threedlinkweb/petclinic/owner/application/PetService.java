package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.PetRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.PetResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.PetMapper;
import com.threedlinkweb.petclinic.owner.domain.Owner;
import com.threedlinkweb.petclinic.owner.domain.Pet;
import com.threedlinkweb.petclinic.owner.domain.PetType;
import com.threedlinkweb.petclinic.owner.infrastructure.OwnerRepository;
import com.threedlinkweb.petclinic.owner.infrastructure.PetRepository;
import com.threedlinkweb.petclinic.owner.infrastructure.PetTypeRepository;
import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final PetTypeRepository petTypeRepository;
	private final PetMapper petMapper;

	public PetService(PetRepository petRepository,
					  OwnerRepository ownerRepository,
					  PetTypeRepository petTypeRepository,
					  PetMapper petMapper) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.petTypeRepository = petTypeRepository;
		this.petMapper = petMapper;
	}

	@Transactional(readOnly = true)
	public List<PetResponseDTO> findByOwnerId(Integer ownerId) {
		return petRepository.findByOwnerId(ownerId)
			.stream()
			.map(petMapper::toDto)
			.toList();
	}

	@Transactional(readOnly = true)
	public PetResponseDTO findById(Integer id) {
		Pet pet = petRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + id));
		return petMapper.toDto(pet);
	}

	@Transactional
	public PetResponseDTO create(PetRequestDTO dto) {
		Owner owner = ownerRepository.findById(dto.ownerId())
			.orElseThrow(() -> new ResourceNotFoundException("Propietario no encontrado con ID: " + dto.ownerId()));

		PetType petType = petTypeRepository.findById(dto.typeId())
			.orElseThrow(() -> new ResourceNotFoundException("Tipo de mascota no encontrado con ID: " + dto.typeId()));

		Pet pet = petMapper.toEntity(dto, petType);
		pet.setOwner(owner);

		Pet savedPet = petRepository.save(pet);
		return petMapper.toDto(savedPet);
	}

	@Transactional
	public PetResponseDTO update(Integer id, PetRequestDTO dto) {
		Pet existingPet = petRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + id));

		PetType petType = petTypeRepository.findById(dto.typeId())
			.orElseThrow(() -> new ResourceNotFoundException("Tipo de mascota no encontrado con ID: " + dto.typeId()));

		existingPet.setName(dto.name());
		existingPet.setBirthDate(dto.birthDate());
		existingPet.setType(petType);

		Pet updatedPet = petRepository.save(existingPet);
		return petMapper.toDto(updatedPet);
	}

	@Transactional
	public void delete(Integer id) {
		if (!petRepository.existsById(id)) {
			throw new ResourceNotFoundException("Mascota no encontrada con ID: " + id);
		}
		petRepository.deleteById(id);
	}
}
