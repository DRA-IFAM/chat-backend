package com.dra.backend.persistency;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dra.backend.models.entities.Compromisso;

public interface CompromissoRepository extends JpaRepository<Compromisso, String> {

}
