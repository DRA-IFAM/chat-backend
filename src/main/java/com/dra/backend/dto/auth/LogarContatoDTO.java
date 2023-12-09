package com.dra.backend.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LogarContatoDTO {
    private String email;
    private String senha;

    private LogarContatoDTO(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    static LogarContatoDTO from(String username, String password) {
        return new LogarContatoDTO(username, password);
    }

    public String validateFields() {
        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            return "Dados inválidos. Por favor, tente novamente.";
        }
        return null;
    }

}
