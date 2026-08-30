package com.wydweb.conta.repository;

import com.wydweb.conta.entity.ContaJogo;
import com.wydweb.conta.entity.StatusContaJogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaJogoRepository extends JpaRepository<ContaJogo, Long> {

    long countByUsuarioIdAndStatus(
            Long usuarioId,
            StatusContaJogo status
    );

    boolean existsByLogin(String login);

    List<ContaJogo> findByUsuarioId(Long usuarioId);

    Optional<ContaJogo> findByIdAndUsuarioId(
            Long id,
            Long usuarioId
    );
}