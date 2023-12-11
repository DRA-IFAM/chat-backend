package com.dra.backend.models.responses;

import com.dra.backend.models.entities.Contato;

import lombok.Data;

@Data
public class ListarContato {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    private ListarContato(Contato contato) {
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.telefone = contato.getTelefone();
        this.endereco = contato.getEndereco();
        this.bairro = contato.getBairro();
        this.cidade = contato.getCidade();
        this.estado = contato.getEstado();
    }

    public static ListarContato from(Contato contato) {
        return new ListarContato(contato);
    }
}
