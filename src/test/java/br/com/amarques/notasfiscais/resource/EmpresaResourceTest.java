package br.com.amarques.notasfiscais.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import br.com.amarques.notasfiscais.mapper.EmpresaMapper;
import br.com.amarques.notasfiscais.service.EmpresaService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmpresaResource.class)
class EmpresaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpresaService empresaService;

    @Test
    void shouldReturnAllEmpresasRegistered() throws Exception {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        empresa.setId(ID);
        empresa.setFantasia(FANTASIA);

        when(empresaService.getAll()).thenReturn(List.of(EmpresaMapper.toDTO(empresa), EmpresaMapper.toDTO(empresa)));

        MvcResult mvcResult = mockMvc.perform(get("/empresas"))
                .andExpect(status().isOk())
                .andReturn();

        List<EmpresaDTO> empresas = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, EmpresaDTO.class));

        assertNotNull(empresas);
        assertEquals(2, empresas.size());
    }

    @Test
    void shouldReturnEmpresaWhenFindByID() throws Exception {
        Empresa empresa = new Empresa(RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);
        empresa.setId(ID);
        empresa.setFantasia(FANTASIA);

        when(empresaService.getById(ID)).thenReturn(EmpresaMapper.toDTO(empresa));

        MvcResult mvcResult = mockMvc.perform(get("/empresas/{id}", ID))
                .andExpect(status().isOk())
                .andReturn();

        EmpresaDTO empresaDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                EmpresaDTO.class);

        assertNotNull(empresaDTO);
        assertThat(empresaDTO.cnpj, is(equalTo(CNPJ)));
        assertThat(empresaDTO.razaoSocial, is(equalTo(RAZAO_SOCIAL)));
        assertThat(empresaDTO.tipo, is(equalTo(TIPO_EMPRESA)));
    }

    @Test
    void shouldCreate() throws Exception {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        when(empresaService.create(dto)).thenReturn(new SimpleEntityDTO(ID));

        MvcResult mvcResult = mockMvc.perform(post("/empresas")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        SimpleEntityDTO simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(ID)));
    }

    @Test
    void shouldUpdate() throws Exception {
        CreateUpdateEmpresaDTO dto = new CreateUpdateEmpresaDTO(FANTASIA, RAZAO_SOCIAL, CNPJ, TIPO_EMPRESA);

        mockMvc.perform(put("/empresas/{id}", ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(empresaService, times(1)).update(ID, dto);
    }

    @Test
    void shouldDelete() throws Exception {
        mockMvc.perform(delete("/empresas/{id}", ID)).andExpect(status().isOk());

        verify(empresaService, times(1)).delete(ID);
    }

    private static final Long ID = 123L;
    private static final String RAZAO_SOCIAL = "Razão Social";
    private static final String CNPJ = "27.414.368/0001-52";
    private static final String FANTASIA = "Fantasia";
    private static final TipoEmpresaEnum TIPO_EMPRESA = TipoEmpresaEnum.PRESTADOR;
}
