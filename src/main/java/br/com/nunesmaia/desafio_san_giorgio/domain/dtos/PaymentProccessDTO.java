package br.com.nunesmaia.desafio_san_giorgio.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProccessDTO {

    @NotNull(message = "vVendedor vazio")
    private Long sellerId;

    @NotEmpty(message = "Lista vazia")
    private HashSet<PaymentDTO> payments;
}
