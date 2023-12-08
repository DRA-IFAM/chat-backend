package com.dra.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dra.backend.dto.auth.CriarContatoDTO;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.ContatoRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private ContatoRepository userRepository;

    public Contato register(CriarContatoDTO data) {
        if (this.userRepository.findByEmail(data.getEmail()) != null) {
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        Contato user = new Contato(data.getNome(), data.getEmail(), encryptedPassword, data.getTelefone(),
                data.getEndereço(), data.getBairro(), data.getCidade(), data.getEstado());
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username);
    }

}