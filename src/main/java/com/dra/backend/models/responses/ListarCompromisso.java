package com.dra.backend.models.responses;

import java.util.Date;
import java.util.List;

import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.entities.CompromissoStatus;

import lombok.Data;

@Data
public class ListarCompromisso {
    Long id;
    String criador;
    String titulo;
    Date data;
    String local;
    String descricao;
    List<String> participantes;
    CompromissoStatus status;

    public static ListarCompromisso from(Compromisso compromisso) {
        ListarCompromisso compromissoDTO = new ListarCompromisso();
        compromissoDTO.setId(compromisso.getId());
        compromissoDTO.setCriador(compromisso.getCriador().getNome());
        compromissoDTO.setTitulo(compromisso.getTitulo());
        compromissoDTO.setData(compromisso.getData());
        compromissoDTO.setLocal(compromisso.getLocal());
        compromissoDTO.setDescricao(compromisso.getDescricao());
        compromissoDTO.setParticipantes(
                compromisso.getParticipantes().stream().map(participante -> participante.getNome()).toList());
        compromissoDTO.setStatus(compromisso.getStatus());
        return compromissoDTO;
    }

}
