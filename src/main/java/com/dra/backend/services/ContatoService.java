package com.dra.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.dto.contato.ListarContato;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.persistency.ContatoRepository;

@Service
public class ContatoService {
    @Autowired
    ContatoRepository contatoRepository;

    public List<ListarContato> getContatos() {
        List<Contato> contatos = contatoRepository.findAll();
        List<ListarContato> listarContatos = contatos.stream().map(contato -> new ListarContato(contato.getNome(),
                contato.getEmail(), contato.getTelefone(), contato.getEndereco(), contato.getBairro(),
                contato.getCidade(), contato.getEstado())).toList();
        return listarContatos;
    }

    public ListarContato getContato(String id) {
        Contato contato = contatoRepository.findContatoById(id);
        ListarContato listarContato = new ListarContato(contato.getNome(), contato.getEmail(),
                contato.getTelefone(), contato.getEndereco(), contato.getBairro(), contato.getCidade(),
                contato.getEstado());
        return listarContato;
    }

    public Contato deletarContato(String id) {
        Contato contato = contatoRepository.findContatoById(id);
        if (contato != null) {
            contatoRepository.delete(contato);
            return contato;
        }
        return null;
    }
}
