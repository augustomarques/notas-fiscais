package br.com.amarques.notasfiscais.exception;

public class InvalidNotaFiscalEmpresaTipoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidNotaFiscalEmpresaTipoException(String message) {
        super(message);
    }
}
