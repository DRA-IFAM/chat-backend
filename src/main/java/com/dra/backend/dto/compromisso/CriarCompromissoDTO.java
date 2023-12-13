package com.dra.backend.dto.compromisso;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dra.backend.configs.SingleValueToListDeserializer;
import com.dra.backend.models.entities.Compromisso;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class CriarCompromissoDTO {
    String titulo;
    Date data;
    String local;
    String descricao;
    @JsonDeserialize(using = SingleValueToListDeserializer.class)
    List<String> participantes;

    public static Optional<String> validarCampos(CriarCompromissoDTO compromissoDTO) {
        if (compromissoDTO.getTitulo() == null || compromissoDTO.getTitulo().isEmpty())
            return Optional.of("O título não pode ser vazio.");
        if (compromissoDTO.getData() == null)
            return Optional.of("A data não pode ser vazia.");
        if (compromissoDTO.getLocal() == null || compromissoDTO.getLocal().isEmpty())
            return Optional.of("O local não pode ser vazio.");
        if (compromissoDTO.getDescricao() == null || compromissoDTO.getDescricao().isEmpty())
            return Optional.of("A descrição não pode ser vazia.");

        if (compromissoDTO.getParticipantes().isEmpty())
            return Optional.of("O compromisso deve ter pelo menos um participante.");

        return Optional.empty();
    }

    public static Compromisso from(CriarCompromissoDTO compromissoDTO) {
        Compromisso compromisso = new Compromisso();
        compromisso.setTitulo(compromissoDTO.getTitulo());
        compromisso.setData(compromissoDTO.getData());
        compromisso.setLocal(compromissoDTO.getLocal());
        compromisso.setDescricao(compromissoDTO.getDescricao());
        return compromisso;
    }
}
