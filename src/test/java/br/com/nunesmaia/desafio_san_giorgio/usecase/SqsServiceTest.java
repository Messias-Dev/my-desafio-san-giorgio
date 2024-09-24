package br.com.nunesmaia.desafio_san_giorgio.usecase;

import br.com.nunesmaia.desafio_san_giorgio.config.SqsConfig;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqsServiceTest {

    @Mock
    private SqsClient sqsClient;

    @Mock
    private SqsConfig sqsConfig;

    @InjectMocks
    private SqsService service;

    @Test
    void whenSuccess() {
        // given
        var dto = new PaymentDTO(1L, 50D);
        dto.setStatus(StatusEnum.TOTAL);

        var expectedRequest = SendMessageRequest.builder()
                .queueUrl("url")
                .messageBody("{\"billingId\":1, \"price\":50.00, \"status\":\"TOTAL\"}")
                .build();

        // when
        when(this.sqsConfig.getQueueUrl(any())).thenReturn("url");

        this.service.sendMessage(dto);

        // then
        verify(sqsClient).sendMessage(expectedRequest);
    }
}