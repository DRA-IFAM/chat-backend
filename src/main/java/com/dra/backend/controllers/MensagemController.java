package com.dra.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dra.backend.dto.mensagem.EnviarMensagemDTO;
import com.dra.backend.dto.mensagem.MensagemDTO;
import com.dra.backend.models.entities.Mensagem;
import com.dra.backend.services.JwtService;
import com.dra.backend.services.MensagemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Mensagem")
@RequestMapping("/api/mensagem")
@RestController
public class MensagemController {

        @Autowired
        JwtService jwtService;

        @Autowired
        MensagemService mensagemService;

        @GetMapping
        @Operation(summary = "Lista todas as mensagens.")
        @ApiResponse(responseCode = "200", description = "Mensagens listadas com sucesso.")
        ResponseEntity<List<MensagemDTO>> getMinhasMensagens(@RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                List<Mensagem> mensagens = this.mensagemService.verMensagens(idEmissor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);

        }

        @GetMapping("/{idReceptor}")
        @Operation(summary = "Lista todas as mensagens enviadas para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagens listadas com sucesso.")
        ResponseEntity<List<MensagemDTO>> getMensagemEnviadaPara(@PathVariable String idReceptor,
                        @RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                List<Mensagem> mensagens = this.mensagemService.verMensagens(idEmissor, idReceptor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);
        }

        @PostMapping
        @Operation(summary = "Envia uma mensagem para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso.")
        ResponseEntity<MensagemDTO> criarMensagem(@RequestBody EnviarMensagemDTO dtoMensagem,
                        @RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                Mensagem mensagem = this.mensagemService.enviarMensagem(idEmissor, dtoMensagem.getReceptor(),
                                dtoMensagem.getConteudo(), dtoMensagem.getAssunto());
                MensagemDTO mensagemDTO = new MensagemDTO(mensagem);
                return ResponseEntity.ok(mensagemDTO);
        }

        @DeleteMapping("/{idMensagem}")
        @Operation(summary = "Deleta uma mensagem.")
        @ApiResponse(responseCode = "200", description = "Mensagem deletada com sucesso.")
        ResponseEntity<String> deletarMensagem(@PathVariable Long idMensagem,
                        @RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                Mensagem mensagem = null;
                try {
                        mensagem = this.mensagemService.deletarMensagem(idEmissor, idMensagem);
                        if (mensagem == null) {
                                return ResponseEntity.notFound().build();
                        }
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
                return ResponseEntity.ok("Mensagem deletada com sucesso.");
        }

}