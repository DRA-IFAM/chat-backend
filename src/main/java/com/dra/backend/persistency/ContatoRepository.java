package com.dra.backend.persistency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.dra.backend.models.entities.Contato;

public interface ContatoRepository extends JpaRepository<Contato, String> {
    UserDetails findByEmail(String email);

    Contato findContatoById(String id);
}
