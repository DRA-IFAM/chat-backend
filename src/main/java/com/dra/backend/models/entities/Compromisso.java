package com.dra.backend.models.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "compromissos")
public class Compromisso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Contato criador;

	@Column(nullable = false)
	private String titulo;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(nullable = false)
	private String local;

	@Column(nullable = false)
	private String descricao;

	@ManyToMany
	private List<Contato> participantes;

	@Enumerated(EnumType.STRING)
	private CompromissoStatus status;

	@JsonBackReference
	@OneToMany(mappedBy = "compromisso")
	private List<Acao> acoes;

	public Compromisso(Long id, Contato criador, String titulo, Date data, String local, String descricao,
			List<Contato> participantes, CompromissoStatus status) {
		this.id = id;
		this.criador = criador;
		this.titulo = titulo;
		this.data = data;
		this.local = local;
		this.descricao = descricao;
		this.participantes = participantes;
		this.status = status;
	}

	@PrePersist
	public void onPrePersist() {
		this.status = CompromissoStatus.SOLICITADO;
	}

}
