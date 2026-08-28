package com.wydweb.usuario.service;

import com.wydweb.common.exception.BusinessException;
import com.wydweb.common.exception.ResourceNotFoundException;
import com.wydweb.usuario.dto.request.UsuarioRequest;
import com.wydweb.usuario.dto.request.UsuarioUpdateRequest;
import com.wydweb.usuario.dto.response.UsuarioResponse;
import com.wydweb.usuario.entity.StatusUsuario;
import com.wydweb.usuario.entity.Usuario;
import com.wydweb.usuario.mapper.UsuarioMapper;
import com.wydweb.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado.");
        }

        if (usuarioRepository.existsByLogin(request.login())) {
            throw new BusinessException("Login já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(request);

        usuario.setSenha(passwordEncoder.encode(request.senha()));

        usuario.setStatus(StatusUsuario.ATIVO);

        usuario.setDataCadastro(LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        return usuarioMapper.toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse atualizar(
            Long id,
            UsuarioUpdateRequest request
    ) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        if (!usuario.getEmail().equals(request.email())
                && usuarioRepository.existsByEmail(request.email())) {

            throw new BusinessException("E-mail já cadastrado.");
        }

        if (!usuario.getLogin().equals(request.login())
                && usuarioRepository.existsByLogin(request.login())) {

            throw new BusinessException("Login já cadastrado.");
        }

        usuarioMapper.updateEntity(usuario, request);

        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public void excluir(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        usuarioRepository.delete(usuario);
    }

}