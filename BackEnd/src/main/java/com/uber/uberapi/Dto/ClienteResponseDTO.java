package com.uber.uberapi.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Response é a resposta ou o resultado dessa solicitação
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
}
