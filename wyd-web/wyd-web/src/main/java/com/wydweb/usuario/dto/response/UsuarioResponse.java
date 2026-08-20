package com.wydweb.usuario.dto.response;

import com.wydweb.usuario.entity.StatusUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponse(

        Long id,
        String nome,
        LocalDate dataNascimento,
        String email,
        String login,
        StatusUsuario status,
        LocalDateTime dataCadastro

) {
}