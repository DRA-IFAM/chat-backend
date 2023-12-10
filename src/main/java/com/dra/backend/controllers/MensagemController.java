package com.dra.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dra.backend.dto.mensagem.EnviarMensagemDTO;
import com.dra.backend.models.entities.Mensagem;
import com.dra.backend.models.responses.ListarMensagem;
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
        ResponseEntity<List<ListarMensagem>> getMinhasMensagens() {
                String emailEmissor = getEmailEmissor();
                List<Mensagem> mensagens = this.mensagemService.verMensagens(emailEmissor);
                List<ListarMensagem> listarMensagem = mensagens.stream().map(mensagem -> ListarMensagem.from(mensagem))
                                .toList();
                return ResponseEntity.ok(listarMensagem);

        }

        @GetMapping("/{emailReceptor}")
        @Operation(summary = "Lista todas as mensagens enviadas para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagens listadas com sucesso.")
        ResponseEntity<List<ListarMensagem>> getMensagemEnviadaPara(@PathVariable String emailReceptor) {
                String emailEmissor = getEmailEmissor();
                List<Mensagem> mensagens = this.mensagemService.verMensagens(emailEmissor, emailReceptor);
                List<ListarMensagem> listarMensagens = mensagens.stream().map(mensagem -> ListarMensagem.from(mensagem))
                                .toList();
                return ResponseEntity.ok(listarMensagens);
        }

        @PostMapping
        @Operation(summary = "Envia uma mensagem para um usuário.")
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso.")
        ResponseEntity<ListarMensagem> criarMensagem(@RequestBody EnviarMensagemDTO dtoMensagem) {
                Optional<String> error = EnviarMensagemDTO.validarCampos(dtoMensagem);
                if (error.isPresent()) {
                        return ResponseEntity.badRequest().build();
                }
                String emailEmissor = getEmailEmissor();
                Mensagem mensagem = this.mensagemService.enviarMensagem(emailEmissor, dtoMensagem.getReceptor(),
                                dtoMensagem.getConteudo(), dtoMensagem.getAssunto());
                ListarMensagem listarMensagem = ListarMensagem.from(mensagem);
                return ResponseEntity.ok(listarMensagem);
        }

        @DeleteMapping("/{idMensagem}")
        @Operation(summary = "Deleta uma mensagem.")
        @ApiResponse(responseCode = "204", description = "Mensagem deletada com sucesso.")
        @ApiResponse(responseCode = "400", description = "Você não tem permissão para deletar essa mensagem.")
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada.")
        ResponseEntity<Void> deletarMensagem(@PathVariable Long idMensagem) {
                String emailEmissor = getEmailEmissor();
                Optional<Mensagem> mensagem = null;
                try {
                        mensagem = this.mensagemService.deletarMensagem(emailEmissor, idMensagem);
                        if (mensagem.isEmpty()) {
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