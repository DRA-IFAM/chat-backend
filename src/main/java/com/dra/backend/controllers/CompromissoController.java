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
import com.dra.backend.models.entities.CompromissoStatus;
import com.dra.backend.models.responses.ListarCompromisso;
import com.dra.backend.services.CompromissoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Compromisso")
@RestController
@RequestMapping("api/compromisso")
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
		String emailCriador = pegarEmailDoToken();
		compromissoService.marcarCompromisso(compromissoDTO, emailCriador);
		return ResponseEntity.status(HttpStatus.CREATED).body("Compromisso criado com sucesso.");
	}

	@GetMapping
	@Operation(summary = "Lista todos os compromissos.")
	@ApiResponse(responseCode = "200", description = "Compromissos listados com sucesso.")
	ResponseEntity<List<ListarCompromisso>> listaCompromissos() {
		String emailCriador = pegarEmailDoToken();
		List<Compromisso> compromissos = compromissoService.listaCompromissosPorCriador(emailCriador);
		List<ListarCompromisso> response = compromissos.stream().map(compromisso -> ListarCompromisso.from(compromisso))
				.toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista o compromisso pelo Id")
	@ApiResponse(responseCode = "200", description = "Compromisso listado com sucesso.")
	ResponseEntity<ListarCompromisso> listaCompromissoPorId(@PathVariable Long id) {
		String emailCriador = pegarEmailDoToken();
		Compromisso compromisso = compromissoService.listarMeusCompromissoPorId(id, emailCriador);
		if (compromisso == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ListarCompromisso());
		}
		ListarCompromisso response = ListarCompromisso.from(compromisso);
		return ResponseEntity.ok(response);
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
	ResponseEntity<Void> excluirCompromisso(@PathVariable Long id) {
		String emailCriador = pegarEmailDoToken();
		Compromisso compromisso = compromissoService.excluirCompromisso(id, emailCriador);
		if (compromisso == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/responder/{id}")
	@Operation(summary = "Aceita / nega / cancela um compromisso.")
	@ApiResponse(responseCode = "200", description = "Compromisso aceito com sucesso.")
	ResponseEntity<Void> aceitarCompromisso(@PathVariable Long id, @RequestBody CompromissoStatus status) {
		String emailParticipante = pegarEmailDoToken();
		Compromisso compromisso = compromissoService.responderCompromisso(id, status, emailParticipante);
		if (compromisso == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	private String pegarEmailDoToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
