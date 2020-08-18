package br.com.amarques.notasfiscais.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;

class EmpresaMapperTest {

    private static final Long ID = 123L;
    private static final String RAZAO_SOCIAL = "Razão Social";
    private static final String CNPJ = "27.414.368/0001-52";
    private static final String FANTASIA = "Fantasia";
    private static final TipoEmpresaEnum TIPO_EMPRESA = TipoEmpresaEnum.PRESTADOR;

    @Test
    void shouldConvertDTOToEntity() {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        Empresa empresa = EmpresaMapper.toEntity(dto);

        assertNotNull(empresa);
        assertNull(empresa.getId());
        assertThat(empresa.getRazaoSocial(), is(equalTo(RAZAO_SOCIAL)));
        assertThat(empresa.getFantasia(), is(equalTo(FANTASIA)));
        assertThat(empresa.getCnpj(), is(equalTo(CNPJ)));
        assertThat(empresa.getTipo(), is(equalTo(TIPO_EMPRESA)));
    }

    @Test
    void shouldConvertEntityToDTO() {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        empresa.setId(ID);
        empresa.setFantasia(FANTASIA);

        EmpresaDTO dto = EmpresaMapper.toDTO(empresa);

        assertNotNull(dto);
        assertThat(dto.id, is(equalTo(ID)));
        assertThat(dto.razaoSocial, is(equalTo(RAZAO_SOCIAL)));
        assertThat(dto.fantasia, is(equalTo(FANTASIA)));
        assertThat(dto.tipo, is(equalTo(TIPO_EMPRESA)));
        assertThat(dto.cnpj, is(equalTo(CNPJ)));
    }
}
