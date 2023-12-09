package com.dra.backend.dto.mensagem;

import java.util.Date;

import com.dra.backend.models.entities.Mensagem;

import lombok.Data;

@Data
public class MensagemDTO {
    Long id;
    String assunto;
    String emissor;
    String receptor;
    String conteudo;
    Date data;

    public MensagemDTO(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.assunto = mensagem.getAssunto();
        this.emissor = mensagem.getEmissor().getEmail();
        this.receptor = mensagem.getReceptor().getEmail();
        this.conteudo = mensagem.getConteudo();
        this.data = mensagem.getData();
    }
}
