package br.com.amarques.notasfiscais.service;

import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.PRESTADOR;
import static br.com.amarques.notasfiscais.enums.TipoEmpresaEnum.TOMADOR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;
import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;
import br.com.amarques.notasfiscais.exception.InvalidNotaFiscalEmpresaTipoException;
import br.com.amarques.notasfiscais.exception.NotFoundException;
import br.com.amarques.notasfiscais.repository.NotaFiscalRepository;

@ExtendWith(MockitoExtension.class)
class NotaFiscalServiceTest {

    @Mock
    private NotaFiscalRepository repository;
    @Mock
    private EmpresaService empresaService;

    private NotaFiscalService notaFiscalService;

    @BeforeEach
    void before() {
        this.notaFiscalService = new NotaFiscalService(repository, empresaService);
    }

    @Test
    void shouldReturnRegisteredNotaFiscal() {
        //        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, 444L, 555L);

        when(repository.findById(ID)).thenReturn(Optional.of(buildNotaFiscal()));

        NotaFiscalDTO notaFiscalDTO = notaFiscalService.getById(ID);

        assertNotNull(notaFiscalDTO);
        assertThat(notaFiscalDTO.id, is(equalTo(ID)));
        assertThat(notaFiscalDTO.data, is(equalTo(DATA_NF_TEXT)));
        assertThat(notaFiscalDTO.numero, is(equalTo(NUMERO_NF)));
        assertThat(notaFiscalDTO.prestadorId, is(equalTo(9999L)));
        assertThat(notaFiscalDTO.tomadorId, is(equalTo(8888L)));
        assertThat(notaFiscalDTO.valor, is(equalTo(VALOR_NF)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindUnregisteredID() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> notaFiscalService.getById(ID));

        assertEquals(exception.getMessage(), MessageFormat.format("NotaFiscal [id: {0}] not found", ID));
    }

    @Test
    void shouldReturnAllRegisteredNotasFiscais() {
        when(repository.findAll()).thenReturn(List.of(buildNotaFiscal(), buildNotaFiscal()));

        List<NotaFiscalDTO> notasFiscais = notaFiscalService.getAll();

        assertFalse(notasFiscais.isEmpty());
        assertThat(notasFiscais.size(), is(equalTo(2)));
    }

    @Test
    void shouldReturnEmptyListWhenFindAllNotasFiscais() {
        when(repository.findAll()).thenReturn(List.of());

        List<NotaFiscalDTO> notasFiscais = notaFiscalService.getAll();

        assertTrue(notasFiscais.isEmpty());
    }

    @Test
    void shouldReturnAllNotasFiscaisRelatedWithEmpresaID() {
        when(empresaService.findById(8888L)).thenReturn(TOMADORA);
        when(repository.findAllByEmpresa(TOMADORA)).thenReturn(List.of(buildNotaFiscal(), buildNotaFiscal()));

        List<NotaFiscalDTO> notasFiscais = notaFiscalService.getAllByEmpresa(8888L);

        assertFalse(notasFiscais.isEmpty());
        assertThat(notasFiscais.size(), is(equalTo(2)));
    }

    @Test
    void shouldThrowInvalidNotaFiscalEmpresaTipoExceptionWHenTryCreateNotaFiscalWith() {
        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, 444L, 444L);

        assertThrows(InvalidNotaFiscalEmpresaTipoException.class, () -> notaFiscalService.create(dto));

        verify(repository, never()).save(any(NotaFiscal.class));
    }

    @Test
    void shouldThrowAnExceptionWhenTheCompanyTypeIsNotCorrect() {
        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, 8888L, 9999L);

        when(empresaService.findById(9999L)).thenReturn(TOMADORA);

        Exception exception = assertThrows(InvalidNotaFiscalEmpresaTipoException.class, () -> {
            notaFiscalService.create(dto);
        });

        assertEquals(exception.getMessage(), MessageFormat.format("The Empresa [id: {0}] should be [{1}] but is [{2}]",
                9999L, PRESTADOR.name(), TOMADORA.getTipo().name()));

        verify(repository, never()).save(any(NotaFiscal.class));
    }

    @Test
    void shoulCreate() {
        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, 8888L, 9999L);
        NotaFiscal notaFiscal = buildNotaFiscal();
        notaFiscal.setId(null);

        when(empresaService.findById(9999L)).thenReturn(PRESTADORA);
        when(empresaService.findById(8888L)).thenReturn(TOMADORA);

        notaFiscalService.create(dto);

        verify(repository, times(1)).save(notaFiscal);
    }

    private static NotaFiscal buildNotaFiscal() {
        PRESTADORA.setId(9999L);
        TOMADORA.setId(8888L);

        NotaFiscal notaFiscal = new NotaFiscal(NUMERO_NF, DATA_NF, VALOR_NF);
        notaFiscal.setId(ID);
        notaFiscal.setPrestador(PRESTADORA);
        notaFiscal.setTomador(TOMADORA);

        return notaFiscal;
    }

    private static final Long ID = 12321L;
    private static final Integer NUMERO_NF = 12345;
    private static final LocalDate DATA_NF = LocalDate.of(2020, 8, 18);
    private static final String DATA_NF_TEXT = "18/08/2020";
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;

    private static final Empresa PRESTADORA = new Empresa("Razão Socia P", "27.414.368/0001-52", PRESTADOR);
    private static final Empresa TOMADORA = new Empresa("Razão Social T", "35.437.352/0001-93", TOMADOR);
}
