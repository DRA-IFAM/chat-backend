package com.dra.backend.persistency;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.dra.backend.models.entities.Contato;

public interface ContatoRepository extends JpaRepository<Contato, String> {
    Optional<Contato> findByEmail(String email);

    UserDetails findContatoByEmail(String email);
}
