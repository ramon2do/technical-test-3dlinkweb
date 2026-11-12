package com.threedlinkweb.petclinic.vet.application;

import com.threedlinkweb.petclinic.shared.exception.ResourceNotFoundException;
import com.threedlinkweb.petclinic.vet.api.dto.VetResponseDTO;
import com.threedlinkweb.petclinic.vet.api.mapper.VetMapper;
import com.threedlinkweb.petclinic.vet.domain.Vet;
import com.threedlinkweb.petclinic.vet.infrastructure.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetServiceTest {

	@Mock
	private VetRepository vetRepository;
	@Mock
	private VetMapper vetMapper;

	@InjectMocks
	private VetService vetService;

	private Vet vet;
	private VetResponseDTO responseDTO;

	@BeforeEach
	void setUp() {
		vet = new Vet();
		vet.setId(1);
		vet.setFirstName("James");
		vet.setLastName("Carter");

		responseDTO = new VetResponseDTO(1, "James", "Carter", Set.of("radiology"));
	}

	@Test
	void findById_WhenVetExists_ReturnsVetResponseDTO() {
		when(vetRepository.findById(1)).thenReturn(Optional.of(vet));
		when(vetMapper.toDto(vet)).thenReturn(responseDTO);

		VetResponseDTO result = vetService.findById(1);

		assertNotNull(result);
		assertEquals("James", result.firstName());
		verify(vetRepository, times(1)).findById(1);
	}

	@Test
	void findById_WhenVetDoesNotExist_ThrowsResourceNotFoundException() {
		when(vetRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> vetService.findById(99));
	}
}
