package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.OwnerRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.OwnerResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.OwnerMapper;
import com.threedlinkweb.petclinic.owner.domain.Owner;
import com.threedlinkweb.petclinic.owner.infrastructure.OwnerRepository;
import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

	@Mock
	private OwnerRepository ownerRepository;

	@Mock
	private OwnerMapper ownerMapper;

	@InjectMocks
	private OwnerService ownerService;

	private Owner owner;
	private OwnerRequestDTO requestDTO;
	private OwnerResponseDTO responseDTO;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");

		requestDTO = new OwnerRequestDTO("John", "Doe", "123 Main St", "Springfield", "1234567890");
		responseDTO = new OwnerResponseDTO(1, "John", "Doe", "123 Main St", "Springfield", "1234567890");
	}

	@Test
	void findById_WhenOwnerExists_ReturnsOwnerResponseDTO() {
		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
		when(ownerMapper.toDto(owner)).thenReturn(responseDTO);

		OwnerResponseDTO result = ownerService.findById(1);

		assertNotNull(result);
		assertEquals("John", result.firstName());
		verify(ownerRepository, times(1)).findById(1);
	}

	@Test
	void findById_WhenOwnerDoesNotExist_ThrowsResourceNotFoundException() {
		when(ownerRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> ownerService.findById(99));
		verify(ownerRepository, times(1)).findById(99);
	}

	@Test
	void create_WhenValidData_ReturnsCreatedOwner() {
		when(ownerMapper.toEntity(requestDTO)).thenReturn(owner);
		when(ownerRepository.save(owner)).thenReturn(owner);
		when(ownerMapper.toDto(owner)).thenReturn(responseDTO);

		OwnerResponseDTO result = ownerService.create(requestDTO);

		assertNotNull(result);
		assertEquals(1, result.id());
		verify(ownerRepository, times(1)).save(any(Owner.class));
	}

	@Test
	void delete_WhenOwnerDoesNotExist_ThrowsResourceNotFoundException() {
		when(ownerRepository.existsById(99)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> ownerService.delete(99));
		verify(ownerRepository, never()).deleteById(anyInt());
	}
}
