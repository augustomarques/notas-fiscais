package br.com.amarques.notasfiscais.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CNPJValidateTest {

    private static final String CNPJ_VALIDO = "27.414.368/0001-52";
    private static final String CNPJ_INVALIDO = "27.414.368/0001-00";

    @Test
    void shouldReturnThatTheCnpjIsValid() {
        assertTrue(CNPJValidate.isValid(CNPJ_VALIDO));
    }

    @Test
    void shouldReturnThatTheCnpjIsInvalid() {
        assertFalse(CNPJValidate.isValid(CNPJ_INVALIDO));
    }
}
