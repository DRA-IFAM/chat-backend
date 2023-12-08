package com.dra.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.backend.models.entities.Contato;
import com.dra.backend.models.responses.ListarContato;
import com.dra.backend.services.ContatoService;

@RequestMapping("/api")
@RestController
public class ContatoController {
    @Autowired
    ContatoService contatoService;

    @GetMapping("/contato")
    ResponseEntity<List<ListarContato>> getContatos() {
        List<ListarContato> contatos = contatoService.listarContatos();
        return ResponseEntity.ok(contatos);

    }

    @GetMapping("/contato/{id}")
    ResponseEntity<ListarContato> getContatoById(@PathVariable String id) {
        Optional<ListarContato> contato = contatoService.listarContato(id);
        if (!contato.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(contato.get());
    }

    @DeleteMapping("/contato/{id}")
    ResponseEntity<Contato> deleteContato(@PathVariable String id) {
        Optional<Contato> contato = contatoService.deletarContato(id);
        if (!contato.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().build();
    }

}
