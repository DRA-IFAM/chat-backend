package com.dra.backend.dto.contato;

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
}
