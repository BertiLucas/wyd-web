package com.wydweb.conta.controller;

import com.wydweb.conta.dto.request.ContaJogoRequest;
import com.wydweb.conta.dto.request.ContaJogoStatusRequest;
import com.wydweb.conta.dto.request.ContaJogoUpdateRequest;
import com.wydweb.conta.dto.response.ContaJogoResponse;
import com.wydweb.conta.service.ContaJogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/contas-jogo")
@RequiredArgsConstructor
public class ContaJogoController {

    private final ContaJogoService contaJogoService;

    @PostMapping
    public ResponseEntity<ContaJogoResponse> criar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody ContaJogoRequest request
    ) {

        ContaJogoResponse response =
                contaJogoService.criar(usuarioId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContaJogoResponse>> listarPorUsuario(
            @PathVariable Long usuarioId
    ) {
        List<ContaJogoResponse> response =
                contaJogoService.listarPorUsuario(usuarioId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contaId}")
    public ResponseEntity<ContaJogoResponse> buscarPorId(
            @PathVariable Long usuarioId,
            @PathVariable Long contaId
    ) {

        ContaJogoResponse response =
                contaJogoService.buscarPorId(usuarioId, contaId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{contaId}")
    public ResponseEntity<ContaJogoResponse> atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long contaId,
            @Valid @RequestBody ContaJogoUpdateRequest request
    ) {

        ContaJogoResponse response =
                contaJogoService.atualizar(
                        usuarioId,
                        contaId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{contaId}/status")
    public ResponseEntity<ContaJogoResponse> alterarStatus(
            @PathVariable Long usuarioId,
            @PathVariable Long contaId,
            @Valid @RequestBody ContaJogoStatusRequest request
    ) {

        ContaJogoResponse response =
                contaJogoService.alterarStatus(
                        usuarioId,
                        contaId,
                        request
                );

        return ResponseEntity.ok(response);
    }

}