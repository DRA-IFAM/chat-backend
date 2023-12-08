package com.dra.backend.dto.mensagem;

import lombok.Data;

@Data
public class EnviarMensgemDTO {
    private String conteudo;
    private String receptor;

    public EnviarMensgemDTO(String conteudo, String receptor) {
        this.conteudo = conteudo;
        this.receptor = receptor;
    }

}