package com.dra.backend.services;

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

    public List<Acao> listarTodasAcoes(Long id) {
        Compromisso compromisso = compromissoRepository.findById(id).get();
        return acaoRepository.findByCompromisso(compromisso);
    }

    public Optional<Acao> buscarAcaoPorId(Long id) {
        return acaoRepository.findById(id);
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

    public Acao atualizarAcao(Long id, Acao dadosAtualizados) {
        return acaoRepository.findById(id)
                .map(acaoExistente -> {
                    acaoExistente.setDescricao(dadosAtualizados.getDescricao());
                    acaoExistente.setPublicador(dadosAtualizados.getPublicador());
                    acaoExistente.setCompromisso(dadosAtualizados.getCompromisso());
                    acaoExistente.setDataPlanejada(dadosAtualizados.getDataPlanejada());
                    acaoExistente.setDataRealizada(dadosAtualizados.getDataRealizada());
                    return acaoRepository.save(acaoExistente);
                })
                .orElseGet(() -> {
                    dadosAtualizados.setId(id);
                    return acaoRepository.save(dadosAtualizados);
                });
    }

    public boolean deletarAcao(Long id) {
        if (acaoRepository.existsById(id)) {
            acaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Acao> buscarAcoesPorPublicador(Contato publicador) {
        return acaoRepository.findByPublicador(publicador);
    }
}
