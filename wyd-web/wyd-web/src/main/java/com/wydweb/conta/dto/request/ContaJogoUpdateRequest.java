package com.wydweb.conta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContaJogoUpdateRequest(

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255, message = "A senha deve possuir no mínimo 6 caracteres.")
        String senha

) {
}