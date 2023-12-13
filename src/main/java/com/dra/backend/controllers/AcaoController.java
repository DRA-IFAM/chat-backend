package com.dra.backend.controllers;

import com.dra.backend.models.entities.Acao;
import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.services.AcaoService;
import com.dra.backend.services.CompromissoService;
import com.dra.backend.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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

    @Autowired
    private CompromissoService compromissoService;

    @Autowired
    private ContatoService contatoService;

    @GetMapping
    @Operation(summary = "Lista todas as ações.")
    @ApiResponse(responseCode = "200", description = "Ações listadas com sucesso.")
    public ResponseEntity<List<Acao>> listarTodasAcoes() {
        List<Acao> acoes = acaoService.listarTodasAcoes();
        return ResponseEntity.ok(acoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma ação pelo ID.")
    @ApiResponse(responseCode = "200", description = "Ação encontrada.")
    @ApiResponse(responseCode = "404", description = "Ação não encontrada.")
    public ResponseEntity<Acao> buscarAcaoPorId(@PathVariable Long id) {
        return acaoService.buscarAcaoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Cria uma nova ação.")
    @ApiResponse(responseCode = "201", description = "Ação criada com sucesso.")
    public ResponseEntity<Acao> criarAcao(@RequestBody Acao acao) {
        Acao novaAcao = acaoService.criarAcao(acao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAcao);
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

    @PutMapping("/{id}")
    public ResponseEntity<Acao> atualizarAcao(@PathVariable Long id, @RequestBody Acao dadosAtualizados) {
        Acao acaoAtualizada = acaoService.atualizarAcao(id, dadosAtualizados);
        if (acaoAtualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(acaoAtualizada);


    }

    @GetMapping("/data-planejada")
    public ResponseEntity<List<Acao>> buscarAcoesPorDataPlanejada(@RequestParam Date dataPlanejada) {
        List<Acao> acoes = acaoService.buscarAcoesPorDataPlanejada(dataPlanejada);
        return ResponseEntity.ok(acoes);
    }

    @GetMapping("/data-realizada")
    public ResponseEntity<List<Acao>> buscarAcoesPorDataRealizada(@RequestParam Date dataRealizada) {
        List<Acao> acoes = acaoService.buscarAcoesPorDataRealizada(dataRealizada);
        return ResponseEntity.ok(acoes);
    }

    }


