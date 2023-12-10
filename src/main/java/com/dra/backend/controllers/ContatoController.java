package com.dra.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dra.backend.models.responses.ListarContato;
import com.dra.backend.services.ContatoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Contato")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Acesso negado")
})
@RequestMapping("/api/contato")
@RestController
public class ContatoController {
    @Autowired
    ContatoService contatoService;

    @GetMapping
    @Operation(summary = "Lista todos os contatos.")
    @ApiResponse(responseCode = "200", description = "Contatos listados com sucesso.")
    ResponseEntity<List<ListarContato>> listarContatos() {
        List<ListarContato> contatos = contatoService.listarContatos();
        return ResponseEntity.ok(contatos);

    }

    @GetMapping("/{email}")
    @Operation(summary = "Lista um contato pelo email.")
    @ApiResponse(responseCode = "200", description = "Contato listado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Contato não encontrado.")
    ResponseEntity<ListarContato> listarContatoPorEmail(@PathVariable String email) {
        Optional<ListarContato> contato = contatoService.listarContato(email);
        if (!contato.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(contato.get());
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deleta um contato pelo email.")
    @ApiResponse(responseCode = "204", description = "Contato deletado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Não é possível deletar o contato de outro usuário.")
    ResponseEntity<Void> deletarContato(@PathVariable String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = authentication.getName();
        if (!emailLogado.equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        contatoService.deletarContato(emailLogado);
        return ResponseEntity.noContent().build();
    }

}
