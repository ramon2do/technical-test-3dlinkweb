package com.threedlinkweb.petclinic.owner.domain;

import com.threedlinkweb.petclinic.shared.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

	@Column(name = "visit_date")
	private LocalDate date;

	@Column(name = "description")
	@NotBlank
	private String description;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public LocalDate getDate() { return date; }
	public void setDate(LocalDate date) { this.date = date; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public Pet getPet() { return pet; }
	public void setPet(Pet pet) { this.pet = pet; }
}
