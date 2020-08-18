package br.com.amarques.notasfiscais.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;

class EmpresaTest {

    private static final String RAZAO_SOCIAL = "Razão Social";
    private static final String CNPJ = "27.414.368/0001-52";
    private static final String FANTASIA = "Fantasia";
    private static final TipoEmpresaEnum TIPO_EMPRESA = TipoEmpresaEnum.PRESTADOR;

    @Test
    void shouldCreateEmpresa() {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        assertNotNull(empresa);
        assertNull(empresa.getId());
        assertNull(empresa.getFantasia());
        assertThat(empresa.getRazaoSocial(), is(equalTo(RAZAO_SOCIAL)));
        assertThat(empresa.getCnpj(), is(equalTo(CNPJ)));
        assertThat(empresa.getTipo(), is(equalTo(TIPO_EMPRESA)));

        empresa.setFantasia(FANTASIA);
        empresa.setRazaoSocial("Razão Social Alterada");
        empresa.setCnpj("88.535.135/0001-00");
        empresa.setTipo(TipoEmpresaEnum.TOMADOR);

        assertNotNull(empresa);
        assertNull(empresa.getId());
        assertNotNull(empresa.getFantasia());
        assertThat(empresa.getRazaoSocial(), is(equalTo("Razão Social Alterada")));
        assertThat(empresa.getCnpj(), is(equalTo("88.535.135/0001-00")));
        assertThat(empresa.getTipo(), is(equalTo(TipoEmpresaEnum.TOMADOR)));
    }
}
