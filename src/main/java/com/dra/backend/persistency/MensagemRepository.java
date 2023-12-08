package com.dra.backend.persistency;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.entities.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByReceptor(Contato receptor);

    List<Mensagem> findByEmissor(Contato emissor);

    // encontrar pelo id do emissor e pelo id do receptor
    Mensagem findByReceptorAndEmissor(Contato receptor, Contato emissor);
}