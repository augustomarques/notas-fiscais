package br.com.amarques.notasfiscais.domain;

import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.PRESTADOR;
import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.TOMADOR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class NotaFiscalTest {

    private static final Integer NUMERO_NF = 12345;
    private static final LocalDate DATA_NF = LocalDate.of(2020, 8, 18);
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;

    @Test
    void shouldCreateNotaFiscal() {
        Empresa empresaPrestadora = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
        Empresa empresaTomadora = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);

        NotaFiscal notaFiscal = new NotaFiscal(NUMERO_NF, DATA_NF, VALOR_NF);
        notaFiscal.setPrestador(empresaPrestadora);
        notaFiscal.setTomador(empresaTomadora);

        assertNotNull(notaFiscal);

        assertThat(notaFiscal.getNumero(), is(equalTo(NUMERO_NF)));
        assertThat(notaFiscal.getData(), is(equalTo(DATA_NF)));
        assertThat(notaFiscal.getValor(), is(equalTo(VALOR_NF)));
        assertThat(notaFiscal.getPrestador(), is(equalTo(empresaPrestadora)));
        assertThat(notaFiscal.getTomador(), is(equalTo(empresaTomadora)));
    }
}
