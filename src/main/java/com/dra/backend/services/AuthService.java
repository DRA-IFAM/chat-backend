package com.dra.backend.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.ContatoRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private ContatoRepository userRepository;

    public Optional<Contato> register(Contato contato) {
        Optional<Contato> user = this.userRepository.findByEmail(contato.getEmail());
        if (user.isPresent()) {
            return Optional.empty();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(contato.getSenha());
        contato.setSenha(encryptedPassword);
        return Optional.of(this.userRepository.save(contato));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}