package com.dra.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.backend.model.Compromisso;
import com.dra.backend.service.CompromissoService;

@RestController
@RequestMapping("/compromisso")
public class CompromissoController {

	@Autowired
	CompromissoService compromissoService;

	@PostMapping
	ResponseEntity<Compromisso> criarCompromissos() {
		return null;
	}

	@GetMapping
	ResponseEntity<List<Compromisso>> listaCompromissos() {
		List<Compromisso> compromissos = compromissoService.listaCompromissos();
		if (compromissos.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(compromissos);
		return ResponseEntity.ok(compromissos);
	}

	@GetMapping("/{id}")
	ResponseEntity<Compromisso> listaCompromissoPorId() {
		return null;
	}

	@PutMapping
	ResponseEntity<Compromisso> editarCompromisso() {
		return null;
	}

	@DeleteMapping
	ResponseEntity<Compromisso> excluirCompromisso() {
		return null;
	}

	@PostMapping("/aceitar")
	ResponseEntity<Compromisso> aceitarCompromisso() {
		return null;
	}

	@PostMapping("/negar")
	ResponseEntity<Compromisso> negarCompromisso() {
		return null;
	}

	@PutMapping("/cancelar")
	ResponseEntity<Compromisso> cancelarCompromisso() {
		return null;
	}
}

