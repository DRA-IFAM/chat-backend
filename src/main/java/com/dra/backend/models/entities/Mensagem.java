package com.dra.backend.models.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(nullable = false)
    private String conteudo;
    @ManyToOne
    private Contato emissor;
    @ManyToOne
    private Contato receptor;

    @PrePersist
    protected void onCreate() {
        data = new Date();
    }
}
