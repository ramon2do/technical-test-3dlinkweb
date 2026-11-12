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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

	@Mock
	private PetRepository petRepository;
	@Mock
	private OwnerRepository ownerRepository;
	@Mock
	private PetTypeRepository petTypeRepository;
	@Mock
	private PetMapper petMapper;

	@InjectMocks
	private PetService petService;

	private Pet pet;
	private PetRequestDTO requestDTO;
	private PetResponseDTO responseDTO;
	private Owner owner;
	private PetType petType;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setId(1);

		petType = new PetType();
		petType.setId(2);

		pet = new Pet();
		pet.setId(1);
		pet.setName("Leo");
		pet.setOwner(owner);
		pet.setType(petType);

		requestDTO = new PetRequestDTO("Leo", LocalDate.now(), 2, 1);
		responseDTO = new PetResponseDTO(1, "Leo", LocalDate.now(), "dog", 1);
	}

	@Test
	void findById_WhenPetExists_ReturnsPetResponseDTO() {
		when(petRepository.findById(1)).thenReturn(Optional.of(pet));
		when(petMapper.toDto(pet)).thenReturn(responseDTO);

		PetResponseDTO result = petService.findById(1);

		assertNotNull(result);
		assertEquals("Leo", result.name());
		verify(petRepository, times(1)).findById(1);
	}

	@Test
	void findById_WhenPetDoesNotExist_ThrowsResourceNotFoundException() {
		when(petRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> petService.findById(99));
	}

	@Test
	void create_WhenValidData_ReturnsCreatedPet() {
		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
		when(petTypeRepository.findById(2)).thenReturn(Optional.of(petType));
		when(petMapper.toEntity(requestDTO, petType)).thenReturn(pet);
		when(petRepository.save(pet)).thenReturn(pet);
		when(petMapper.toDto(pet)).thenReturn(responseDTO);

		PetResponseDTO result = petService.create(requestDTO);

		assertNotNull(result);
		assertEquals("Leo", result.name());
		verify(petRepository, times(1)).save(any(Pet.class));
	}

	@Test
	void create_WhenOwnerNotFound_ThrowsResourceNotFoundException() {
		when(ownerRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> petService.create(requestDTO));
		verify(petRepository, never()).save(any(Pet.class));
	}
}
