package com.dra.backend.models.responses;

import com.dra.backend.models.entities.Acao;

import lombok.Data;

@Data
public class ListarAcao {
    Long id;
    String descricao;
    String publicador;
    Long compromisso;
    String dataPlanejada;
    String dataRealizada;

    public static ListarAcao from(Acao acao) {
        ListarAcao listarAcao = new ListarAcao();
        listarAcao.setId(acao.getId());
        listarAcao.setDescricao(acao.getDescricao());
        listarAcao.setCompromisso(acao.getCompromisso().getId());
        listarAcao.setPublicador(acao.getPublicador().getEmail());
        listarAcao.setDataPlanejada(acao.getDataPlanejada() != null ? acao.getDataPlanejada().toString() : null);
        listarAcao.setDataRealizada(acao.getDataRealizada() != null ? acao.getDataRealizada().toString() : null);
        return listarAcao;
    }
}
