package com.dra.backend.dto.mensagem;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensagemDTO {
    Long id;
    String emissor;
    String receptor;
    String conteudo;
    Date data;
}
