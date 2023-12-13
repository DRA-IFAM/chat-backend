package com.dra.backend.controllers;

import com.dra.backend.dto.acao.CriarAcaoDTO;
import com.dra.backend.models.entities.Acao;
import com.dra.backend.services.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Acao")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Acesso negado")
})
@RequestMapping("/api/acao")
@RestController
public class AcaoController {

    @Autowired
    private AcaoService acaoService;

    @PostMapping("/{idCompromisso}")
    @Operation(summary = "Cria uma nova ação.")
    @ApiResponse(responseCode = "201", description = "Ação criada com sucesso.")
    public ResponseEntity<List<Acao>> criarAcao(@RequestBody List<CriarAcaoDTO> acoesDTO,
            @PathVariable Long idCompromisso) {
        for (CriarAcaoDTO acaoDTO : acoesDTO) {
            Optional<String> erro = CriarAcaoDTO.validarCampos(acaoDTO);
            if (erro.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        String emailPublicador = pegarEmailDoToken();
        List<Acao> novaAcao = acaoService.criarAcao(acoesDTO, idCompromisso, emailPublicador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAcao);
    }

    @GetMapping("/{idCompromisso}")
    @Operation(summary = "Lista todas as ações.")
    @ApiResponse(responseCode = "200", description = "Ações listadas com sucesso.")
    public ResponseEntity<List<Acao>> listarTodasAcoes(@PathVariable Long idCompromisso) {
        List<Acao> acoes = acaoService.listarTodasAcoes(idCompromisso);
        System.out.println(acoes);
        return ResponseEntity.ok(acoes);
    }

    // @GetMapping("/{id}")
    // @Operation(summary = "Busca uma ação pelo ID.")
    // @ApiResponse(responseCode = "200", description = "Ação encontrada.")
    // @ApiResponse(responseCode = "404", description = "Ação não encontrada.")
    // public ResponseEntity<Acao> buscarAcaoPorId(@PathVariable Long id) {
    // return acaoService.buscarAcaoPorId(id)
    // .map(ResponseEntity::ok)
    // .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    // }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma ação")
    @ApiResponse(responseCode = "200", description = "Ação atualizada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Ação não encontrada.")
    public ResponseEntity<Acao> atualizarAcao(@PathVariable Long id, Acao acao) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(acaoService.atualizarAcao(id, acao));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma ação pelo ID.")
    @ApiResponse(responseCode = "204", description = "Ação deletada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Ação não encontrada.")
    public ResponseEntity<Void> deletarAcao(@PathVariable Long id) {
        if (!acaoService.deletarAcao(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    private String pegarEmailDoToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
