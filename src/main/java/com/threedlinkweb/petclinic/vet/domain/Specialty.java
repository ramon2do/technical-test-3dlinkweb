package com.threedlinkweb.petclinic.vet.domain;

import com.threedlinkweb.petclinic.shared.domain.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {
}
