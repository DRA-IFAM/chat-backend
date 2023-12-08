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

@RequestMapping("/api")
@RestController
public class MensagemController {

        @Autowired
        JwtService jwtService;

        @Autowired
        MensagemService mensagemService;

        @GetMapping("/mensagem")
        public ResponseEntity<List<MensagemDTO>> getMinhasMensagens(@RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                List<Mensagem> mensagens = this.mensagemService.verMensagens(idEmissor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);

        }

        @GetMapping("/mensagem/{idReceptor}")
        public ResponseEntity<List<MensagemDTO>> getMensagemEnviadaPara(@PathVariable String idReceptor,
                        @RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                List<Mensagem> mensagens = this.mensagemService.verMensagens(idEmissor, idReceptor);
                List<MensagemDTO> mensagensDTO = mensagens.stream().map(mensagem -> new MensagemDTO(mensagem)).toList();
                return ResponseEntity.ok(mensagensDTO);
        }

        @PostMapping("/mensagem")
        ResponseEntity<MensagemDTO> criarMensagem(@RequestBody EnviarMensagemDTO dtoMensagem,
                        @RequestHeader String Authorization) {
                String idEmissor = this.jwtService.validateToken(Authorization);
                Mensagem mensagem = this.mensagemService.enviarMensagem(idEmissor, dtoMensagem.getReceptor(),
                                dtoMensagem.getConteudo(), dtoMensagem.getAssunto());
                MensagemDTO mensagemDTO = new MensagemDTO(mensagem);
                return ResponseEntity.ok(mensagemDTO);
        }

}