package com.wydweb.conta.service;

import com.wydweb.common.exception.BusinessException;
import com.wydweb.common.exception.ResourceNotFoundException;
import com.wydweb.conta.dto.request.ContaJogoRequest;
import com.wydweb.conta.dto.request.ContaJogoStatusRequest;
import com.wydweb.conta.dto.request.ContaJogoUpdateRequest;
import com.wydweb.conta.dto.response.ContaJogoResponse;
import com.wydweb.conta.entity.ContaJogo;
import com.wydweb.conta.entity.StatusContaJogo;
import com.wydweb.conta.mapper.ContaJogoMapper;
import com.wydweb.conta.repository.ContaJogoRepository;
import com.wydweb.usuario.entity.Usuario;
import com.wydweb.usuario.entity.StatusUsuario;
import com.wydweb.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaJogoService {

    private final ContaJogoRepository contaJogoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContaJogoMapper contaJogoMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ContaJogoResponse criar(
            Long usuarioId,
            ContaJogoRequest request
    ) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        if (usuario.getStatus() != StatusUsuario.ATIVO) {
            throw new BusinessException(
                    "Usuário está inativo e não pode criar contas de jogo."
            );
        }

        long quantidadeContas =
                contaJogoRepository.countByUsuarioIdAndStatus(
                        usuarioId,
                        StatusContaJogo.ATIVA
                );

        if (quantidadeContas >= 5) {
            throw new BusinessException(
                    "O usuário já possui o limite de 5 contas de jogo ativas."
            );
        }

        if (contaJogoRepository.existsByLogin(request.login())) {
            throw new BusinessException(
                    "Login da conta de jogo já cadastrado."
            );
        }

        ContaJogo contaJogo = new ContaJogo();

        contaJogo.setLogin(request.login());

        contaJogo.setSenha(
                passwordEncoder.encode(request.senha())
        );

        contaJogo.setStatus(StatusContaJogo.ATIVA);
        contaJogo.setDataCriacao(LocalDateTime.now());
        contaJogo.setUsuario(usuario);

        contaJogoRepository.save(contaJogo);

        return contaJogoMapper.toResponse(contaJogo);
    }

    @Transactional(readOnly = true)
    public List<ContaJogoResponse> listarPorUsuario(Long usuarioId) {

        usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        return contaJogoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(contaJogoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaJogoResponse buscarPorId(
            Long usuarioId,
            Long contaId
    ) {

        ContaJogo contaJogo = contaJogoRepository
                .findByIdAndUsuarioId(contaId, usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conta de jogo não encontrada."
                        )
                );

        return contaJogoMapper.toResponse(contaJogo);
    }

    @Transactional
    public ContaJogoResponse atualizar(
            Long usuarioId,
            Long contaId,
            ContaJogoUpdateRequest request
    ) {

        ContaJogo contaJogo = contaJogoRepository
                .findByIdAndUsuarioId(contaId, usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conta de jogo não encontrada."
                        )
                );

        contaJogo.setSenha(
                passwordEncoder.encode(request.senha())
        );

        contaJogoRepository.save(contaJogo);

        return contaJogoMapper.toResponse(contaJogo);
    }

    @Transactional
    public ContaJogoResponse alterarStatus(
            Long usuarioId,
            Long contaId,
            ContaJogoStatusRequest request
    ) {

        ContaJogo contaJogo = contaJogoRepository
                .findByIdAndUsuarioId(contaId, usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conta de jogo não encontrada."
                        )
                );

        StatusContaJogo novoStatus = request.status();

        if (contaJogo.getStatus() == novoStatus) {
            throw new BusinessException(
                    "A conta de jogo já está com esse status."
            );
        }

        if (novoStatus == StatusContaJogo.ATIVA) {

            long quantidadeContasAtivas =
                    contaJogoRepository.countByUsuarioIdAndStatus(
                            usuarioId,
                            StatusContaJogo.ATIVA
                    );

            if (quantidadeContasAtivas >= 5) {
                throw new BusinessException(
                        "O usuário já possui o limite de 5 contas de jogo ativas."
                );
            }
        }

        contaJogo.setStatus(novoStatus);

        contaJogoRepository.save(contaJogo);

        return contaJogoMapper.toResponse(contaJogo);
    }

}