package com.dra.backend.models.responses;

import java.util.Date;

import com.dra.backend.models.entities.Mensagem;

import lombok.Data;

@Data
public class ListarMensagem {
    Long id;
    String assunto;
    String emissor;
    String receptor;
    String conteudo;
    Date data;

    private ListarMensagem(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.assunto = mensagem.getAssunto();
        this.emissor = mensagem.getEmissor().getEmail();
        this.receptor = mensagem.getReceptor().getEmail();
        this.conteudo = mensagem.getConteudo();
        this.data = mensagem.getData();
    }

    public static ListarMensagem from(Mensagem mensagem) {
        return new ListarMensagem(mensagem);
    }
}
