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

	public Compromisso editarCompromisso(Long id, Compromisso compromisso) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent()) {
			Compromisso compromisso_ = optionalCompromisso.get();

			if (compromisso_.getStatus() == Compromisso.Status.NEGADO
					|| compromisso_.getStatus() == Compromisso.Status.CANCELADO) {
				throw new UnsupportedOperationException(
						"Não é permitido editar compromissos com status NEGADO ou CANCELADO.");
			}

			if (!compromisso.getData().equals(compromisso_.getData())
					&& !compromisso.getLocal().equals(compromisso_.getLocal())) {
				compromisso.setStatus(Compromisso.Status.REAGENDADO);
			} else {
				compromisso.setStatus(compromisso_.getStatus());
			}
			compromisso.setId(id);
			return compromissoRepository.save(compromisso);
		}
		return new Compromisso();
	}

	public void excluirCompromisso(Long id) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent())
			compromissoRepository.deleteById(id);
	}

	public Compromisso aceitarCompromisso(Long id) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent()) {
			Compromisso compromissos = optionalCompromisso.get();

			if (compromissos.getStatus() == Compromisso.Status.SOLICITADO) {
				compromissos.setStatus(Compromisso.Status.ACEITO);
				return compromissoRepository.save(compromissos);
			} else {
				throw new UnsupportedOperationException("Este compromisso não está em estado de solicitação.");
			}
		}
		return new Compromisso();
	}

	public Compromisso negarCompromisso(Long id) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent()) {
			Compromisso compromissos = optionalCompromisso.get();

			if (compromissos.getStatus() == Compromisso.Status.SOLICITADO) {
				compromissos.setStatus(Compromisso.Status.NEGADO);
				return compromissoRepository.save(compromissos);
			} else {
				throw new UnsupportedOperationException("Este compromisso não está em estado de solicitação.");
			}
		}
		return new Compromisso();
	}

	public Compromisso cancelarCompromisso(Long id) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent()) {
			Compromisso compromissos = optionalCompromisso.get();

			if (compromissos.getStatus() == Compromisso.Status.SOLICITADO) {
				compromissos.setStatus(Compromisso.Status.CANCELADO);
				return compromissoRepository.save(compromissos);
			} else {
				throw new UnsupportedOperationException("Este compromisso não está em estado de solicitação.");
			}
		}
		return new Compromisso();
	}
}
