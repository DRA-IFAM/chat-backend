package com.dra.backend.dto.auth;

import java.util.Optional;

import lombok.*;

@Data
public class LogarContatoDTO {
    private String email;
    private String senha;

    public static Optional<String> validarCampos(LogarContatoDTO usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return Optional.of("Email inválido. Por favor, tente novamente.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            return Optional.of("Senha inválida. Por favor, tente novamente.");
        }
        return Optional.empty();
    }

}
