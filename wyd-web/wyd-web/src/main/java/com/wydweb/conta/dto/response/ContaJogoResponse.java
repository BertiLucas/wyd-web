package com.wydweb.conta.dto.response;

import com.wydweb.conta.entity.StatusContaJogo;

import java.time.LocalDateTime;

public record ContaJogoResponse(
        Long id,
        String login,
        StatusContaJogo status,
        LocalDateTime dataCriacao
) {
}