package br.com.nunesmaia.desafio_san_giorgio.http;

import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentProccessDTO;
import br.com.nunesmaia.desafio_san_giorgio.usecase.ProccessPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "/payments")
@RequiredArgsConstructor
public class PaymentWS {

    private final ProccessPayment proccessPayment;

    @PostMapping()
    public ResponseEntity<Set<PaymentDTO>> proccessPayment(@Valid @RequestBody PaymentProccessDTO dto) {
        return ResponseEntity.ok().body(this.proccessPayment.execute(dto));
    }
}
