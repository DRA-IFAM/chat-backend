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

    public List<Mensagem> verMensagens(String emailEmissor) {
        Optional<Contato> emissor = contatoRepository.findByEmail(emailEmissor);
        if (emissor.isEmpty()) {
            return null;
        }
        List<Mensagem> mensagens = mensagemRepository.findByEmissor(emissor.get());
        return mensagens;
    }

    public Mensagem enviarMensagem(String emailEmissor, String emailRecepetor, String assunto, String conteudo) {
        Optional<Contato> emissor = contatoRepository.findByEmail(emailEmissor);
        Optional<Contato> receptor = contatoRepository.findByEmail(emailRecepetor);
        Mensagem mensagem = new Mensagem(emissor.get(), receptor.get(), assunto, conteudo);
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> verMensagens(String emailEmissor, String emailReceptor) {
        Optional<Contato> emissor = contatoRepository.findByEmail(emailEmissor);
        Optional<Contato> receptor = contatoRepository.findByEmail(emailReceptor);
        List<Mensagem> mensagens = mensagemRepository.findAllByReceptorAndEmissor(receptor.get(), emissor.get());
        return mensagens;
    }

    public Mensagem deletarMensagem(String emailEmissor, Long idMensagem) {
        Optional<Mensagem> mensagem = mensagemRepository.findById(idMensagem);
        if (mensagem.isEmpty()) {
            return null;
        }
        if (!mensagem.get().getEmissor().getEmail().equals(emailEmissor)) {
            throw new RuntimeException("Você não tem permissão para deletar essa mensagem.");
        }
        mensagemRepository.delete(mensagem.get());
        return mensagem.get();
    }
}