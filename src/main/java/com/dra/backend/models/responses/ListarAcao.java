package com.dra.backend.models.responses;

import com.dra.backend.models.entities.Acao;

import lombok.Data;

@Data
public class ListarAcao {
    Long id;
    String descricao;
    String publicador;
    String compromisso;
    String dataPlanejada;
    String dataRealizada;

    public static ListarAcao from(Acao acao) {
        ListarAcao acaoDTO = new ListarAcao();
        acaoDTO.setId(acao.getId());
        acaoDTO.setDescricao(acao.getDescricao());
        acaoDTO.setPublicador(acao.getPublicador().getNome());
        acaoDTO.setCompromisso(acao.getCompromisso().getTitulo());
        acaoDTO.setDataPlanejada(acao.getDataPlanejada().toString());
        if (acao.getDataRealizada() != null) {
            acaoDTO.setDataRealizada(acao.getDataRealizada().toString());
        }
        return acaoDTO;
    }
}
