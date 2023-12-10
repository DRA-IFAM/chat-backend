package com.dra.backend.persistency;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.entities.Acao;

public interface AcaoRepository extends JpaRepository<Acao, Long> {

    List<Acao> findByPublicador(Contato publicador);

}