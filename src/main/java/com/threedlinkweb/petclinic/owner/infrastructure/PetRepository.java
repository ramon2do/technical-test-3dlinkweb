package com.threedlinkweb.petclinic.owner.infrastructure;

import com.threedlinkweb.petclinic.owner.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
	// Método para buscar mascotas por el ID del dueño
	List<Pet> findByOwnerId(Integer ownerId);
}
