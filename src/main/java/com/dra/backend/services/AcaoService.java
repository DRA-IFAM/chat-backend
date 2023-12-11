package com.dra.backend.services;

import com.dra.backend.models.entities.Acao;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.AcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;

    public List<Acao> listarTodasAcoes() {
        return acaoRepository.findAll();
    }

    public Optional<Acao> buscarAcaoPorId(Long id) {
        return acaoRepository.findById(id);
    }

    public Acao criarAcao(Acao acao) {
        return acaoRepository.save(acao);
    }

    public Acao atualizarAcao(Long id, Acao dadosAtualizados) {
        return acaoRepository.findById(id)
                .map(acaoExistente -> {
                    acaoExistente.setDescricao(dadosAtualizados.getDescricao());
                    acaoExistente.setPublicador(dadosAtualizados.getPublicador());
                    acaoExistente.setDataPlanejada(dadosAtualizados.getDataPlanejada());
                    acaoExistente.setDataRealizada(dadosAtualizados.getDataRealizada());
                    return acaoRepository.save(acaoExistente);
                })
                .orElseGet(() -> {
                    dadosAtualizados.setId(id);
                    return acaoRepository.save(dadosAtualizados);
                });
    }

    public void deletarAcao(Long id) {
        acaoRepository.deleteById(id);
    }
    
    public List<Acao> buscarAcoesPorPublicador(Contato publicador) {
        return acaoRepository.findByPublicador(publicador);
    }
}
