package com.dra.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.model.Compromisso;
import com.dra.backend.repository.CompromissoRepository;

@Service
public class CompromissoService {

	@Autowired
	CompromissoRepository compromissoRepository;

	public Compromisso criarCompromisso() {
		return null;
	}

	public List<Compromisso> listaCompromissos() {
		return compromissoRepository.findAll();
	}

	public Compromisso listaCompromissosPorId() {
		return null;
	}

	public Compromisso editarCompromisso() {
		return null;
	}

	public void excluirCompromisso() {

	}

	public Compromisso aceitarCompromisso() {
		return null;
	}

	public Compromisso negarCompromisso() {
		return null;
	}

	public Compromisso cancelarCompromisso() {
		return null;
	}
}
