package com.dra.backend.persistency;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.entities.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByEmissor(Contato emissor);

    List<Mensagem> findAllByReceptorAndEmissor(Contato receptor, Contato emissor);
}