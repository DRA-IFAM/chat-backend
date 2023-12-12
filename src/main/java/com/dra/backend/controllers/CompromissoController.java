package com.dra.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.services.CompromissoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Compromisso")
@RestController
@RequestMapping("/compromisso")
public class CompromissoController {

	@Autowired
	CompromissoService compromissoService;

	@PostMapping
	@Operation(summary = "Cria um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso criado com sucesso.")
	ResponseEntity<Compromisso> criarCompromissos(@RequestBody Compromisso compromisso) {
		compromissoService.criarCompromisso(compromisso);
		return ResponseEntity.created(null).body(compromisso);
	}

	@GetMapping
	@Operation(summary = "Lista todos os compromissos.")
	@ApiResponse(responseCode = "200", description = "Compromissos listados com sucesso.")
	ResponseEntity<List<Compromisso>> listaCompromissos() {
		List<Compromisso> compromissos = compromissoService.listaCompromissos();
		if (compromissos.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(compromissos);
		return ResponseEntity.ok(compromissos);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista o compromisso pelo Id")
	@ApiResponse(responseCode = "200", description = "Compromisso listado com sucesso.")
	ResponseEntity<Compromisso> listaCompromissoPorId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(compromissoService.listaCompromisso(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	@PutMapping
	@Operation(summary = "Edita o compromisso selecionado.")
	@ApiResponse(responseCode = "200", description = "Compromisso editado com sucesso.")
	ResponseEntity<Compromisso> editarCompromisso(@RequestBody Compromisso compromisso) {
		try {
			return ResponseEntity.accepted().body(compromissoService.editarCompromisso(compromisso));
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
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
