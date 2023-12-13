package com.dra.backend.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "compromise")
public class Compromisso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Contato criador;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime data;

	@Column(nullable = false)
	private String local;

	@Column(nullable = false)
	private String descricao;

	@ManyToMany
	private List<Contato> participantes;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Compromisso(Long id, Contato criador, String titulo, LocalDateTime data, String local, String descricao,
			List<Contato> participantes, Status status) {
		this.id = id;
		this.criador = criador;
		this.titulo = titulo;
		this.data = data;
		this.local = local;
		this.descricao = descricao;
		this.participantes = participantes;
		this.status = status;
	}

	public enum Status {
		ACEITO, NEGADO, CANCELADO, SOLICITADO, REAGENDADO
	}
}
