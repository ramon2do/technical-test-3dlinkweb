package com.threedlinkweb.petclinic.owner.application;

import com.threedlinkweb.petclinic.owner.api.dto.PetTypeResponseDTO;
import com.threedlinkweb.petclinic.owner.api.mapper.PetTypeMapper;
import com.threedlinkweb.petclinic.owner.domain.PetType;
import com.threedlinkweb.petclinic.owner.infrastructure.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetTypeServiceTest {

	@Mock
	private PetTypeRepository petTypeRepository;
	@Mock
	private PetTypeMapper petTypeMapper;

	@InjectMocks
	private PetTypeService petTypeService;

	private PetType petType;
	private PetTypeResponseDTO responseDTO;

	@BeforeEach
	void setUp() {
		petType = new PetType();
		petType.setId(1);
		petType.setName("cat");

		responseDTO = new PetTypeResponseDTO(1, "cat");
	}

	@Test
	void findAll_ReturnsListOfPetTypes() {
		when(petTypeRepository.findAll()).thenReturn(List.of(petType));
		when(petTypeMapper.toDto(petType)).thenReturn(responseDTO);

		List<PetTypeResponseDTO> result = petTypeService.findAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("cat", result.get(0).name());
		verify(petTypeRepository, times(1)).findAll();
	}
}
