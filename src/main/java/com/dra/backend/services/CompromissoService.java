package com.dra.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.persistency.CompromissoRepository;

@Service
public class CompromissoService {

	@Autowired
	CompromissoRepository compromissoRepository;

	public Compromisso criarCompromisso(Compromisso compromisso) {
		return compromissoRepository.save(compromisso);
	}

	public List<Compromisso> listaCompromissos() {
		return compromissoRepository.findAll();
	}

	public Compromisso listaCompromisso() {
		return null;
	}

	public Compromisso editarCompromisso(Compromisso compromisso) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(compromisso.getId());
		if (optionalCompromisso.isPresent()) {
			compromisso.setId(compromisso.getId());
			compromisso.setData(compromisso.getData());
			compromisso.setLocal(compromisso.getLocal());
			compromisso.setDescricao(compromisso.getDescricao());

			if (!compromisso.getData().equals(compromisso.getData())) {
				compromisso.setStatus(Compromisso.Status.REAGENDADO);
			}

			return compromissoRepository.save(compromisso);
		}
		return new Compromisso();
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
