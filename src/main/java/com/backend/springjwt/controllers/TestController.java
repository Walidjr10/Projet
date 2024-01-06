package com.backend.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Content.";
	}

	@GetMapping("/directeur")
	@PreAuthorize("hasRole('DIRECTEUR')")
	public String directeurAccess() {
		return "Directeur Content.";
	}

	@GetMapping("/secretaire")
	@PreAuthorize("hasRole('SECRETAIRE')")
	public String secretaireAccess() {
		return "Secretaire Board.";
	}

	@GetMapping("/etudiant")
	@PreAuthorize("hasRole('ETUDIANT')")
	public String etudiantAccess() {
		return "Student Board.";
	}
}