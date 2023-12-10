package com.dra.backend.models.entities;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "compromise")
public class Compromisso {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	private Contato admin;

	@ManyToMany
	private List<Contato> convidados;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;

	@Column(nullable = false)
	private String local;

	@Column(nullable = false)
	private String descricao;

	/*
	 * @ManyToMany private List<Acao> acoes;
	 */

	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Compromisso() {}

	public Compromisso(Contato admin, List<Contato> convidados, Calendar data, String local, String descricao,
			/*
			 * List<Acao> acoes,
			 */
			Status status) {
		this.admin = admin;
		this.convidados = convidados;
		this.data = data;
		this.local = local;
		this.descricao = descricao;
//        this.acoes = acoes;
		this.status = status;
	}

	public enum Status {
		ACEITO, NEGADO, CANCELADO, SOLICITADO, REAGENDADO
	}
}
