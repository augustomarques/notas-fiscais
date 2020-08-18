package br.com.amarques.notasfiscais.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;

class EmpresaTest {

    @Test
    void shouldCreateEmpresa() {
        Empresa empresa = new Empresa("Razão Social", "27.414.368/0001-52", TipoEmpresaEnum.PRESTADOR);

        assertNotNull(empresa);

        assertNull(empresa.getId());
        assertNull(empresa.getFantasia());

        assertThat(empresa.getRazaoSocial(), is(equalTo("Razão Social")));
        assertThat(empresa.getCnpj(), is(equalTo("27.414.368/0001-52")));
        assertThat(empresa.getTipo(), is(equalTo(TipoEmpresaEnum.PRESTADOR)));

        empresa.setFantasia("Fantasia");
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
