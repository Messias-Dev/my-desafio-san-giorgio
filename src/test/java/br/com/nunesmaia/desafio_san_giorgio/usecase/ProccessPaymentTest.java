package br.com.nunesmaia.desafio_san_giorgio.usecase;

import br.com.nunesmaia.desafio_san_giorgio.database.BillingRepository;
import br.com.nunesmaia.desafio_san_giorgio.database.SellerRepository;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.dtos.PaymentProccessDTO;
import br.com.nunesmaia.desafio_san_giorgio.domain.entity.Billing;
import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import br.com.nunesmaia.desafio_san_giorgio.exception.BillingsNotFoundException;
import br.com.nunesmaia.desafio_san_giorgio.exception.SellerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProccessPaymentTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private SqsService sqsService;

    @InjectMocks
    private ProccessPayment proccessPayment;

    private HashSet<PaymentDTO> payments;


    @BeforeEach
    void setUp() {
        // given
        payments = new HashSet<>();
    }

    @Nested
    class WhenSuccess {

        private static final Double BILLING_PRICE = 50D;

        @Test
        void whenTotalPayment() {
            var response = test(50D, StatusEnum.TOTAL);
            assertEquals(StatusEnum.TOTAL, response.stream().findFirst().get().getStatus());
        }

        @Test
        void whenPartialPayment() {
            var response = test(40D, StatusEnum.PARTIAL);
            assertEquals(StatusEnum.PARTIAL, response.stream().findFirst().get().getStatus());
        }

        @Test
        void whenExcessPayment() {
            var response = test(60D, StatusEnum.EXCESS);
            assertEquals(StatusEnum.EXCESS, response.stream().findFirst().get().getStatus());
        }

        private Set<PaymentDTO> test(Double paymentPrice, StatusEnum expectedStatus) {
            // given
            payments.add(new PaymentDTO(1L, paymentPrice));

            var dto = new PaymentProccessDTO(1L, payments);

            var billings = Set.of(new Billing(1L, 50D));

            // when
            when(sellerRepository.existsById(any())).thenReturn(true);
            when(billingRepository.findByIdIn(any())).thenReturn(billings);

            var response = assertDoesNotThrow(() -> proccessPayment.execute(dto));

            // then
            assert response.size() == 1;
            return response;
        }
    }

    @Test
    void whenSellerNotExistsThrowsSellerNotFoundException() {

        // given
        payments.add(new PaymentDTO(1L, 50D));

        var dto = new PaymentProccessDTO(1L, payments);

        // when
        when(sellerRepository.existsById(any())).thenReturn(false);

        assertThrows(SellerNotFoundException.class, () -> proccessPayment.execute(dto));

        // then
        verifyNoInteractions(billingRepository);
        verifyNoInteractions(sqsService);
    }

    @Test
    void whenBillingsSizeDifferentBillingsNotFoundException() {

        // given
        payments.add(new PaymentDTO(1L, 50D));
        payments.add(new PaymentDTO(2L, 40D));

        var dto = new PaymentProccessDTO(1L, payments);

        var billings = Set.of(new Billing(1L, 50D));

        // when
        when(sellerRepository.existsById(any())).thenReturn(true);
        when(billingRepository.findByIdIn(any())).thenReturn(billings);

        assertThrows(BillingsNotFoundException.class, () -> proccessPayment.execute(dto));

        // then
        verifyNoInteractions(sqsService);
    }


}