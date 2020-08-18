package br.com.amarques.notasfiscais.repository;

import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.PRESTADOR;
import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.TOMADOR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;
import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Test
    void shouldFindEmpresaByCNPJ() {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        empresaRepository.save(empresa);

        Optional<Empresa> empresaEncontrada = empresaRepository.findByCnpj(CNPJ);

        assertTrue(empresaEncontrada.isPresent());
        assertThat(empresaEncontrada.get().getCnpj(), is(equalTo(CNPJ)));
    }

    @Test
    void shouldReturnFalseWhenEmpresaHasNotaFiscal() {
        Empresa empresaPrestadora = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
        Empresa empresaTomadora = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);

        empresaRepository.save(empresaPrestadora);
        empresaRepository.save(empresaTomadora);

        NotaFiscal notaFiscal = new NotaFiscal(NUMERO_NF, DATA_NF, VALOR_NF);
        notaFiscal.setPrestador(empresaPrestadora);
        notaFiscal.setTomador(empresaTomadora);

        notaFiscalRepository.save(notaFiscal);

        boolean canBeRemovedPrestadora = empresaRepository.canBeRemoved(empresaPrestadora);
        boolean canBeRemovedTomadora = empresaRepository.canBeRemoved(empresaTomadora);

        assertFalse(canBeRemovedPrestadora);
        assertFalse(canBeRemovedTomadora);
    }

    @Test
    void shouldReturnTrueWhenEmpresaDontHaveNotaFiscal() {
        Empresa empresaPrestadora = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
        Empresa empresaTomadora = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);

        empresaRepository.save(empresaPrestadora);
        empresaRepository.save(empresaTomadora);

        boolean canBeRemovedPrestadora = empresaRepository.canBeRemoved(empresaPrestadora);
        boolean canBeRemovedTomadora = empresaRepository.canBeRemoved(empresaTomadora);

        assertTrue(canBeRemovedPrestadora);
        assertTrue(canBeRemovedTomadora);
    }

    private static final String RAZAO_SOCIAL = "Razão Social";
    private static final String CNPJ = "27.414.368/0001-52";
    private static final TipoEmpresaEnum TIPO_EMPRESA = TipoEmpresaEnum.PRESTADOR;

    private static final Integer NUMERO_NF = 12345;
    private static final LocalDate DATA_NF = LocalDate.of(2020, 8, 18);
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;
}
