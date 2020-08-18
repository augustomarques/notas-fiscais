package br.com.amarques.notasfiscais.service;

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

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import br.com.amarques.notasfiscais.exception.DeleteEmpresaException;
import br.com.amarques.notasfiscais.exception.EntityAlreadyRegisteredException;
import br.com.amarques.notasfiscais.exception.NotFoundException;
import br.com.amarques.notasfiscais.repository.EmpresaRepository;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository repository;

    private EmpresaService empresaService;

    @BeforeEach
    void before() {
        this.empresaService = new EmpresaService(repository);
    }

    @Test
    void shouldFindByIdAndReturnTheEmpresaDTO() {
        when(repository.findById(ID)).thenReturn(Optional.of(buildEmpresa()));

        EmpresaDTO empresaDTO = empresaService.getById(ID);

        assertNotNull(empresaDTO);
        assertThat(empresaDTO.id, is(equalTo(ID)));
        assertThat(empresaDTO.cnpj, is(equalTo(CNPJ)));
        assertThat(empresaDTO.razaoSocial, is(equalTo(RAZAO_SOCIAL)));
        assertThat(empresaDTO.tipo, is(equalTo(TIPO_EMPRESA)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindByIDNotRegistered() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> empresaService.getById(ID));

        assertEquals(exception.getMessage(), MessageFormat.format("Empresa [id: {0}] not found", ID));
    }

    @Test
    void shouldFindAllAndReturnNone() {
        when(repository.findAll()).thenReturn(List.of());

        List<EmpresaDTO> empresasDTO = empresaService.getAll();

        assertTrue(empresasDTO.isEmpty());
    }

    @Test
    void shouldFindAllAndReturnTwoEmpresas() {
        when(repository.findAll()).thenReturn(List.of(buildEmpresa(), buildEmpresa()));

        List<EmpresaDTO> empresasDTO = empresaService.getAll();

        assertFalse(empresasDTO.isEmpty());
        assertThat(empresasDTO.size(), is(equalTo(2)));
    }

    @Test
    void shouldDelete() {
        when(repository.findById(ID)).thenReturn(Optional.of(buildEmpresa()));
        when(repository.canBeRemoved(buildEmpresa())).thenReturn(true);

        empresaService.delete(ID);

        verify(repository, times(1)).delete(buildEmpresa());
    }

    @Test
    void shouldThrowDeleteEmpresaExceptionWhenTryDeleteAnEmpresaWithNotasFiscais() {
        when(repository.findById(ID)).thenReturn(Optional.of(buildEmpresa()));
        when(repository.canBeRemoved(buildEmpresa())).thenReturn(false);

        assertThrows(DeleteEmpresaException.class, () -> empresaService.delete(ID));
    }

    @Test
    void shouldCreateANewEmpresa() {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        Empresa empresa = buildEmpresa();
        empresa.setId(null);

        when(repository.findByCnpj(CNPJ)).thenReturn(Optional.empty());

        empresaService.create(dto);

        verify(repository, times(1)).save(empresa);
    }

    @Test
    void shouldThrowEntityAlreadyRegisteredExceptionWhenTryCreateAnEmpresaWithCNOJAlreadyRegistered() {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        when(repository.findByCnpj(CNPJ)).thenReturn(Optional.of(buildEmpresa()));

        Exception exception = assertThrows(EntityAlreadyRegisteredException.class, () -> empresaService.create(dto));

        assertEquals(exception.getMessage(), MessageFormat.format(
                "There is already an Empresa registered with CNPJ [{0}]", CNPJ));
        verify(repository, never()).save(any(Empresa.class));
    }

    @Test
    void shouldUpdate() {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO("Nova Fantasia", RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        when(repository.findByCnpj(CNPJ)).thenReturn(Optional.empty());
        when(repository.findById(ID)).thenReturn(Optional.of(buildEmpresa()));

        empresaService.update(ID, dto);

        Empresa empresa = buildEmpresa();
        empresa.setFantasia("Nova Fantasia");

        verify(repository, times(1)).save(empresa);
    }

    @Test
    void shouldThrowExceptionWhenTryRegisterEmpresaWithCNPJRegisteredToAnotherEmpresa() {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        when(repository.findByCnpj(CNPJ)).thenReturn(Optional.of(buildEmpresa()));

        Exception exception = assertThrows(EntityAlreadyRegisteredException.class, () -> {
            empresaService.update(112233L, dto);
        });

        assertEquals(exception.getMessage(), MessageFormat.format(
                "There is already an Empresa registered with CNPJ [{0}]", CNPJ));
        verify(repository, never()).save(any(Empresa.class));
    }

    private static Empresa buildEmpresa() {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        empresa.setFantasia(FANTASIA);
        empresa.setId(ID);
        return empresa;
    }

    private static final Long ID = 12345L;
    private static final String FANTASIA = "Fantasia";
    private static final String RAZAO_SOCIAL = "Razão Social";
    private static final String CNPJ = "27.414.368/0001-52";
    private static final TipoEmpresaEnum TIPO_EMPRESA = TipoEmpresaEnum.PRESTADOR;
}
