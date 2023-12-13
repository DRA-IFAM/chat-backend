package com.dra.backend.services;

import com.dra.backend.dto.acao.AtualizarAcaoDTO;
import com.dra.backend.dto.acao.CriarAcaoDTO;
import com.dra.backend.models.entities.Acao;
import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.AcaoRepository;
import com.dra.backend.persistency.CompromissoRepository;
import com.dra.backend.persistency.ContatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private CompromissoRepository compromissoRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    public List<Acao> listarAcoesPorCompromisso(Long id) {
        Compromisso compromisso = compromissoRepository.findById(id).get();
        return acaoRepository.findByCompromisso(compromisso);
    }

    public List<Acao> criarAcao(List<CriarAcaoDTO> acaoDTO, Long idCompromisso, String emailPublicador) {
        List<Acao> acoes = new ArrayList<Acao>();
        for (CriarAcaoDTO acao : acaoDTO) {
            Optional<Compromisso> compromisso = compromissoRepository.findById(idCompromisso);
            Optional<Contato> publicador = contatoRepository.findByEmail(emailPublicador);
            if (!compromisso.isPresent() || !publicador.isPresent()) {
                return null;
            }

            Acao novaAcao = CriarAcaoDTO.from(acao);
            novaAcao.setCompromisso(compromisso.get());
            novaAcao.setPublicador(publicador.get());
            acoes.add(novaAcao);

        }
        try {
            acoes = acaoRepository.saveAll(acoes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acoes;
    }

    public Acao atualizarAcao(Long id, AtualizarAcaoDTO acaoDTO) {
        Optional<Acao> acao = acaoRepository.findById(id);
        if (!acao.isPresent()) {
            return null;
        }

        if (acaoDTO.getDescricao() != null) {
            acao.get().setDescricao(acaoDTO.getDescricao());
        }

        if (acaoDTO.getDataPlanejada() != null) {
            acao.get().setDataPlanejada(acaoDTO.getDataPlanejada());
        }

        if (acaoDTO.getDataRealizada() != null) {
            acao.get().setDataRealizada(acaoDTO.getDataRealizada());
        }

        return acaoRepository.save(acao.get());
    }

    public boolean deletarAcao(Long id) {
        if (acaoRepository.existsById(id)) {
            acaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
