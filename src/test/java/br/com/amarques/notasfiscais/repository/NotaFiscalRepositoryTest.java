package br.com.amarques.notasfiscais.repository;

import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.PRESTADOR;
import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.TOMADOR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class NotaFiscalRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Test
    void shouldReturnAllNotasFiscaisOfTheEmpresa() {
        Empresa empresaPrestadora = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
        Empresa empresaTomadora = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);

        empresaRepository.save(empresaPrestadora);
        empresaRepository.save(empresaTomadora);

        NotaFiscal notaFiscalNumero1 = new NotaFiscal(1, LocalDate.of(2020, 8, 15), new BigDecimal(1000));
        notaFiscalNumero1.setPrestador(empresaPrestadora);
        notaFiscalNumero1.setTomador(empresaTomadora);

        NotaFiscal notaFiscalNumero2 = new NotaFiscal(2, LocalDate.of(2020, 8, 16), new BigDecimal(2000));
        notaFiscalNumero2.setPrestador(empresaPrestadora);
        notaFiscalNumero2.setTomador(empresaTomadora);

        notaFiscalRepository.save(notaFiscalNumero1);
        notaFiscalRepository.save(notaFiscalNumero2);

        List<NotaFiscal> notasDaEmpresaTomadora = notaFiscalRepository.findAllByEmpresa(empresaTomadora);
        List<NotaFiscal> notasDaEmpresaPrestadora = notaFiscalRepository.findAllByEmpresa(empresaPrestadora);

        assertThat(notasDaEmpresaTomadora.size(), is(equalTo(2)));
        assertThat(notasDaEmpresaPrestadora.size(), is(equalTo(2)));
    }
}
