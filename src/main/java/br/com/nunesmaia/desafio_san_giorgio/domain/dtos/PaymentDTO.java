package br.com.nunesmaia.desafio_san_giorgio.domain.dtos;

import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long billingId;
    private Double price;

    @Setter
    private StatusEnum status;


}
