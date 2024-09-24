package br.com.nunesmaia.desafio_san_giorgio.domain.dtos;

import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class PaymentDTO {

    @NotNull(message = "Cobranca vazia")
    private Long billingId;

    @NotNull(message = "Valor vazio")
    private Double price;

    @Setter
    private StatusEnum status;


    public PaymentDTO(Long billingId, Double price) {
        this.billingId = billingId;
        this. price = price;
    }
}
