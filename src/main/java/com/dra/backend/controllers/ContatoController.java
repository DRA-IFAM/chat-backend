package com.dra.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.backend.dto.contato.ListarContato;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.services.ContatoService;

@RequestMapping("/api")
@RestController
public class ContatoController {
    @Autowired
    ContatoService contatoService;

    @GetMapping("/contato")
    ResponseEntity<List<ListarContato>> getContatos() {
        List<ListarContato> contatos = contatoService.getContatos();
        return ResponseEntity.ok(contatos);

    }

    @GetMapping("/contato/{id}")
    ResponseEntity<ListarContato> getContatoById(@PathVariable String id) {
        ListarContato contato = contatoService.getContato(id);
        if (contato == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contato);
        return ResponseEntity.ok(contato);
    }

    @DeleteMapping("/contato/{id}")
    ResponseEntity<Contato> deleteContato(@PathVariable String id) {
        Contato contato = contatoService.deletarContato(id);
        if (contato == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contato);
        return ResponseEntity.ok().build();
    }

}
