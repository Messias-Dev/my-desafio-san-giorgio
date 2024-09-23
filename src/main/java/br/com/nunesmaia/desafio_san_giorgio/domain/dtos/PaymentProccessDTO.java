package br.com.nunesmaia.desafio_san_giorgio.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@Getter
@RequiredArgsConstructor
public class PaymentProccessDTO {

    @NotNull(message = "Vendedor vazio")
    private Long sellerId;

    @NotEmpty(message = "Lista vazia")
    private HashSet<PaymentDTO> payments;
}
