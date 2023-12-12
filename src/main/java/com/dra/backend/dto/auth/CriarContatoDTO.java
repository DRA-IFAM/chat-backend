package com.dra.backend.dto.auth;

import java.util.Optional;

import lombok.*;

@Data
public class CriarContatoDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    public static Optional<String> validarCampos(CriarContatoDTO contato) {
        if (contato.getNome() == null || contato.getNome().isEmpty()) {
            return Optional.of("Nome inválido. Por favor, tente novamente.");
        }
        if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
            return Optional.of("Email inválido. Por favor, tente novamente.");
        }
        if (contato.getSenha() == null || contato.getSenha().isEmpty()) {
            return Optional.of("Senha inválida. Por favor, tente novamente.");
        }
        if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            return Optional.of("Telefone inválido. Por favor, tente novamente.");
        }
        if (contato.getEndereco() == null || contato.getEndereco().isEmpty()) {
            return Optional.of("Endereço inválido. Por favor, tente novamente.");
        }
        if (contato.getBairro() == null || contato.getBairro().isEmpty()) {
            return Optional.of("Bairro inválido. Por favor, tente novamente.");
        }
        if (contato.getCidade() == null || contato.getCidade().isEmpty()) {
            return Optional.of("Cidade inválida. Por favor, tente novamente.");
        }
        if (contato.getEstado() == null || contato.getEstado().isEmpty()) {
            return Optional.of("Estado inválido. Por favor, tente novamente.");
        }
        return Optional.empty();
    }

}