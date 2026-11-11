package com.threedlinkweb.petclinic.owner.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OwnerRequestDTO {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 30, message = "El nombre no puede exceder los 30 caracteres")
	private String firstName;

	@NotBlank(message = "El apellido es obligatorio")
	@Size(max = 30, message = "El apellido no puede exceder los 30 caracteres")
	private String lastName;

	@NotBlank(message = "La dirección es obligatoria")
	@Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
	private String address;

	@NotBlank(message = "La ciudad es obligatoria")
	@Size(max = 80, message = "La ciudad no puede exceder los 80 caracteres")
	private String city;

	@NotBlank(message = "El teléfono es obligatorio")
	@Digits(fraction = 0, integer = 10, message = "El teléfono debe tener solo números y máximo 10 dígitos")
	private String telephone;

	// Getters y Setters
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public String getTelephone() { return telephone; }
	public void setTelephone(String telephone) { this.telephone = telephone; }
}
