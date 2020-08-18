package br.com.amarques.notasfiscais.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;

class NotaFiscalTest {

    private static final Integer NUMERO_NF = 12345;
    private static final LocalDate DATA_NF = LocalDate.of(2020, 8, 18);
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;

    @Test
    void shouldCreateNotaFiscal() {
        Empresa empresaPrestadora = new Empresa("Razão Social", "27.414.368/0001-52", TipoEmpresaEnum.PRESTADOR);
        Empresa empresaTomadora = new Empresa("Razão Social", "27.414.368/0001-52", TipoEmpresaEnum.TOMADOR);

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
