package com.dra.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dra.backend.dto.compromisso.CriarCompromissoDTO;
import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.responses.ListarCompromisso;
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
	ResponseEntity<String> criarCompromisso(@RequestBody CriarCompromissoDTO compromissoDTO) {
		Optional<String> error = CriarCompromissoDTO.validarCampos(compromissoDTO);
		if (error.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.get());
		}
		String emailCriador = pegarEmailCriador();
		compromissoService.marcarCompromisso(compromissoDTO, emailCriador);
		return ResponseEntity.status(HttpStatus.CREATED).body("Compromisso criado com sucesso.");
	}

	@GetMapping
	@Operation(summary = "Lista todos os compromissos.")
	@ApiResponse(responseCode = "200", description = "Compromissos listados com sucesso.")
	ResponseEntity<List<ListarCompromisso>> listaCompromissos() {
		Optional<String> emailCriador = Optional.of(pegarEmailCriador());
		List<Compromisso> compromissos = compromissoService.listaCompromissosPorCriador(emailCriador.get());
		List<ListarCompromisso> response = compromissos.stream().map(compromisso -> ListarCompromisso.from(compromisso))
				.toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista o compromisso pelo Id")
	@ApiResponse(responseCode = "200", description = "Compromisso listado com sucesso.")
	ResponseEntity<Compromisso> listaCompromissoPorId(@PathVariable Long id) {
		return ResponseEntity.ok(compromissoService.listaCompromisso(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Edita o compromisso selecionado.")
	@ApiResponse(responseCode = "200", description = "Compromisso editado com sucesso.")
	ResponseEntity<Compromisso> editarCompromisso(@PathVariable Long id,
			@RequestBody Compromisso compromisso) {
		try {
			return ResponseEntity.accepted().body(compromissoService.editarCompromisso(id, compromisso));
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso excluído com sucesso.")
	ResponseEntity<Compromisso> excluirCompromisso(@PathVariable Long id) {
		try {
			compromissoService.excluirCompromisso(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Compromisso());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	@PostMapping("/aceitar/{id}")
	@Operation(summary = "Aceita um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso aceito com sucesso.")
	ResponseEntity<Compromisso> aceitarCompromisso(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(compromissoService.aceitarCompromisso(id));
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	@PostMapping("/negar/{id}")
	@Operation(summary = "Nega um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso negado com sucesso.")
	ResponseEntity<Compromisso> negarCompromisso(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(compromissoService.negarCompromisso(id));
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	@PutMapping("/cancelar/{id}")
	@Operation(summary = "Cancela um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso cancelado com sucesso.")
	ResponseEntity<Compromisso> cancelarCompromisso(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(compromissoService.cancelarCompromisso(id));
		} catch (UnsupportedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Compromisso());
		}
	}

	private String pegarEmailCriador() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
