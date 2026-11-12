package com.threedlinkweb.petclinic.owner.domain;

import com.threedlinkweb.petclinic.shared.domain.NamedEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pet")
	private List<Visit> visits = new ArrayList<>();

	public LocalDate getBirthDate() { return birthDate; }
	public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

	public PetType getType() { return type; }
	public void setType(PetType type) { this.type = type; }

	public Owner getOwner() { return owner; }
	public void setOwner(Owner owner) { this.owner = owner; }

	public List<Visit> getVisits() { return visits; }
	public void setVisits(List<Visit> visits) { this.visits = visits; }

	public void addVisit(Visit visit) {
		this.visits.add(visit);
		visit.setPet(this);
	}
}
