package com.threedlinkweb.petclinic.owner.domain;

import com.threedlinkweb.petclinic.shared.domain.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner extends Person {

	@Column(name = "address")
	@NotBlank
	@Size(max = 255)
	private String address;

	@Column(name = "city")
	@NotBlank
	@Size(max = 80)
	private String city;

	@Column(name = "telephone")
	@NotBlank
	@Pattern(regexp = "^\\d{7,10}$", message = "El teléfono debe tener entre 7 y 10 dígitos numéricos")
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
	private List<Pet> pets = new ArrayList<>();

	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public String getTelephone() { return telephone; }
	public void setTelephone(String telephone) { this.telephone = telephone; }
	public List<Pet> getPets() { return pets; }
	public void setPets(List<Pet> pets) { this.pets = pets; }

	public void addPet(Pet pet) {
		this.pets.add(pet);
		pet.setOwner(this);
	}
}
