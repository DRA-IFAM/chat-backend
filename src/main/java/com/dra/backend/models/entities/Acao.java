package com.dra.backend.models.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToOne
    private Contato publicador;
    @Temporal(TemporalType.DATE)
    private Date dataPlanejada;
    @Temporal(TemporalType.DATE)
    private Date dataRealizada;

    public Acao(Contato publicador, String descricao) {
        this.publicador = publicador;
        this.descricao = descricao;
    }

    @PrePersist
    protected void onCreate() {
        dataRealizada = new Date(); dataPlanejada = new Date();
    }
}
