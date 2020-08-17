package br.com.amarques.notasfiscais.exception;

public class DeleteEmpresaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeleteEmpresaException(String message) {
        super(message);
    }
}
