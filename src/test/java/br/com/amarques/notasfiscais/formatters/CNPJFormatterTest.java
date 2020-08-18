package br.com.amarques.notasfiscais.formatters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.exception.InvalidCNPJException;

class CNPJFormatterTest {

    private static final String CNPJ_NAO_FORMATADO = "27414368000152";
    private static final String CNPJ_FORMATADO = "27.414.368/0001-52";
    private static final String CNPJ_INVALIDO = "1234567890";

    @Test
    void shouldFormatTheCNPJ() {
        assertThat(CNPJFormatter.format(CNPJ_NAO_FORMATADO), is(equalTo(CNPJ_FORMATADO)));
    }

    @Test
    void shouldThrowInvalidCNPJException() {
        assertThrows(InvalidCNPJException.class, () -> CNPJFormatter.format(CNPJ_INVALIDO));
    }
}
