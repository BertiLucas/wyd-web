package com.wydweb.conta.mapper;

import com.wydweb.conta.dto.response.ContaJogoResponse;
import com.wydweb.conta.entity.ContaJogo;
import org.springframework.stereotype.Component;

@Component
public class ContaJogoMapper {

    public ContaJogoResponse toResponse(ContaJogo contaJogo) {

        return new ContaJogoResponse(
                contaJogo.getId(),
                contaJogo.getLogin(),
                contaJogo.getStatus(),
                contaJogo.getDataCriacao()
        );
    }
}