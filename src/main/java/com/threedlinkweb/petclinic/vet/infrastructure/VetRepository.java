package com.threedlinkweb.petclinic.vet.infrastructure;

import com.threedlinkweb.petclinic.vet.domain.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
}
