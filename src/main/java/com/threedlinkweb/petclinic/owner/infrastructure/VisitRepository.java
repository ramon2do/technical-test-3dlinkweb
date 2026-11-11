package com.threedlinkweb.petclinic.owner.infrastructure;

import com.threedlinkweb.petclinic.owner.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
	// Buscar visitas por el ID de la mascota
	List<Visit> findByPetId(Integer petId);
}
