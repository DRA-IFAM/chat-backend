package com.dra.backend.models.responses;

import com.dra.backend.models.entities.Contato;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListarContato {
    private String nome;
    private String email;
    private String telefone;
    private String endereço;
    private String bairro;
    private String cidade;
    private String estado;

    public ListarContato(Contato contato) {
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.telefone = contato.getTelefone();
        this.endereço = contato.getEndereco();
        this.bairro = contato.getBairro();
        this.cidade = contato.getCidade();
        this.estado = contato.getEstado();
    }
}
