package com.wydweb.conta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContaJogoRequest(

        @NotBlank(message = "O login é obrigatório.")
        @Size(min = 4, max = 50, message = "O login deve possuir entre 4 e 50 caracteres.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255, message = "A senha deve possuir no mínimo 6 caracteres.")
        String senha

) {
}