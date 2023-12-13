package com.dra.backend.persistency;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.entities.Compromisso;
import com.dra.backend.models.entities.Acao;

public interface AcaoRepository extends JpaRepository<Acao, Long> {

    Optional<Acao> findById(Long id);

    List<Acao> findByDataPlanejada(Date dataPlanejada);

    List<Acao> findByDataRealizada(Date dataRealizada);

    List<Acao> findByCompromisso(Compromisso compromisso);

    List<Acao> findByPublicador(Contato publicador);

}
