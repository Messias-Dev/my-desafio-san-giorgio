package br.com.nunesmaia.desafio_san_giorgio.exception;

import java.util.Set;

public class BillingsNotFoundException extends RuntimeException {

    public BillingsNotFoundException(Set<Long> missingBillingIds) {
        super("Os seguintes IDs de cobrança não foram encontrados: " + missingBillingIds);
    }
}
