package com.wydweb.conta.dto.request;

import com.wydweb.conta.entity.StatusContaJogo;
import jakarta.validation.constraints.NotNull;

public record ContaJogoStatusRequest(

        @NotNull(message = "O status é obrigatório.")
        StatusContaJogo status

) {
}