package com.dra.backend.dto.mensagem;

import lombok.Data;

@Data
public class EnviarMensagemDTO {
    private String assunto;
    private String conteudo;
    private String receptor;

     EnviarMensagemDTO(String assunto, String conteudo, String receptor) {
        this.assunto = assunto;
        this.conteudo = conteudo;
        this.receptor = receptor;
    }

}