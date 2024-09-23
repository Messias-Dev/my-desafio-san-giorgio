package br.com.nunesmaia.desafio_san_giorgio.exception;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
        super("Vendedor não encontrado");
    }
}
