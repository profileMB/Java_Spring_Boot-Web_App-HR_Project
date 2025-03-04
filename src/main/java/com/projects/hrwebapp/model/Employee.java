package com.projects.hrwebapp.model;

import com.projects.hrwebapp.validation.OnCreate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Employee {
	
	private Integer id;
	
	@NotEmpty(message = "Veuillez renseigner votre prénom")
	private String firstName;
	
	@NotEmpty(message = "Veuillez renseigner votre nom")
	private String lastName;
	
	@Email(message = "Adresse e-mail invalide")
	@NotEmpty(message = "Veuillez renseigner votre e-mail")
	private String mail;

	@NotEmpty(message = "Veuillez définir un mot de passe", groups = OnCreate.class)
	@Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
	private String password;
	
}