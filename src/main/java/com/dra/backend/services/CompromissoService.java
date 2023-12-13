package com.dra.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.dto.compromisso.CriarCompromissoDTO;
import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.entities.CompromissoStatus;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.CompromissoRepository;
import com.dra.backend.persistency.ContatoRepository;

@Service
public class CompromissoService {

	@Autowired
	CompromissoRepository compromissoRepository;

	@Autowired
	ContatoRepository contatoRepository;

	public Compromisso marcarCompromisso(CriarCompromissoDTO compromissoDTO, String emailCriador) {
		Compromisso compromisso = CriarCompromissoDTO.from(compromissoDTO);
		Optional<Contato> criador = contatoRepository.findByEmail(emailCriador);
		List<Contato> participantes = new ArrayList<Contato>();
		if (criador.isPresent()) {
			compromisso.setCriador(criador.get());
		} else {
			throw new UnsupportedOperationException("Não foi possível encontrar o contato do criador.");
		}
		if (compromissoDTO.getParticipantes() == null) {
			throw new UnsupportedOperationException("Não é possível criar um compromisso sem participantes.");
		}
		for (String emailParticipante : compromissoDTO.getParticipantes()) {
			Optional<Contato> participante = contatoRepository.findByEmail(emailParticipante);
			if (participante.isPresent()) {
				participantes.add(participante.get());
			} else {
				throw new UnsupportedOperationException(
						"Não foi possível encontrar o contato do participante " + emailParticipante + ".");
			}
		}
		compromisso.setStatus(CompromissoStatus.SOLICITADO);
		compromisso.setParticipantes(participantes);
		return compromissoRepository.save(compromisso);
	}

	public List<Compromisso> listaCompromissosPorCriador(String emailCriador) {
		return compromissoRepository.findAll();
	}

	public Compromisso listarMeusCompromissoPorId(Long id, String emailCriador) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findByIdAndCriadorEmail(id,
				emailCriador);
		if (optionalCompromisso.isPresent())
			return optionalCompromisso.get();
		return null;
	}

	public Compromisso editarCompromisso(Long id, Compromisso compromisso) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findById(id);
		if (optionalCompromisso.isPresent()) {
			Compromisso compromisso_ = optionalCompromisso.get();

			if (compromisso_.getStatus() == CompromissoStatus.NEGADO
					|| compromisso_.getStatus() == CompromissoStatus.CANCELADO) {
				throw new UnsupportedOperationException(
						"Não é permitido editar compromissos com status NEGADO ou CANCELADO.");
			}

			if (!compromisso.getData().equals(compromisso_.getData())
					&& !compromisso.getLocal().equals(compromisso_.getLocal())) {
				compromisso.setStatus(CompromissoStatus.REAGENDADO);
			} else {
				compromisso.setStatus(compromisso_.getStatus());
			}
			compromisso.setId(id);
			return compromissoRepository.save(compromisso);
		}
		return new Compromisso();
	}

	public Compromisso excluirCompromisso(Long id, String emailCriador) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findByIdAndCriadorEmail(id, emailCriador);
		if (!optionalCompromisso.isPresent()) {
			return null;
		}
		Compromisso compromisso = optionalCompromisso.get();
		compromissoRepository.delete(compromisso);
		return compromisso;
	}

	public Compromisso responderCompromisso(Long id, CompromissoStatus status, String emailParticipante) {
		Optional<Compromisso> optionalCompromisso = compromissoRepository.findByIdAndParticipantesEmail(id,
				emailParticipante);
		if (!optionalCompromisso.isPresent()) {
			return null;
		}
		if (optionalCompromisso.isPresent()) {
			Compromisso compromissos = optionalCompromisso.get();
			if (compromissos.getStatus() == CompromissoStatus.SOLICITADO) {
				compromissos.setStatus(status);
				return compromissoRepository.save(compromissos);
			} else {
				throw new UnsupportedOperationException("Este compromisso não está em estado de solicitação.");
			}

		}
		return new Compromisso();
	}

}
