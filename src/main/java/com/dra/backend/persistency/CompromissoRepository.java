package com.dra.backend.persistency;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Compromisso;

public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {
    Optional<Compromisso> findByIdAndCriadorEmail(Long id, String criadorId);

    Optional<Compromisso> findByIdAndParticipantesEmail(Long id, String participanteId);
}
