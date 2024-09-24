package br.com.nunesmaia.desafio_san_giorgio.usecase;

import br.com.nunesmaia.desafio_san_giorgio.config.SqsConfig;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsService {

    private final SqsClient sqsClient;
    private final SqsConfig sqsConfig;

    public void sendMessage(PaymentDTO payment) {

        String queueUrl = this.sqsConfig.getQueueUrl(payment.getStatus());
        String messageBody = String.format(
                "{\"billingId\":%d, \"price\":%.2f, \"status\":\"%s\"}",
                payment.getBillingId(), payment.getPrice(), payment.getStatus()
        );

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        log.info("enviando objeto para fila " + sendMsgRequest.queueUrl() + " : {}", sendMsgRequest.messageBody());
        this.sqsClient.sendMessage(sendMsgRequest);
    }

}
