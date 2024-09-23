package br.com.nunesmaia.desafio_san_giorgio.usecase;

import br.com.nunesmaia.desafio_san_giorgio.database.repository.BillingRepository;
import br.com.nunesmaia.desafio_san_giorgio.database.repository.SellerRepository;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentProccessDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.entity.Billing;
import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import br.com.nunesmaia.desafio_san_giorgio.exception.BillingsNotFoundException;
import br.com.nunesmaia.desafio_san_giorgio.exception.SellerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProccessPayment {

    private final SellerRepository sellerRepository;
    private final BillingRepository billingRepository;
    private final SQSService sqsService;

    public Set<PaymentDTO> execute(PaymentProccessDTO dto) {

        log.info("verificando existencia de vendedor com ID: {}", dto.getSellerId());

        if (!this.sellerRepository.existsById(dto.getSellerId())) {
            throw new SellerNotFoundException();
        }

        var dtoBillingIds = dto.getPayments().stream().map(PaymentDTO::getBillingId).collect(Collectors.toSet());

        log.info("buscando cobranças por IDs");
        var billings = this.billingRepository.findByIdIn(dtoBillingIds);

        // Mapeia os IDs e precos dos Billings
        Map<Long, Double> billingMap = billings.stream()
                .collect(Collectors.toMap(Billing::getId, Billing::getPrice));

        if (billings.size() != dtoBillingIds.size()) {

            var missingBillingIds = dtoBillingIds.stream()
                    .filter(id -> !billingMap.containsKey(id))
                    .collect(Collectors.toSet());

            throw new BillingsNotFoundException(missingBillingIds);
        }

        var response = new HashSet<PaymentDTO>();

        for (PaymentDTO payment : dto.getPayments()) {
            Double originalPrice = billingMap.get(payment.getBillingId());

            if (payment.getPrice().equals(originalPrice)) {
                payment.setStatus(StatusEnum.TOTAL);
            } else if (payment.getPrice() < originalPrice) {
                payment.setStatus(StatusEnum.PARTIAL);
            } else {
                payment.setStatus(StatusEnum.EXCESS);
            }

            this.sqsService.sendMessage(payment);

            response.add(payment);
        }

        return response;
    }

}
