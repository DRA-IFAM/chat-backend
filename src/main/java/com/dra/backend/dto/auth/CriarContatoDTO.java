package com.dra.backend.dto.auth;

import com.dra.backend.models.entities.Contato;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriarContatoDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    public static Contato from(CriarContatoDTO contato) {
        return new Contato(contato.getNome(), contato.getEmail(), contato.getSenha(), contato.getTelefone(),
                contato.getEndereco(), contato.getBairro(), contato.getCidade(), contato.getEstado());

    }

    public String validateFields() {
        if (nome == null || email == null || senha == null) {
            return "Dados inválidos. Por favor, tente novamente.";
        }
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            return "Dados inválidos. Por favor, tente novamente.";
        }
        return null;
    }

}