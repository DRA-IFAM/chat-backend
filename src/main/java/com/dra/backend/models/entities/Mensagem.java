package com.dra.backend.models.entities;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Schema(hidden = true)
@Entity
@Table(name = "mensagens")
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

	private Mensagem(Contato emissor, Contato receptor, String assunto, String conteudo) {
		this.emissor = emissor;
		this.receptor = receptor;
		this.assunto = assunto;
		this.conteudo = conteudo;
	}

	public static Mensagem from(Contato emissor, Contato receptor, String assunto, String conteudo) {
		return new Mensagem(emissor, receptor, assunto, conteudo);
	}

	@PrePersist
	protected void onCreate() {
		data = new Date();
	}

}