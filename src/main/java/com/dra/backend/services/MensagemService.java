package com.dra.backend.services;

import java.util.List;
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
        Contato emissor = contatoRepository.findContatoById(idEmissor);
        return mensagemRepository.findByEmissor(emissor);
    }

    public Mensagem enviarMensagem(String idEmissor, String idRecepetor, String conteudo) {
        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(conteudo);
        mensagem.setEmissor(contatoRepository.findContatoById(idEmissor));
        mensagem.setReceptor(contatoRepository.findByEmail(idRecepetor));
        return mensagemRepository.save(mensagem);
    }

    public Mensagem verMensagem(String idEmissor, String idReceptor) {
        Contato emissor = contatoRepository.findContatoById(idEmissor);
        Contato receptor = contatoRepository.findContatoById(idReceptor);
        Mensagem mensagem = mensagemRepository.findByReceptorAndEmissor(receptor, emissor);
        return mensagem;
    }

}