package br.com.amarques.notasfiscais.mapper;

import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.PRESTADOR;
import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.TOMADOR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;
import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;

class NotaFiscalMapperTest {

    private static final Long ID = 12321L;
    private static final Integer NUMERO_NF = 12345;
    private static final LocalDate DATA_NF = LocalDate.of(2020, 8, 18);
    private static final String DATA_NF_TEXT = "18/08/2020";
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;

    private static final Empresa PRESTADORA = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
    private static final Empresa TOMADORA = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);

    @Test
    void shouldConvertDTOToEntity() {
        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, 444L, 555L);

        NotaFiscal notaFiscal = NotaFiscalMapper.toEntity(dto, TOMADORA, PRESTADORA);

        assertNotNull(notaFiscal);
        assertNull(notaFiscal.getId());
        assertThat(notaFiscal.getNumero(), is(equalTo(NUMERO_NF)));
        assertThat(notaFiscal.getData(), is(equalTo(DATA_NF)));
        assertThat(notaFiscal.getPrestador(), is(equalTo(PRESTADORA)));
        assertThat(notaFiscal.getTomador(), is(equalTo(TOMADORA)));
        assertThat(notaFiscal.getValor(), is(equalTo(VALOR_NF)));
    }

    @Test
    void shouldConvertEntityToDTO() {
        PRESTADORA.setId(9999L);
        TOMADORA.setId(8888L);

        NotaFiscal notaFiscal = new NotaFiscal(NUMERO_NF, DATA_NF, VALOR_NF);
        notaFiscal.setId(ID);
        notaFiscal.setPrestador(PRESTADORA);
        notaFiscal.setTomador(TOMADORA);

        NotaFiscalDTO dto = NotaFiscalMapper.toDTO(notaFiscal);

        assertNotNull(dto);
        assertThat(dto.id, is(equalTo(ID)));
        assertThat(dto.data, is(equalTo(DATA_NF_TEXT)));
        assertThat(dto.numero, is(equalTo(NUMERO_NF)));
        assertThat(dto.prestadorId, is(equalTo(9999L)));
        assertThat(dto.tomadorId, is(equalTo(8888L)));
        assertThat(dto.valor, is(equalTo(VALOR_NF)));
    }
}
