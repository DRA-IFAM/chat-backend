package com.dra.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dra.backend.dto.mensagem.EnviarMensagemDTO;
import com.dra.backend.dto.mensagem.MensagemDTO;
import com.dra.backend.models.entities.Mensagem;
import com.dra.backend.services.JwtService;
import com.dra.backend.services.MensagemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Mensagem")
@SecurityRequirement(name = "BearerAuth")
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
        ResponseEntity<List<MensagemDTO>> getMinhasMensagens() {
                String emailEmissor = getEmailEmissor();
                List<Mensagem> mensagens = this.mensagemService.verMensagens(emailEmissor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);

        }

        @GetMapping("/{emailReceptor}")
        @Operation(summary = "Lista todas as mensagens enviadas para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagens listadas com sucesso.")
        ResponseEntity<List<MensagemDTO>> getMensagemEnviadaPara(@PathVariable String emailReceptor) {
                String emailEmissor = getEmailEmissor();
                List<Mensagem> mensagens = this.mensagemService.verMensagens(emailEmissor, emailReceptor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);
        }

        @PostMapping
        @Operation(summary = "Envia uma mensagem para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso.")
        ResponseEntity<MensagemDTO> criarMensagem(@RequestBody EnviarMensagemDTO dtoMensagem) {
                String emailEmissor = getEmailEmissor();
                Mensagem mensagem = this.mensagemService.enviarMensagem(emailEmissor, dtoMensagem.getReceptor(),
                                dtoMensagem.getConteudo(), dtoMensagem.getAssunto());
                MensagemDTO mensagemDTO = new MensagemDTO(mensagem);
                return ResponseEntity.ok(mensagemDTO);
        }

        @DeleteMapping("/{idMensagem}")
        @Operation(summary = "Deleta uma mensagem.")
        @ApiResponse(responseCode = "204", description = "Mensagem deletada com sucesso.")
        @ApiResponse(responseCode = "400", description = "Você não tem permissão para deletar essa mensagem.")
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada.")
        ResponseEntity<Void> deletarMensagem(@PathVariable Long idMensagem) {
                String emailEmissor = getEmailEmissor();
                Mensagem mensagem = null;
                try {
                        mensagem = this.mensagemService.deletarMensagem(emailEmissor, idMensagem);
                        if (mensagem == null) {
                                return ResponseEntity.notFound().build();
                        }
                } catch (Exception e) {
                        return ResponseEntity.badRequest().build();
                }
                return ResponseEntity.noContent().build();
        }

        private String getEmailEmissor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return authentication.getName();
        }

}