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
		compromisso.setStatus(Compromisso.Status.SOLICITADO);
		compromisso.setCriador(compromisso.getCriador());
		return compromissoRepository.save(compromisso);
	}

	public List<Compromisso> listaCompromissos() {
		return compromissoRepository.findAll();
	}

	public Compromisso listaCompromisso(Long id) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent())
			return optionalCompromisso.get();
		return new Compromisso();
	}

	public Compromisso editarCompromisso(Compromisso compromisso) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(compromisso.getId());
		if (optionalCompromisso.isPresent()) {
			Compromisso compromissos = optionalCompromisso.get();

			if (compromissos.getCriador().equals(compromisso.getCriador())) {
				compromissos.setId(compromisso.getId());
				compromissos.setData(compromisso.getData());
				compromissos.setLocal(compromisso.getLocal());
				compromissos.setDescricao(compromisso.getDescricao());

				if (!compromissos.getData().equals(compromisso.getData())) {
					compromissos.setStatus(Compromisso.Status.REAGENDADO);
				}
				return compromissoRepository.save(compromissos);
			} else {
				throw new UnsupportedOperationException("Usuário não tem permissão para editar este compromisso.");
			}
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
