package com.dra.backend.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Compromisso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Calendar data;

	private String local;

	private String descrição;

	private Status status;

	public Compromisso() {
	}

	public Compromisso(Long id, Calendar data, String local, String descrição, Status status) {
		super();
		this.id = id;
		this.data = data;
		this.local = local;
		this.descrição = descrição;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getDescrição() {
		return descrição;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ACEITO, NEGADO, CANCELADO, SOLICITADO, REAGENDADO
	}

	@Override
	public String toString() {
		return "Compromisso [id=" + id + ", data=" + data + ", local=" + local + ", descrição=" + descrição
				+ ", status=" + status + "]";
	}
}
