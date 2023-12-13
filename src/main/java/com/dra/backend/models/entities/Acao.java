package com.dra.backend.models.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "acao")
@NoArgsConstructor
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @JsonManagedReference
    @ManyToOne
    private Compromisso compromisso;
    @JsonManagedReference
    @ManyToOne
    private Contato publicador;
    @Temporal(TemporalType.DATE)
    private Date dataPlanejada;
    @Temporal(TemporalType.DATE)
    private Date dataRealizada;

    public Acao(Compromisso compromisso, Contato publicador,
            String descricao, Date dataPlanejada) {
        this.compromisso = compromisso;
        this.publicador = publicador;
        this.descricao = descricao;
        this.dataPlanejada = dataPlanejada;
    }

}
