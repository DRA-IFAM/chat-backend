package com.dra.backend.dto.mensagem;

import java.util.Optional;

import lombok.*;

@Data
public class EnviarMensagemDTO {
    private String assunto;
    private String conteudo;
    private String emissor;
    private String receptor;

    public static Optional<String> validarCampos(EnviarMensagemDTO mensagem) {
        if (mensagem.getAssunto() == null || mensagem.getAssunto().isEmpty()) {
            return Optional.of("Assunto inválido. Por favor, tente novamente.");
        }
        if (mensagem.getConteudo() == null || mensagem.getConteudo().isEmpty()) {
            return Optional.of("Conteúdo inválido. Por favor, tente novamente.");
        }
        if (mensagem.getReceptor() == null || mensagem.getReceptor().isEmpty()) {
            return Optional.of("Receptor inválido. Por favor, tente novamente.");
        }
        return Optional.empty();
    }

}