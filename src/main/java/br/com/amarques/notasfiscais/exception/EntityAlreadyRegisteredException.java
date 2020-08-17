package br.com.amarques.notasfiscais.exception;

public class EntityAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyRegisteredException(String message) {
        super(message);
    }
}
