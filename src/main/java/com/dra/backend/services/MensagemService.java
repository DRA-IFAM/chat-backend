package com.dra.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.entities.Mensagem;
import com.dra.backend.persistency.ContatoRepository;
import com.dra.backend.persistency.MensagemRepository;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    public List<Mensagem> verMensagens(String idEmissor) {
        Optional<Contato> emissor = contatoRepository.findById(idEmissor);
        if (emissor.isEmpty()) {
            return null;
        }
        List<Mensagem> mensagens = mensagemRepository.findByEmissor(emissor.get());
        return mensagens;
    }

    public Mensagem enviarMensagem(String idEmissor, String idRecepetor, String assunto, String conteudo) {
        Optional<Contato> emissor = contatoRepository.findById(idEmissor);
        Optional<Contato> receptor = contatoRepository.findById(idRecepetor);
        System.out.println(emissor);
        Mensagem mensagem = new Mensagem(emissor.get(), receptor.get(), assunto, conteudo);
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> verMensagens(String idEmissor, String idReceptor) {
        Optional<Contato> emissor = contatoRepository.findById(idEmissor);
        Optional<Contato> receptor = contatoRepository.findById(idReceptor);
        List<Mensagem> mensagens = mensagemRepository.findAllByReceptorAndEmissor(receptor.get(), emissor.get());
        return mensagens;
    }

}