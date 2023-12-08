package com.dra.backend.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriarContatoDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereço;
    private String bairro;
    private String cidade;
    private String estado;

    private CriarContatoDTO(String nome, String email, String senha, String telefone, String endereço, String bairro,
            String cidade, String estado) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
        this.setEndereço(endereço);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setEstado(estado);

    }

    public static CriarContatoDTO from(String nome, String email, String senha, String telefone, String endereço,
            String bairro, String cidade, String estado) {
        return new CriarContatoDTO(nome, email, senha, telefone, endereço, bairro, cidade, estado);
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

    public Object getUsername() {
        return null;
    }

    public Object getPassword() {
        return null;
    }

}