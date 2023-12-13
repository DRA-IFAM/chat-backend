package com.dra.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.responses.ListarContato;
import com.dra.backend.persistency.ContatoRepository;

@Service
public class ContatoService {
    @Autowired
    ContatoRepository contatoRepository;

    public List<ListarContato> listarContatos() {
        List<Contato> contatos = contatoRepository.findAll();
        List<ListarContato> listarContatos = contatos.stream().map(contato -> ListarContato.from(contato)).toList();
        return listarContatos;
    }

    public Optional<ListarContato> listarContato(String email) {
        Optional<Contato> contato = contatoRepository.findByEmail(email);
        if (contato.isEmpty()) {
            return null;
        }
        ListarContato listarContato = ListarContato.from(contato.get());
        return Optional.of(listarContato);
    }

    public Optional<Contato> deletarContato(String email) {
        Optional<Contato> contato = contatoRepository.findByEmail(email);
        if (contato.isEmpty()) {
            return null;
        }
        contatoRepository.delete(contato.get());
        return contato;
    }

}
