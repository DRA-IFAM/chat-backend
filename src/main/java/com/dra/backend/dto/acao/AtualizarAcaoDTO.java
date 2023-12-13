package com.dra.backend.dto.acao;

import java.sql.Date;

import com.dra.backend.models.entities.Acao;

import lombok.Data;

@Data
public class AtualizarAcaoDTO {
    private String descricao;
    private Date dataPlanejada;
    private Date dataRealizada;

    public static Acao from(AtualizarAcaoDTO acaoDTO) {
        Acao acao = new Acao();
        acao.setDescricao(acaoDTO.getDescricao());
        acao.setDataPlanejada(acaoDTO.getDataPlanejada());
        acao.setDataRealizada(acaoDTO.getDataRealizada());
        return acao;
    }
}
