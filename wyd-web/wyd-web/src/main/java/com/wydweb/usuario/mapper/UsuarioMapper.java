package com.wydweb.usuario.mapper;

import com.wydweb.usuario.dto.request.UsuarioRequest;
import com.wydweb.usuario.dto.request.UsuarioUpdateRequest;
import com.wydweb.usuario.dto.response.UsuarioResponse;
import com.wydweb.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setDataNascimento(request.dataNascimento());
        usuario.setEmail(request.email());
        usuario.setLogin(request.login());
        usuario.setSenha(request.senha());

        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getStatus(),
                usuario.getDataCadastro()
        );
    }

    public void updateEntity(
            Usuario usuario,
            UsuarioUpdateRequest request
    ) {
        usuario.setNome(request.nome());
        usuario.setDataNascimento(request.dataNascimento());
        usuario.setEmail(request.email());
        usuario.setLogin(request.login());
    }
}