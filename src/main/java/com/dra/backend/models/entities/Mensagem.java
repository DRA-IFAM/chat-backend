package com.dra.backend.models.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String assunto;
    @ManyToOne
    private Contato emissor;
    @ManyToOne
    private Contato receptor;
    @Column(nullable = false)
    private String conteudo;
    @Temporal(TemporalType.DATE)
    private Date data;

    public Mensagem(Contato emissor, Contato receptor, String assunto, String conteudo) {
        this.emissor = emissor;
        this.receptor = receptor;
        this.assunto = assunto;
        this.conteudo = conteudo;
    }

    @PrePersist
    protected void onCreate() {
        data = new Date();
    }
}
