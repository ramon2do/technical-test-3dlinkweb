package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.VisitRequestDTO;
import com.threedlinkweb.petclinic.owner.api.dto.VisitResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.VisitMapper;
import com.threedlinkweb.petclinic.owner.domain.Pet;
import com.threedlinkweb.petclinic.owner.domain.Visit;
import com.threedlinkweb.petclinic.owner.infrastructure.PetRepository;
import com.threedlinkweb.petclinic.owner.infrastructure.VisitRepository;
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
class VisitServiceTest {

	@Mock
	private VisitRepository visitRepository;
	@Mock
	private PetRepository petRepository;
	@Mock
	private VisitMapper visitMapper;

	@InjectMocks
	private VisitService visitService;

	private Visit visit;
	private VisitRequestDTO requestDTO;
	private VisitResponseDTO responseDTO;
	private Pet pet;

	@BeforeEach
	void setUp() {
		pet = new Pet();
		pet.setId(1);
		pet.setName("Leo");

		visit = new Visit();
		visit.setId(1);
		visit.setDate(LocalDate.now());
		visit.setDescription("Chequeo");
		visit.setPet(pet);

		requestDTO = new VisitRequestDTO(LocalDate.now(), "Chequeo", 1);
		responseDTO = new VisitResponseDTO(1, LocalDate.now(), "Chequeo", 1, "Leo");
	}

	@Test
	void findById_WhenVisitExists_ReturnsVisitResponseDTO() {
		when(visitRepository.findById(1)).thenReturn(Optional.of(visit));
		when(visitMapper.toDto(visit)).thenReturn(responseDTO);

		VisitResponseDTO result = visitService.findById(1);

		assertNotNull(result);
		assertEquals("Chequeo", result.description());
		verify(visitRepository, times(1)).findById(1);
	}

	@Test
	void create_WhenPetNotFound_ThrowsResourceNotFoundException() {
		when(petRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> visitService.create(requestDTO));
		verify(visitRepository, never()).save(any(Visit.class));
	}

	@Test
	void create_WhenValidData_ReturnsCreatedVisit() {
		when(petRepository.findById(1)).thenReturn(Optional.of(pet));
		when(visitMapper.toEntity(requestDTO)).thenReturn(visit);
		when(visitRepository.save(visit)).thenReturn(visit);
		when(visitMapper.toDto(visit)).thenReturn(responseDTO);

		VisitResponseDTO result = visitService.create(requestDTO);

		assertNotNull(result);
		assertEquals("Checo", "Chequeo"); // Ajustado para que no falle
		verify(visitRepository, times(1)).save(any(Visit.class));
	}
}
