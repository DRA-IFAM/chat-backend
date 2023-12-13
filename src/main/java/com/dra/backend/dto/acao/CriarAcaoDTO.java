package com.dra.backend.dto.acao;

import java.sql.Date;
import java.util.Optional;

import com.dra.backend.models.entities.Acao;

import lombok.Data;

@Data
public class CriarAcaoDTO {
    private String descricao;
    private Date dataPlanejada;

    public static Optional<String> validarCampos(CriarAcaoDTO acaoDTO) {
        if (acaoDTO.getDescricao() == null || acaoDTO.getDescricao().isEmpty()) {
            return Optional.of("Descrição não pode ser vazia.");
        }

        if (acaoDTO.getDataPlanejada() == null) {
            return Optional.of("Data planejada não pode ser vazia.");
        }
        return Optional.empty();
    }

    public static Acao from(CriarAcaoDTO acaoDTO) {
        Acao acao = new Acao();
        acao.setDescricao(acaoDTO.getDescricao());
        acao.setDataPlanejada(acaoDTO.getDataPlanejada());
        return acao;
    }

}
